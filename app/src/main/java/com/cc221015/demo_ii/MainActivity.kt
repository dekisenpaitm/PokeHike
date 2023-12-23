package com.cc221015.demo_ii

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.cc221015.demo_ii.data.PokemonBaseHandler
import com.cc221015.demo_ii.data.TrainerBaseHandler
import com.cc221015.demo_ii.ui.theme.Demo_IITheme
import com.cc221015.demo_ii.viewModel.MainViewModel
import com.cc221015.demo_ii.viewModel.PokemonViewModel

// The main activity of the application.
class MainActivity : ComponentActivity() {

    // Database handler for Pokemon trainers.
    private val db = TrainerBaseHandler(this)

    // ViewModel for the main screen.
    private val mainViewModel = MainViewModel(db)

    // Database handler for Pokemon entities.
    private val pdb = PokemonBaseHandler(this)

    // ViewModel for the Pokemon-related view.
    private val pokemonViewModel = PokemonViewModel(pdb)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Demo_IITheme {
                // A surface container using the 'background' color from the theme.
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // Initialize and fetch Pokemon trainers from the database.
                    db.getPokemonTrainers()

                    // Create and display the main view with associated ViewModels.
                    MainView(mainViewModel, pokemonViewModel)
                }
            }
        }
    }
}
