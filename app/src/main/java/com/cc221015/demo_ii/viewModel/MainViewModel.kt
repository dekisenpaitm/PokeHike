package com.cc221015.demo_ii.viewModel

import androidx.lifecycle.ViewModel
import com.cc221015.demo_ii.data.PokemonTrainer
import com.cc221015.demo_ii.data.TrainerBaseHandler
import com.cc221015.demo_ii.stateModel.MainViewState
import com.cc221015.demo_ii.ui.theme.Screen
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class MainViewModel(private val db: TrainerBaseHandler): ViewModel() {
    private val _mainViewState = MutableStateFlow(MainViewState())
    val mainViewState: StateFlow<MainViewState> = _mainViewState.asStateFlow()

    fun save(pokemonTrainer: PokemonTrainer){
        db.insertPokemonTrainer(pokemonTrainer)
    }

    fun getPokemonTrainer() {
        _mainViewState.update { it.copy(pokemonTrainers = db.getPokemonTrainers()) }
    }

    fun selectScreen(screen: Screen){
        _mainViewState.update { it.copy(selectedScreen = screen) }
    }

    fun clickDelete(pokemonTrainer: PokemonTrainer){
        db.deletePokemonTrainer(pokemonTrainer)
        getPokemonTrainer()
    }

    fun editPokemonTrainer(pokemonTrainer: PokemonTrainer){
        _mainViewState.update { it.copy(openDialog = true, editPokemonTrainer = pokemonTrainer) }
    }

    fun savePokemonTrainer(pokemonTrainer: PokemonTrainer){
        _mainViewState.update { it.copy(openDialog = false) }
        db.updatePokemonTrainer(pokemonTrainer)
        getPokemonTrainer()
    }

    fun dismissDialog(){
        _mainViewState.update { it.copy(openDialog = false) }
    }
}