import android.util.Log.e
import android.util.Log.i
import com.empresa.abc.model.Mdl_Indicator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.w3c.dom.Element
import org.w3c.dom.Node
import org.xml.sax.InputSource
import java.io.IOException
import java.io.StringReader
import javax.xml.parsers.DocumentBuilderFactory

class CtrIndicator {

    private val URL_BASE =
        "https://gee.bccr.fi.cr/Indicadores/Suscripciones/WS/wsindicadoreseconomicos.asmx"
    private val METHOD_NAME = "ObtenerIndicadoresEconomicosXML"
    private val SOAP_ACTION = "http://ws.sdde.bccr.fi.cr/$METHOD_NAME"
    var myIndicator: String = ""

    /* Esta función recibe un objeto Mdl_Indicator que contiene los valores requeridos para la solicitud al servicio web.
           Crea un objeto XML con el cuerpo de la solicitud SOAP y realiza una llamada HTTP POST al servicio.

           Puntos de flujo:
            Construye un XML SOAP con los parámetros del Mdl_Indicator.
            Construye una solicitud HTTP usando OkHttp.
            Ejecuta la solicitud HTTP en un contexto de corutina en el hilo de fondo.
            Si la respuesta HTTP es exitosa, extrae el valor del indicador económico de la respuesta XML utilizando la función
            extractDataFromXmlResponse(responseBody: String).
            Si la respuesta HTTP no es exitosa, imprime un mensaje de error.
            Finalmente, mencionar que la función utiliza corutinas para ejecutar la solicitud HTTP en segundo plano, usando el despachador Dispatchers.IO
            con lo que evitamos errore de solicitudes al saturar el hilo principal
      */
    fun obtenerIndicadoresEconomicos(indicator: Mdl_Indicator): String {

        val xml = """
            <?xml version="1.0" encoding="utf-8"?>
            <soap:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
              <soap:Body>
                <$METHOD_NAME xmlns="http://ws.sdde.bccr.fi.cr">
                  <Indicador>${indicator.indicador}</Indicador>
                  <FechaInicio>${indicator.fechaInicio}</FechaInicio>
                  <FechaFinal>${indicator.fechaFinal}</FechaFinal>
                  <Nombre>${indicator.nombre}</Nombre>
                  <SubNiveles>${indicator.subNiveles}</SubNiveles>
                  <CorreoElectronico>${indicator.correoElectronico}</CorreoElectronico>
                  <Token>${indicator.token}</Token>
                </$METHOD_NAME>
              </soap:Body>
            </soap:Envelope>
        """.trimIndent()

        val request = Request.Builder()
            .url(URL_BASE)
            .post(xml.toRequestBody("text/xml; charset=utf-8".toMediaTypeOrNull()))
            .addHeader("Accept", "*/*")
            .addHeader("Content-Length", xml.length.toString())
            .addHeader("SOAPAction", SOAP_ACTION)
            .build()

        val client = OkHttpClient()

        runBlocking {
            launch(Dispatchers.IO) {
                try {
                    val response = client.newCall(request).execute()

                    if (response.isSuccessful) {
                        val responseBody = response.body?.string() ?: ""
                        myIndicator = extractDataFromXmlResponse(responseBody)
                    } else {
                        // Imprimir error en la respuesta
                        println("HTTP Error: ${response.message}")
                    }
                } catch (e: IOException) {
                    // Mostrar la pila de la excepcion
                    e.printStackTrace()
                }
            }
        }
        return myIndicator
    }

    /*
        Descripción: Esta función toma la respuesta XML del servicio web y extrae el valor del indicador económico por medio del tagname "NUM_VALOR".

        Puntos de flujo:
        Crea un DocumentBuilder para analizar el XML de la respuesta.
        Obtiene el contenido del nodo ObtenerIndicadoresEconomicosXMLResult que contiene el nodo (INGC011_CAT_INDICADORECONOMIC).
        Analiza el siguiente nivel o elemento de datos XML para extraer el valor del indicador económico (NUM_VALOR).
        Retorna el valor del indicador económico.
    */
    fun extractDataFromXmlResponse(responseBody: String): String {

        val docBuildFactory = DocumentBuilderFactory.newInstance()
        val docBuilder = docBuildFactory.newDocumentBuilder()
        val doc = docBuilder.parse(InputSource(StringReader(responseBody)))

        val nodeList = doc.getElementsByTagName("ObtenerIndicadoresEconomicosXMLResult")
        if (nodeList != null && nodeList.length > 0) {
            val data = nodeList.item(0).textContent
            val dataDoc = docBuilder.parse(InputSource(StringReader(data)))
            val dataNodeList = dataDoc.getElementsByTagName("INGC011_CAT_INDICADORECONOMIC")
            for (i in 0 until dataNodeList.length) {
                val dataNode = dataNodeList.item(i)
                if (dataNode?.nodeType == Node.ELEMENT_NODE) {
                    val element = dataNode as Element
                    val numValorNode = element.getElementsByTagName("NUM_VALOR").item(0)
                    if (numValorNode != null) {
                        myIndicator = numValorNode.textContent
                        i("HTTP/extractDataFromXmlResponse", myIndicator)
                    }
                }
            }
        } else {
            e("HTTP", "Error: NodeList null o vacio")
        }
        return myIndicator
    }
}