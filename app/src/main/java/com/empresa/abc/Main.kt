package com.empresa.abc

import CtrIndicator
import GetSystemDate
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import com.empresa.abc.Login
import com.empresa.abc.R
import com.empresa.abc.model.Mdl_Indicator
import java.io.File

class Main : AppCompatActivity() {

    private var compra: String = ""
    private var venta: String = ""
    private var fecha = ""

    /* Utilizamos la clase GetSystemDate() para obtener la fecha actual en el formato "dd/MM/yyyy" y la asigna a la variable fecha.
        Crea una instancia de la clase Mdl_Indicator para definir los parámetros necesarios para la solicitud de indicadores económicos.
        Configura los valores de indicador, fechaInicio, fechaFinal, nombre, subNiveles, correoElectronico y token en el objeto indicadores.
        Crea una instancia de la clase CtrIndicator para realizar solicitudes SOAP.
        Llama al método obtenerIndicadoresEconomicos(indicadores) de CtrIndicator con indicadores para obtener el valor de indicador de compra
        y lo formatea utilizando la función formatDecimal. Asigna el resultado a la variable compra.
        Modifica el valor de indicador en indicadores a "318" para obtener el valor de indicador de venta y realiza una llamada similar para obtenerlo.
        Formatea el valor y lo asigna a la variable venta.
        Obtiene una referencia al TextView con ID tvUserLogged y establece el texto en Login.nameUser, que parece ser el nombre del usuario actual.
        Obtiene referencias a los TextView con IDs tvShowBuy y tvShowSale y establece los textos formateados de compra y venta, respectivamente.
    * */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.vw_main)

        fecha = GetSystemDate().getCurrentDate()

        // 317=compra 318=venta
        val indicadores = Mdl_Indicator()
        indicadores.indicador = "317"
        indicadores.fechaInicio = fecha
        indicadores.fechaFinal = fecha
        indicadores.nombre = "Negocio"
        indicadores.subNiveles = "N"
        indicadores.correoElectronico = "jabarca@prides.net"
        indicadores.token = "D2DTREJA8A"

        val indicador = CtrIndicator()
        compra = formatDecimal(indicador.obtenerIndicadoresEconomicos(indicadores))

        indicadores.indicador = "318"
        venta = formatDecimal(indicador.obtenerIndicadoresEconomicos(indicadores))

        val tvNameUserLogged = findViewById<TextView>(R.id.tvUserLogged)
        tvNameUserLogged.text = Login.nameUser


        val tvBuy = findViewById<TextView>(R.id.tvShowBuy)
        tvBuy.text = compra
        val tvSale = findViewById<TextView>(R.id.tvShowSale)
        tvSale.text = venta

    }

    /*
        Permite reducir a dos decimales el valor numerico del string recibido por parametro
    */
    fun formatDecimal(input: String): String {
        try {
            val value = input.toDouble()
            return String.format("%.2f", value)
        } catch (e: NumberFormatException) {
            return input // Devuelve la cadena original si no se puede convertir a un número
        }
    }
}