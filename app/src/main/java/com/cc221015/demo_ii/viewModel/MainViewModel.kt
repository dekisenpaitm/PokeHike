package com.cc221015.demo_ii.viewModel

import androidx.lifecycle.ViewModel
import com.cc221015.demo_ii.data.PokemonTrainer
import com.cc221015.demo_ii.data.TrainerBaseHandler
import com.cc221015.demo_ii.stateModel.MainViewState
import com.cc221015.demo_ii.Screen
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

// ViewModel responsible for managing the main screen's data and logic.
class MainViewModel(private val db: TrainerBaseHandler) : ViewModel() {
    private val _mainViewState = MutableStateFlow(MainViewState())
    val mainViewState: StateFlow<MainViewState> = _mainViewState.asStateFlow()

    // Save a PokemonTrainer in the database.
    fun save(pokemonTrainer: PokemonTrainer) {
        db.insertPokemonTrainer(pokemonTrainer)
    }

    // Get the list of PokemonTrainers and update the view state.
    fun getPokemonTrainer() {
        _mainViewState.update { it.copy(pokemonTrainers = db.getPokemonTrainers()) }
    }

    // Select a screen in the UI.
    fun selectScreen(screen: Screen) {
        _mainViewState.update { it.copy(selectedScreen = screen) }
    }

    // Delete a PokemonTrainer from the database.
    fun deletePokemonTrainer(pokemonTrainer: PokemonTrainer) {
        db.deletePokemonTrainer(pokemonTrainer)
        getPokemonTrainer()
    }

    // Edit a PokemonTrainer and open a dialog for editing.
    fun editPokemonTrainer(pokemonTrainer: PokemonTrainer) {
        _mainViewState.update { it.copy(openDialog = true, editPokemonTrainer = pokemonTrainer) }
    }

    // Save changes to a PokemonTrainer and close the editing dialog.
    fun savePokemonTrainer(pokemonTrainer: PokemonTrainer) {
        _mainViewState.update { it.copy(openDialog = false) }
        db.updatePokemonTrainer(pokemonTrainer)
        getPokemonTrainer()
    }

    // Dismiss the editing dialog.
    fun dismissDialog() {
        _mainViewState.update { it.copy(openDialog = false) }
    }
}
