package com.cc221015.demo_ii.domain


data class Pokemon (
	val number: Int,
	val name: String,
	val type0: String,
	val type1: String,
	val imageUrl: String = "https://img.pokemondb.net/sprites/diamond-pearl/normal/$name.png",
	val liked: String = "false",
)
