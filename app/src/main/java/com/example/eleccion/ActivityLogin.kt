package com.example.eleccion

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.eleccion.DataBase.AdDataBase
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_login.*

class ActivityLogin : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        btnlogin.setOnClickListener{view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
            if(txtncontrol.text.isEmpty() || txtNip.text.isEmpty()){
                Toast.makeText(this,"Complete sus credenciales",Toast.LENGTH_SHORT).show()
                txtncontrol.requestFocus()
            }else{
                val ncontrol = txtncontrol.text.toString()
                val nip = txtNip.text.toString()
                val sentencia = "Insert into usuario(ncontrol, nip) Values('$ncontrol','$nip')"
                var admin = AdDataBase(this)
                if(admin.Ejecuta(sentencia)){
                    val actividad = Intent(this,MainActivity::class.java)
                    actividad.putExtra("ncontrol",ncontrol)
                    actividad.putExtra("nip",nip)
                    startActivity(actividad)
                }else{
                    Toast.makeText(this,"El numero de control o nip son incorectos",Toast.LENGTH_SHORT).show()
                    txtncontrol.requestFocus()
                }
            }
        }
    }
}
