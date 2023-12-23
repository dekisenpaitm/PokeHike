package com.cc221015.demo_ii.domain

// Represents a Pokemon entity with various attributes.
data class Pokemon (
	val number: Int,  // The unique identification number of the Pokemon.
	val name: String,  // The name of the Pokemon.
	val type0: String,  // The primary type of the Pokemon (e.g., Water, Fire).
	val type1: String,  // The secondary type of the Pokemon (e.g., Flying, Psychic).
	val imageUrl: String = "https://img.pokemondb.net/sprites/diamond-pearl/normal/$name.png",
	// The URL of the Pokemon's image. Defaults to a standard Pokemon sprite URL.
	val liked: String = "false"  // Indicates whether the Pokemon is liked by the user (defaults to "false").
)
