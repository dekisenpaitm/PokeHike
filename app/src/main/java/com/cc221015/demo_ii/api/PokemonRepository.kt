package com.cc221015.demo_ii.api

import com.cc221015.demo_ii.data.PokemonApiResult
import com.cc221015.demo_ii.data.PokemonsApiResult
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// This object is responsible for handling interactions with the PokeAPI.
object PokemonRepository {

    // Retrofit service instance for making API requests.
    private val service: PokemonService

    // Initialize the Retrofit service within this object.
    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://pokeapi.co/api/v2/") // Base URL for the PokeAPI.
            .addConverterFactory(GsonConverterFactory.create()) // JSON to Kotlin object converter.
            .build()

        // Create a service instance for making API calls.
        service = retrofit.create(PokemonService::class.java)
    }

    // Retrieve a list of Pokemons from the API. Limited to 151 (Can be expanded if necessary [Updates])
    fun listPokemons(limit: Int = 151): PokemonsApiResult? {
        val call = service.listPokemons(limit)
        return call.execute().body() // Execute the call and return the result.
    }

    // Retrieve information about a specific Pokemon by its number.
    fun getPokemon(number: Int): PokemonApiResult? {
        val call = service.getPokemon(number)
        return call.execute().body() // Execute the call and return the result.
    }
}
