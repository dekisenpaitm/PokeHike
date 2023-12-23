package com.cc221015.demo_ii.stateModel

import com.cc221015.demo_ii.data.PokemonTrainer
import com.cc221015.demo_ii.Screen

// Represents the state of the main screen in the application.
data class MainViewState(
    val pokemonTrainers: List<PokemonTrainer> = emptyList(),  // List of Pokemon trainers in the view.
    val editPokemonTrainer: PokemonTrainer = PokemonTrainer(0,"", "",""),  // Pokemon trainer being edited.
    val selectedScreen: Screen = Screen.First,  // The selected screen/tab in the UI.
    val openDialog: Boolean = false  // Indicates whether a dialog is open in the UI.
)
