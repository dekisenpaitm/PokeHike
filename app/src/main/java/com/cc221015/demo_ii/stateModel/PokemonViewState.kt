package com.cc221015.demo_ii.stateModel

import com.cc221015.demo_ii.domain.Pokemon
import com.cc221015.demo_ii.ui.theme.Screen

data class PokemonViewState(
    val pokemons: List<Pokemon?> = emptyList(),
    val editPokemon: Pokemon = Pokemon(0,"", "","","",""),
    val selectedScreen: Screen = Screen.Third,
    val openDialog: Boolean = false
)
