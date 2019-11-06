package com.example.eleccion

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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
    private lateinit var viewAdapter: CandidatoAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager
    val candidatoList: List<candidato> = ArrayList()
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
        viewManager = LinearLayoutManager(this)
        viewAdapter = CandidatoAdapter(candidatoList,this,{
                candid:candidato -> onItemClickListener(candid)})
        rv_candidato_list.apply{
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
            addItemDecoration(DividerItemDecoration(this@MainActivity,DividerItemDecoration.VERTICAL))
        }


        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT)
        {override fun onMove(recyclerView: RecyclerView,viewHolder: RecyclerView.ViewHolder:RecyclerView.ViewHolder,
                                                                                                                                     target: target: RecyclerView.ViewHolder):Boolean{
            return false
        }
                override fun onSwiped(viewHolder: RecyclerView.ViewHolder,swipeDir: Int){
            val position = viewHolder.adapterPosition
            val candid = viewAdapter.getTask()
            val  admin = AdDataBase(baseContext)
            if(admin.Ejecuta("Delete From candidato Where id_candidato =" + candid[position].id_candidato) == 1){
                retrieveCandidato()
            }
        }
    }).attachToRecyclerView(rv_candidato_list)
}
    private fun onItemClickListener(candid:candidato){
        Toast.makeText(this,"Clicked item" + candid.nombre_alumno,Toast.LENGTH_SHORT).show()
    }

    override fun onResume(){
        super.onResume()
        retrieveCandidato()
    }

    private fun retrieveCandidato(){
        val  candidatoX = getCandidatos()
        viewAdapter.setTask(candidatoX!!)

    }

    fun  getCandidatos():MutableList<candidato>{
        var candidato:MutableList<candidato> = ArrayList()
        val admin = AdDataBase(this)

        val tupla = admin.consulta("Select id_candidato, descripcion, propuesta From candidato Order By id_candidato")
        while (tupla!!.moveToNext()){
            val no = tupla.getInt(0)
            val nombre_carrera = tupla.getString(1)
            candidato.add(candidato(no,nombre_carrera))
        }
        tupla.close()
        admin.close()
        return candidato
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
