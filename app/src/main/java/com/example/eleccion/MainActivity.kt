package com.example.eleccion

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.example.eleccion.Adress.IP
import com.example.eleccion.DataBase.AdDataBase
import com.example.eleccion.Volley.VolleySingleton
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    private lateinit var  scontrol: String
    private lateinit var snip: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
              val actividad = intent
        if(actividad != null && actividad.hasExtra("ncontrol") && actividad.hasExtra("nip")){
            scontrol = actividad.getStringExtra("ncontrol")
            snip = actividad.getStringExtra("nip")
        }else{
            val admin = AdDataBase(this)
            val result = admin.consulta("Select ncontrol,nip From usuario")
            if(result!!.moveToFirst()){
                scontrol = result.getString(0)
                snip = result.getString(1)
                txtncontrol.text = scontrol
                result.close()
                admin.close()
            }else{
                val actividadlogin = Intent(this,ActivityLogin::class.java)
                startActivity(actividadlogin)
            }
        }
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu,menu)
        return super.onCreateOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.btnCandidato ->{
                Toast.makeText(this,"Candidatos",Toast.LENGTH_SHORT).show()
                return true
            }
            R.id.btnPostula ->{
                val actividadpostular = Intent(this,Activitypostular::class.java)
                startActivity(actividadpostular)
                return true
            }
            R.id.btnVoto ->{
                Toast.makeText(this,"Votar",Toast.LENGTH_SHORT).show()
                return true
            }
            R.id.btnResultado ->{
                Toast.makeText(this,"Resultados",Toast.LENGTH_SHORT).show()
                return true
            }
            else->{
                return super.onOptionsItemSelected(item)
            }
        }
    }
   /* fun insertCandidato(v: View){
        if (txtdescripcion.text.toString().isEmpty() || txtpropuesta.text.toString().isEmpty()){
            Toast.makeText(this,"No deje campos vacios",Toast.LENGTH_SHORT).show()
            txtdescripcion.requestFocus()
        }else{
            var jsonEntrada = JSONObject()
            jsonEntrada.put("descripcion",txtdescripcion.text.toString())
            jsonEntrada.put("propuesta",txtpropuesta.text.toString())
            jsonEntrada.put("ncontrol",txtncontrol.text.toString())
            sendRequest(IP.IP+"wsElecciones/insertCandidato.php",jsonEntrada)
        }
    }

    fun sendRequest(wsURL:String,jsonEnt: JSONObject){
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.POST,wsURL,jsonEnt,
            Response.Listener { response ->
                val succ = response["success"]
                val msg = response["message"]
                Toast.makeText(this,"Error URL",Toast.LENGTH_SHORT).show()
            },
            Response.ErrorListener { error ->
                Toast.makeText(this,"${error.message}",Toast.LENGTH_SHORT).show()
                Log.d("ERROR","${error.message}")
                Toast.makeText(this,"Error URL",Toast.LENGTH_SHORT).show()
            }
        )
        VolleySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
    }*/
}
