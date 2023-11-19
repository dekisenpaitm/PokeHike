package com.cc221015.demo_ii.viewModel

import androidx.lifecycle.ViewModel
import com.cc221015.demo_ii.api.PokemonRepository
import com.cc221015.demo_ii.data.PokemonBaseHandler
import com.cc221015.demo_ii.domain.Pokemon
import com.cc221015.demo_ii.stateModel.PokemonViewState
import com.cc221015.demo_ii.ui.theme.Screen
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PokemonViewModel(private val db : PokemonBaseHandler) : ViewModel() {
	private val _pokemonViewState = MutableStateFlow(PokemonViewState())
	val pokemonViewState: StateFlow<PokemonViewState> = _pokemonViewState.asStateFlow()


	fun getPokemon() {
		_pokemonViewState.update { it.copy(pokemons = db.getPokemons()) }
	}

	fun getFavPokemon() {
		_pokemonViewState.update { it.copy(pokemons = db.getFavPokemons()) }
	}

	fun selectScreen(screen: Screen){
		_pokemonViewState.update { it.copy(selectedScreen = screen) }
	}

	fun unlikePokemon(pokemon: Pokemon, favorite: Boolean){
		db.unlikePokemon(pokemon)
		if(favorite) {
			_pokemonViewState.update { it.copy(pokemons = db.getFavPokemons()) }
		} else {
			_pokemonViewState.update { it.copy(pokemons = db.getPokemons()) }
		}
	}


	fun likePokemon(pokemon: Pokemon, favorite: Boolean){
		db.likePokemon(pokemon)
		if(favorite) {
			_pokemonViewState.update { it.copy(pokemons = db.getFavPokemons()) }
		} else {
			_pokemonViewState.update { it.copy(pokemons = db.getPokemons()) }
		}

	}

	fun deleteAllFavedPokemon() {
		db.deleteFavPokemons()
		_pokemonViewState.update { it.copy(pokemons = db.getFavPokemons()) }
		}


	private fun loadPokemons() {
		GlobalScope.launch(Dispatchers.IO) {
			val pokemonsApiResult = PokemonRepository.listPokemons()
			if (pokemonsApiResult != null) {
				println(pokemonsApiResult.results)
			}

			pokemonsApiResult?.results?.let { results ->
				val pokemonList = results.map { pokemonResult ->
					val number = pokemonResult.url
						.replace("https://pokeapi.co/api/v2/pokemon/", "")
						.replace("/", "").toInt()

					val pokemonApiResult = PokemonRepository.getPokemon(number)

					pokemonApiResult?.let {
						println("This is types: $pokemonApiResult")
						val pokemon = Pokemon(
							pokemonApiResult.id,
							pokemonApiResult.name,
							pokemonApiResult.types[0].type.toString(),
							if(pokemonApiResult.types.count() > 1) {
								pokemonApiResult.types[1].type.toString()
							} else {
								""
							}
						)
						db.insertPokemon(pokemon)

						pokemon
					}
				}

				_pokemonViewState.value = PokemonViewState(pokemonList)
			}
		}
	}
}
