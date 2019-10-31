package com.example.eleccion

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils.isEmpty
import android.widget.Toast
import com.example.eleccion.DataBase.AdDataBase
import kotlinx.android.synthetic.main.activity_login.*

class ActivityLogin : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
         btnlogin.setOnClickListener{
            if(txtncontrol.text!!.isEmpty() || txtNip.text!!.isEmpty() ){
                 Toast.makeText(this,"Complete sus credenciales",Toast.LENGTH_SHORT).show()
                 txtncontrol.requestFocus()
             }else{
                 val ncontrol = txtncontrol.text.toString()
                 val nip = txtNip.text.toString()
                 val sentencia = "Insert into usuario(ncontrol, nip) Values('$ncontrol','$nip')"
                 var admin = AdDataBase(this)
                 //val query = admin.consulta("Select ncontrol,nip From usuario")
                 if(admin.Ejecuta(sentencia)){
                // if(query != null){
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
