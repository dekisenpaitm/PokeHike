package com.cc221015.demo_ii.data

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

// This class handles database operations for storing Pokemon trainers' data.
class TrainerBaseHandler(context: Context) : SQLiteOpenHelper(context, dbName, null, 1) {
    companion object PokemonTrainerDatabase {
        private const val dbName = "PokemonTrainerDatabase"
        private const val tableName = "PokemonTrainer"
        private const val id = "_id"
        private const val name = "name"
        private const val gender = "gender"
        private const val sprite = "sprite"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        // Create the PokemonTrainer table when the database is first created.
        db?.execSQL("CREATE TABLE IF NOT EXISTS $tableName ($id INTEGER PRIMARY KEY, $name VARCHAR(30), $gender VARCHAR(10), $sprite VARCHAR(256));")
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        // Upgrade the database (e.g., by dropping the table) if needed.
        db?.execSQL("DROP TABLE IF EXISTS $tableName")
        onCreate(db)
    }

    // Insert a Pokemon trainer into the database.
    fun insertPokemonTrainer(pokemonTrainer: PokemonTrainer) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(name, pokemonTrainer.name)
        values.put(gender, pokemonTrainer.gender)
        values.put(sprite, pokemonTrainer.sprite)

        db.insert(tableName, null, values)
    }

    // Update an existing Pokemon trainer in the database.
    fun updatePokemonTrainer(pokemonTrainer: PokemonTrainer) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(id, pokemonTrainer.id)
        values.put(name, pokemonTrainer.name)
        values.put(gender, pokemonTrainer.gender)
        values.put(sprite, pokemonTrainer.sprite)

        db.update(tableName, values, "_id = ?", arrayOf(pokemonTrainer.id.toString()))
    }

    // Delete a Pokemon trainer from the database.
    fun deletePokemonTrainer(pokemonTrainer: PokemonTrainer) {
        val db = writableDatabase
        db.delete(tableName, "_id = ?", arrayOf(pokemonTrainer.id.toString()))
    }

    // Retrieve all Pokemon trainers from the database.
    fun getPokemonTrainers(): List<PokemonTrainer> {
        var allPokemonTrainers = mutableListOf<PokemonTrainer>()

        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $tableName", null)
        while (cursor.moveToNext()) {
            val idID = cursor.getColumnIndex(id)
            val nameID = cursor.getColumnIndex(name)
            val genderID = cursor.getColumnIndex(gender)
            val spriteID = cursor.getColumnIndex(sprite)
            if (nameID >= 0 && genderID >= 0 && spriteID >= 0)
                allPokemonTrainers.add(
                    PokemonTrainer(
                        cursor.getInt(idID),
                        cursor.getString(nameID),
                        cursor.getString(genderID),
                        cursor.getString(spriteID)
                    )
                )
        }

        return allPokemonTrainers.toList()
    }
}
