package com.cc221015.demo_ii.data

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.cc221015.demo_ii.domain.Pokemon

class PokemonBaseHandler(context: Context) : SQLiteOpenHelper(context, dbName, null, 1) {
    companion object PokemonDatabase{
        private const val dbName = "PokemonDatabase"
        private const val tableName = "Pokemon"
        private const val id = "_id"
        private const val name = "name"
        private const val type0= "type0"
        private const val type1= "type1"
        private const val imageUrl = "imageUrl"
        private const val liked = "liked"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("CREATE TABLE IF NOT EXISTS $tableName (" +
                "$id INTEGER PRIMARY KEY, " +
                "$name VARCHAR(30), " +
                "$type0 VARCHAR(256), " +
                "$type1 VARCHAR(256), " +
                "$imageUrl VARCHAR(256), " +
                "$liked VARCHAR(256));")
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $tableName")
        onCreate(db)
    }

    fun insertPokemon(pokemon: Pokemon){
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(id,pokemon.number)
        values.put(name,pokemon.name)
        values.put(type0,pokemon.type0)
        values.put(type1,pokemon.type1)
        values.put(liked,pokemon.liked)
        values.put(imageUrl, pokemon.imageUrl)

        db.insert(tableName,null, values)
    }

    fun likePokemon(pokemon: Pokemon){
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(liked, "true")

        db.update(tableName,values,"_id = ?", arrayOf(pokemon.number.toString()))
    }

    fun unlikePokemon(pokemon: Pokemon){
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(liked, "false")

        db.update(tableName,values,"_id = ?", arrayOf(pokemon.number.toString()))
    }

    fun getPokemons(): List<Pokemon>{
        var allPokemons = mutableListOf<Pokemon>()

        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $tableName",null)
        while(cursor.moveToNext()){
            val idID = cursor.getColumnIndex(id)
            val nameID = cursor.getColumnIndex(name)
            val type0ID = cursor.getColumnIndex(type0)
            val type1ID = cursor.getColumnIndex(type1)
            val likedID = cursor.getColumnIndex(liked)
            val imageUrlID = cursor.getColumnIndex(imageUrl)
            if(nameID >= 0)
                allPokemons.add(Pokemon(cursor.getInt(idID),cursor.getString(nameID),cursor.getString(type0ID),cursor.getString(type1ID),cursor.getString(imageUrlID),cursor.getString(likedID)))
        }

        return allPokemons.toList()
    }

    fun getFavPokemons(): List<Pokemon>{
        var allPokemons = mutableListOf<Pokemon>()

        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $tableName WHERE $liked='true'",null)
        while(cursor.moveToNext()){
            val idID = cursor.getColumnIndex(id)
            val nameID = cursor.getColumnIndex(name)
            val type0ID = cursor.getColumnIndex(type0)
            val type1ID = cursor.getColumnIndex(type1)
            val likedID = cursor.getColumnIndex(liked)
            val imageUrlID = cursor.getColumnIndex(imageUrl)
            if(nameID >= 0)
                allPokemons.add(Pokemon(cursor.getInt(idID),cursor.getString(nameID),cursor.getString(type0ID),cursor.getString(type1ID),cursor.getString(imageUrlID),cursor.getString(likedID)))
        }

        return allPokemons.toList()
    }
}