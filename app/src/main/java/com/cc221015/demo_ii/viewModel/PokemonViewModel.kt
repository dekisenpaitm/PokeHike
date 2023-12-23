package com.cc221015.demo_ii.viewModel

import androidx.lifecycle.ViewModel
import com.cc221015.demo_ii.api.PokemonRepository
import com.cc221015.demo_ii.data.PokemonBaseHandler
import com.cc221015.demo_ii.domain.Pokemon
import com.cc221015.demo_ii.stateModel.PokemonViewState
import com.cc221015.demo_ii.Screen
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

// ViewModel responsible for managing Pokemon-related data and interactions.
class PokemonViewModel(private val db: PokemonBaseHandler) : ViewModel() {
	private val _pokemonViewState = MutableStateFlow(PokemonViewState())
	val pokemonViewState: StateFlow<PokemonViewState> = _pokemonViewState.asStateFlow()

	// Fetch and load all Pokemon from the database.
	fun getPokemon() {
		_pokemonViewState.update { it.copy(pokemons = db.getPokemons()) }
	}

	// Fetch and load favorite Pokemon from the database.
	fun getFavPokemon() {
		_pokemonViewState.update { it.copy(pokemons = db.getFavPokemons()) }
	}

	// Select a screen in the UI.
	fun selectScreen(screen: Screen) {
		_pokemonViewState.update { it.copy(selectedScreen = screen) }
	}

	// Unlike a Pokemon and update the view state.
	fun unlikePokemon(pokemon: Pokemon, favorite: Boolean) {
		db.unlikePokemon(pokemon)
		if (favorite) {
			_pokemonViewState.update { it.copy(pokemons = db.getFavPokemons()) }
		} else {
			_pokemonViewState.update { it.copy(pokemons = db.getPokemons()) }
		}
	}

	// Like a Pokemon and update the view state.
	fun likePokemon(pokemon: Pokemon, favorite: Boolean) {
		db.likePokemon(pokemon)
		if (favorite) {
			_pokemonViewState.update { it.copy(pokemons = db.getFavPokemons()) }
		} else {
			_pokemonViewState.update { it.copy(pokemons = db.getPokemons()) }
		}
	}

	// Delete all favorited Pokemon and update the view state.
	fun deleteAllFavedPokemon() {
		db.deleteFavPokemons()
		_pokemonViewState.update { it.copy(pokemons = db.getFavPokemons()) }
	}

	// Load Pokemon data from an API and insert it into the database.
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
							if (pokemonApiResult.types.count() > 1) {
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
