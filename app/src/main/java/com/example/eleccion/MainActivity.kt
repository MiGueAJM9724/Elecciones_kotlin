package com.example.eleccion

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.example.eleccion.DataBase.AdDataBase

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
                Toast.makeText(this,"Bienvenido $scontrol :)",Toast.LENGTH_SHORT).show()
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
}
