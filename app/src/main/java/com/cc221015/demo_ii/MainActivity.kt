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
import com.cc221015.demo_ii.ui.theme.MainView
import com.cc221015.demo_ii.viewModel.MainViewModel
import com.cc221015.demo_ii.viewModel.PokemonViewModel

class MainActivity : ComponentActivity() {

    private val db = TrainerBaseHandler(this)
    private val mainViewModel = MainViewModel(db)
    private val pdb = PokemonBaseHandler(this)
    private val pokemonViewModel = PokemonViewModel(pdb)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Demo_IITheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainView(mainViewModel, pokemonViewModel)
                }
            }
        }
    }
}
