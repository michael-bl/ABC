package com.empresa.abc

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

    /*
        Punto de entrada de la aplicacion
        las variables  textview y btnlogin permiten acceder a los objetos del layout vw_login
        y obtener los valores ingresados por el usuario, posteriormente se crea una instancia
        de la clase Main, por medio del Intent y se muestra la interfaz
    */
class Login : AppCompatActivity() {

    //Permite obtener el valor de variable en cualquier clase
    companion object {
        var nameUser: String = ""
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.vw_login)

        val textview = findViewById<TextView>(R.id.tvNameUser)

        val btnlogin = findViewById<Button>(R.id.btnInicio)
        btnlogin.setOnClickListener {
            val intent = Intent(this, Main::class.java)
            startActivity(intent)
            nameUser = textview.text.toString()
        }
    }
}