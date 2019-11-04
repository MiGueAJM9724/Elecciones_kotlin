package com.example.eleccion

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.example.eleccion.Adress.IP
import com.example.eleccion.DataBase.AdDataBase
import com.example.eleccion.Volley.VolleySingleton
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login.txtncontrol
import org.json.JSONObject

class ActivityLogin : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        btnlogin.setOnClickListener{
            if(txtncontrol.text!!.isEmpty() || txtNip.text!!.isEmpty() ){
                Toast.makeText(this,"Complete sus credenciales",Toast.LENGTH_SHORT).show()
                txtncontrol.requestFocus()
            }else{
                var jsonEntrada = JSONObject()
                jsonEntrada.put("ncontrol",txtncontrol.text.toString())
                jsonEntrada.put("nip",txtNip.text.toString())
                getUsuarioById(jsonEntrada)
                val ncontrol = txtncontrol.text.toString()
                val nip = txtNip.text.toString()
                val sentencia = "Insert into usuario(ncontrol, nip) Values('$ncontrol','$nip')"
                var admin = AdDataBase(this)
                if(admin.Ejecuta(sentencia)){
                    val actividad = Intent(this,MainActivity::class.java)
                    startActivity(actividad)
                }else{
                    Toast.makeText(this,"El numero de control o nip son incorectos",Toast.LENGTH_SHORT).show()
                    txtncontrol.requestFocus()
                }

            }
            /*if(txtncontrol.text!!.isEmpty() || txtNip.text!!.isEmpty() ){
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

                 }
             }*/
         }
     }


    fun getUsuarioById(jsonEnt: JSONObject){
        val wsURL = IP.IP + "wsElecciones/getusuario.php"
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.POST, wsURL, jsonEnt, Response.Listener { response ->
                val  succ = response["success"]
                val msg = response["message"]
                val usuariosJson = response.getJSONArray("usuario")
                if(usuariosJson.length() > 0){
                    val ncontrol = usuariosJson.getJSONObject(0).getString("ncontrol")
                    val nip = usuariosJson.getJSONObject(0).getString("nip")
                }
            }, Response.ErrorListener { error ->
                Toast.makeText(this,"ERRORGETUSUARIO" + error.message.toString(),Toast.LENGTH_LONG).show()
                Log.d("Capa 8",error.message.toString())
            }
        )
        VolleySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
    }
}
