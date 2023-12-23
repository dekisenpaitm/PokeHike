package com.cc221015.demo_ii.data

import com.cc221015.demo_ii.domain.PokemonType

// Represents the result of a list of Pokemons fetched from the API.
data class PokemonsApiResult (
    val count: Int,           // Total count of Pokemons in the API.
    val previous: String?,    // URL of the previous page of Pokemons, if available.
    val next: String?,        // URL of the next page of Pokemons, if available.
    val results: List<PokemonResult>  // List of PokemonResult objects containing Pokemon names and URLs.
)

// Represents an individual Pokemon result.
data class PokemonResult(
    val name: String,  // The name of the Pokemon.
    val url: String   // The URL of the Pokemon's details.
)

// Represents the result of detailed Pokemon information fetched from the API.
data class PokemonApiResult(
    val id: Int,                  // The unique ID of the Pokemon.
    val name: String,             // The name of the Pokemon.
    val types: List<PokemonTypeSlot>  // List of PokemonTypeSlot objects containing type information.
)

// Represents a slot for a Pokemon's type.
data class PokemonTypeSlot(
    val slot: Int,        // The slot number of the type (e.g., 1 for the primary type).
    val type: PokemonType  // The actual type of the Pokemon (e.g., Water, Fire).
)
