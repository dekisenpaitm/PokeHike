package com.cc221015.demo_ii.data

import com.cc221015.demo_ii.domain.PokemonType

data class PokemonsApiResult (
    val count: Int,
    val previous: String?,
    val next: String?,
    val results: List<PokemonResult>
)

data class PokemonResult(
    val name: String,
    val url: String
)

data class PokemonApiResult(
    val id: Int,
    val name: String,
    val types: List<PokemonTypeSlot>
)

data class PokemonTypeSlot(
    val slot: Int,
    val type: PokemonType,
)