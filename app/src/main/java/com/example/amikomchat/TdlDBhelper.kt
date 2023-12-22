package com.example.amikomchat

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast
import com.example.amikomchat.model.ModelTdl

class TdlDBhelper(private val context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_VERSION = 5
        private const val DATABASE_NAME = "TdlDBhelper"
        private const val TABLE_TDL = "tdl"
        private const val KEY_ID = "id"
        private const val KEY_JUDUL = "judul"
        private const val KEY_PRIORITAS = "prioritas"
        private const val KEY_DESKRIPSI = "deskripsi"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTableQuery = ("CREATE TABLE $TABLE_TDL($KEY_ID INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "$KEY_JUDUL TEXT, $KEY_PRIORITAS TEXT, $KEY_DESKRIPSI TEXT)")
        db?.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val dropTableQuery = "DROP TABLE IF EXISTS $TABLE_TDL"
        db?.execSQL(dropTableQuery)
        onCreate(db)
    }

    fun insertTDL (tdl: ModelTdl) {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(KEY_JUDUL, tdl.judul)
            put(KEY_PRIORITAS, tdl.prioritas)
            put(KEY_DESKRIPSI, tdl.deskkripsi)
        }
            db.insert(TABLE_TDL, null, values)
            db.close()
    }

    @SuppressLint("Range")
    fun showTDL(): List<ModelTdl> {
        val tdlList = mutableListOf<ModelTdl>()
        val db = this.readableDatabase
        val query = "SELECT * FROM $TABLE_TDL"
        val cursor = db.rawQuery(query,null)

        while (cursor.moveToNext()) {
            val id = cursor.getInt(cursor.getColumnIndex(KEY_ID))
            val judul = cursor.getString(cursor.getColumnIndex(KEY_JUDUL))
            val prioritas = cursor.getString(cursor.getColumnIndex(KEY_PRIORITAS))
            val deskripsi = cursor.getString(cursor.getColumnIndex(KEY_DESKRIPSI))

            val tdl = ModelTdl(id, judul, prioritas, deskripsi)
            tdlList.add(tdl)

        }
        cursor.close()
        db.close()
        return  tdlList
    }

    fun editTdl(tdl:ModelTdl){
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(KEY_JUDUL, tdl.judul)
        values.put(KEY_PRIORITAS, tdl.prioritas)
        values.put(KEY_DESKRIPSI, tdl.deskkripsi)

        val result = db.update(TABLE_TDL,values, "$KEY_ID = ? ", arrayOf(tdl.id.toString())).toLong()

        if (result==(0).toLong()){
            Toast.makeText(context, "Edit List Failed", Toast.LENGTH_SHORT).show()
        }
        else {
            Toast.makeText(context, "Edit List  Success", Toast.LENGTH_SHORT).show()
        }
        db.close()
    }

    @SuppressLint("Range")
    fun getTdlById(tdlId: Int): ModelTdl? {
        val db = this.readableDatabase
        val cursor = db.query(
            TABLE_TDL, arrayOf(KEY_ID, KEY_JUDUL, KEY_PRIORITAS, KEY_DESKRIPSI),
            "$KEY_ID=?", arrayOf(tdlId.toString()), null, null, null, null
        )

        return if (cursor != null && cursor.moveToFirst()) {
            val id = cursor.getInt(cursor.getColumnIndex(KEY_ID))
            val storedJudul = cursor.getString(cursor.getColumnIndex(KEY_JUDUL))
            val storedPrioritas = cursor.getString(cursor.getColumnIndex(KEY_PRIORITAS))
            val storedDeskripsi = cursor.getString(cursor.getColumnIndex(KEY_DESKRIPSI))
            ModelTdl(id, storedJudul, storedPrioritas, storedDeskripsi)
        } else {
            null
        }.also {
            cursor?.close()
            db?.close()
        }
    }

    fun deleteTdl(tdlId: Int){
        val db = this.readableDatabase
        val whereClause = "$KEY_ID = ?"
        val whereArgs = arrayOf(tdlId.toString())

        db.delete(TABLE_TDL, whereClause, whereArgs)
        db.close()
    }
}
