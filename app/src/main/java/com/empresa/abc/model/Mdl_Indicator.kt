package com.empresa.abc.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Mdl_Indicator{

    @SerializedName("Indicador")
    @Expose
    var indicador: String = ""

    @SerializedName("FechaInicio")
    @Expose
    var fechaInicio: String = ""

    @SerializedName("FechaFinal")
    @Expose
    var fechaFinal: String = ""

    @SerializedName("Nombre")
    @Expose
    var nombre: String = ""

    @SerializedName("SubNiveles")
    @Expose
    var subNiveles: String = ""

    @SerializedName("CorreoElectronico")
    @Expose
    var correoElectronico: String = ""

    @SerializedName("Token")
    @Expose
    var token: String = ""
}