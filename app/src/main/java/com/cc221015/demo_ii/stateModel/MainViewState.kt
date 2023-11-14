package com.cc221015.demo_ii.stateModel

import com.cc221015.demo_ii.data.PokemonTrainer
import com.cc221015.demo_ii.ui.theme.Screen

data class MainViewState(
    val pokemonTrainers: List<PokemonTrainer> = emptyList(),
    val editPokemonTrainer: PokemonTrainer = PokemonTrainer(0,"", "",""),
    val selectedScreen: Screen = Screen.First,
    val openDialog: Boolean = false
)
