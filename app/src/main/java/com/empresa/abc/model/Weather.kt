package com.empresa.abc.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


public class Weather {

        @SerializedName("lat")
        @Expose
        private var latitud: String = ""

        @SerializedName("lon")
        @Expose
        private var longitud: String = ""

        @SerializedName("cod_pais")
        @Expose
        private var codPais: String = ""

        @SerializedName("temperatura")
        @Expose
        private var temperatura: String = ""

        @SerializedName("descripcion")
        @Expose
        private var descripcion: String = ""

        @SerializedName("salida_sol")
        @Expose
        private var salidaSol: String = ""

        @SerializedName("puesta_sol")
        @Expose
        private var puestaSol: String = ""

    }