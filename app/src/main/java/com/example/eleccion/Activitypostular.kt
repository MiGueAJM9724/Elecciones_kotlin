package com.example.eleccion

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.example.eleccion.Adress.IP
import com.example.eleccion.DataBase.AdDataBase
import com.example.eleccion.Volley.VolleySingleton
import kotlinx.android.synthetic.main.activity_activitypostular.*
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject

class Activitypostular : AppCompatActivity() {
    private lateinit var  scontrol: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_activitypostular)
        btnAgregar.setOnClickListener{
            if(txtdescripcion.text!!.isEmpty() || txtpropuesta.text!!.isEmpty()){
                Toast.makeText(this,"Complete los campos",Toast.LENGTH_SHORT).show()
                txtdescripcion.requestFocus()
            }else{
                val descripcion = txtdescripcion.text.toString()
                val propuesta = txtpropuesta.text.toString()
                val admin = AdDataBase(this)
                val result = admin.consulta("Select ncontrol From usuario")
                if (result!!.moveToFirst()) {
                    scontrol = result.getString(0)
                    txtncontrols.text = scontrol
                    result.close()
                }
                val ncontrol = txtncontrols.text.toString()
                val sentencia = "Insert into candidato(descripcion,propuesta,ncontrol) " +
                        "Values('$descripcion','$propuesta','$ncontrol')"
                if (admin.Ejecuta(sentencia)) {
                    Toast.makeText(this,"Acción exitosa",Toast.LENGTH_SHORT).show()
                    val intent = Intent(this,MainActivity::class.java)
                    startActivity(intent)
                    btnAgregar.invalidate()
                }
                else{
                    Toast.makeText(this,"Complete los campos",Toast.LENGTH_SHORT).show()
                    txtdescripcion.requestFocus()
                }
            }
        }
    }
}
