package com.example.eleccion.DataBase

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.lang.Exception

class AdDataBase (context: Context): SQLiteOpenHelper(context,
    DATABASE,null,1) {
    companion object{
        val DATABASE = "elecciones"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(
            "Create Table usuario("+
            "ncontrol Text Primary Key," +
                    "nip Text)"
        )
        db?.execSQL(
            "Create Table candidato(" +
                    "id_candidato Integer Primary Key," +
                    "descripcion Text," +
                    "propuesta Text," +
                    "ncontrol Text)"
        )
        db?.execSQL(
            "Create Table voto(" +
                    "id_voto Integer Primary Key," +
                    "id_candidato Integer," +
                    "ncontrol Text)"
        )
    }
    //función para llamar ejecutar un insert,update o delete
    fun Ejecuta(sentencia: String):Boolean{
        try {
            val db = this.writableDatabase
            db.execSQL(sentencia)
            db.close()
            return true
        }catch (ex:Exception){
            return false
        }
    }
    //funcón para hacer consultas
    fun consulta(query: String): Cursor?{
        try {
            val db = this.readableDatabase
            return db.rawQuery(query,null)
        }catch (ex:Exception){
            return null
        }
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}