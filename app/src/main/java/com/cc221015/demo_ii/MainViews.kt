package com.cc221015.demo_ii

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.BottomNavigation
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExposedDropdownMenuBox
import androidx.compose.material.ExposedDropdownMenuDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.cc221015.demo_ii.data.PokemonTrainer
import com.cc221015.demo_ii.domain.Pokemon
import com.cc221015.demo_ii.viewModel.MainViewModel
import com.cc221015.demo_ii.viewModel.PokemonViewModel
import java.util.Locale


// https://kotlinlang.org/docs/sealed-classes.html
// Define a sealed class named 'Screen' to represent different screens in the app.
// Sealed classes are used for representing restricted class hierarchies, where a value can have one of the types from a limited set.
sealed class Screen(val route: String) {
    // Object declarations for different screens, each with a unique route string.
    // These objects extend the Screen class and provide specific routes for navigation.
    // The use of objects here ensures that only a single instance of each screen type exists.

    object First : Screen("first")   // Represents the first screen with route "first"
    object Second : Screen("second") // Represents the second screen with route "second"
    object Third : Screen("third")   // Represents the third screen with route "third"
    object Fourth : Screen("fourth") // Represents the fourth screen with route "fourth"
}

// Usage: This sealed class is particularly useful in a Jetpack Compose navigation setup,
// where you need to define routes for different composables in a type-safe manner.
// Each screen is represented as a singleton object, making it easy to reference them throughout the app.

// Opt-in for the experimental Material3 API which is still in development.
@OptIn(ExperimentalMaterial3Api::class)
// MainView is a Composable function that creates the main view of your app.
@Composable
fun MainView(mainViewModel: MainViewModel, pokemonViewModel: PokemonViewModel) {
    // Collect the current state of the main view from the MainViewModel.
    val state = mainViewModel.mainViewState.collectAsState()
    // Create or remember a NavController for navigation between screens.
    val navController = rememberNavController()

    // Scaffold is a material design container that includes standard layout structures.
    Scaffold(
        // Define the bottom navigation bar for the Scaffold.
        bottomBar = { BottomNavigationBar(navController, state.value.selectedScreen) }
    ) {
        // NavHost manages composable destinations for navigation.
        NavHost(
            navController = navController,
            modifier = Modifier.padding(it), // Apply padding from the Scaffold.
            startDestination = Screen.First.route // Define the starting screen.
        ) {
            // Define the composable function for the 'First' route.
            composable(Screen.First.route) {
                SetBackgroundMain() // Set the main background for this view.
                mainViewModel.getPokemonTrainer() // Fetch the Pokemon trainer information.
                // Check if the pokemon trainers list is not empty.
                if (state.value.pokemonTrainers.isNotEmpty()) {
                    mainViewModel.selectScreen(Screen.First)
                    mainScreen(mainViewModel) // Show the main screen if trainers exist.
                } else {
                    mainViewModel.selectScreen(Screen.First)
                    landingPage(mainViewModel) // Show the landing page otherwise.
                }
            }

            // Define the composable function for the 'Second' route.
            composable(Screen.Second.route) {
                SetBackgroundMain()
                mainViewModel.getPokemonTrainer()
                // Similar logic as the 'First' route but for the second screen.
                if (state.value.pokemonTrainers.isNotEmpty()) {
                    mainViewModel.selectScreen(Screen.Second)
                    pokemonViewModel.getFavPokemon()
                    MyPokemonList(pokemonViewModel, true)
                } else {
                    mainViewModel.selectScreen(Screen.Second)
                    ErrorScreen()
                }
            }

            // Define the composable function for the 'Third' route.
            composable(Screen.Third.route) {
                SetBackgroundMain()
                mainViewModel.getPokemonTrainer()
                // Similar logic as above for the third screen.
                if (state.value.pokemonTrainers.isNotEmpty()) {
                    mainViewModel.selectScreen(Screen.Third)
                    pokemonViewModel.getPokemon()
                    MyPokemonList(pokemonViewModel, false)
                } else {
                    mainViewModel.selectScreen(Screen.Third)
                    ErrorScreen()
                }
            }

            // Define the composable function for the 'Fourth' route.
            composable(Screen.Fourth.route) {
                mainViewModel.getPokemonTrainer()
                SetBackgroundMain()
                // Similar logic as above for the fourth screen.
                if (state.value.pokemonTrainers.isNotEmpty()) {
                    mainViewModel.selectScreen(Screen.Fourth)
                    DisplayTrainerProfile(mainViewModel, pokemonViewModel)
                } else {
                    mainViewModel.selectScreen(Screen.Fourth)
                    ErrorScreen()
                }
            }
        }
    }
}

// Define a Composable function for creating a Bottom Navigation Bar.
@Composable
fun BottomNavigationBar(navController: NavHostController, selectedScreen: Screen) {
    // BottomNavigation is a Material Design component that provides bottom navigation.
    BottomNavigation(
        backgroundColor = MaterialTheme.colorScheme.primary // Set the background color of the navigation bar.
    ) {
        // NavigationBarItem for the 'First' screen.
        NavigationBarItem(
            selected = (selectedScreen == Screen.First), // Determine if this item is selected based on the current screen.
            onClick = { navController.navigate(Screen.First.route) }, // Define action on click, navigating to the 'First' route.
            icon = { Icon(imageVector = Icons.Default.Home, contentDescription = "") } // Set the icon for this item.
        )

        // NavigationBarItem for the 'Second' screen.
        NavigationBarItem(
            // Similar configuration as the first item but for the 'Second' screen.
            selected = (selectedScreen == Screen.Second),
            onClick = { navController.navigate(Screen.Second.route) },
            icon = { Icon(imageVector = Icons.Default.Favorite, contentDescription = "") }
        )

        // NavigationBarItem for the 'Third' screen.
        NavigationBarItem(
            // Similar configuration as above for the 'Third' screen.
            selected = (selectedScreen == Screen.Third),
            onClick = { navController.navigate(Screen.Third.route) },
            icon = { Icon(imageVector = Icons.Default.List, contentDescription = "") }
        )

        // NavigationBarItem for the 'Fourth' screen.
        NavigationBarItem(
            // Similar configuration as above for the 'Fourth' screen.
            selected = (selectedScreen == Screen.Fourth),
            onClick = { navController.navigate(Screen.Fourth.route) },
            icon = { Icon(imageVector = Icons.Default.AccountCircle, contentDescription = "") }
        )
    }
}

// Suppresses lint warnings for using discouraged or experimental APIs.
@SuppressLint("DiscouragedApi")
@OptIn(ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class)
// Defines a Composable function for the landing page.
@Composable
fun landingPage(mainViewModel: MainViewModel) {
    // State for tracking the expansion status of the dropdown menu.
    var isExpanded by remember { mutableStateOf(false) }
    // State for storing the index of the selected trainer's image.
    var selectedTrainerIndex by remember { mutableStateOf("") }
    // State for storing the selected trainer's name.
    var trainerName by remember { mutableStateOf("") }

    // List of trainer image resource names.
    val trainerImageResources = listOf(
        "trainer1",
        "trainer2",
        "trainer3",
        "trainer4",
        "trainer5",
        "trainer6",
        "trainer7",
        "trainer8",
        "trainer9",
        "trainer10",
        "trainer11"
    )
    // Column layout for vertical alignment of UI elements.
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Async image painter for loading the trainer's image.
        val painter = rememberAsyncImagePainter(model = selectedTrainerIndex)
        Image(
            painter = painter,
            contentDescription = "Pokemon Image",
            contentScale = ContentScale.FillHeight,
            modifier = Modifier
                .size(120.dp)
                .clip(MaterialTheme.shapes.medium)
                .padding(10.dp)
        )
        // Exposed dropdown menu box for selecting a trainer.
        ExposedDropdownMenuBox(
            expanded = isExpanded,
            onExpandedChange = { isExpanded = it }
        ) {
            // TextField for displaying the selected trainer's name.
            TextField(
                value = trainerName,
                onValueChange = {},
                readOnly = true,
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded)
                },
            )
            // Dropdown menu for selecting a trainer.
            ExposedDropdownMenu(
                expanded = isExpanded,
                onDismissRequest = { isExpanded = false }
            ) {
                // Iterating over the list of trainer image resources.
                for (i in trainerImageResources) {
                    var currentTrainerName = i
                    val resourceId = LocalContext.current.resources.getIdentifier(
                        i,
                        "drawable",
                        LocalContext.current.packageName
                    )
                    val imageUrl = "android.resource://${LocalContext.current.packageName}/$resourceId"
                    // DropdownMenuItem for each trainer.
                    DropdownMenuItem(text = { TextBox(text = currentTrainerName) }, onClick = { selectedTrainerIndex = imageUrl; trainerName = currentTrainerName })
                }
            }
        }

        // State and TextField for inputting the trainer's name.
        var name by remember { mutableStateOf("") }
        TextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Name") },
            modifier = Modifier.padding(top = 20.dp)
        )
        // State and TextField for inputting the trainer's gender.
        var gender by remember { mutableStateOf("") }
        TextField(
            value = gender,
            onValueChange = { gender = it },
            label = { Text("Gender") },
            modifier = Modifier.padding(top = 20.dp)
        )
        // Button for saving the new trainer information.
        Button(
            onClick = {
                mainViewModel.save(PokemonTrainer(null,name,gender,trainerName))
                      mainViewModel.getPokemonTrainer()},
            modifier = Modifier.padding(top = 20.dp)
        ) {
            Text(text = "Create New Trainer", fontSize = 20.sp)
        }
    }
}
// Helper Composable function for displaying errors.
@Composable
fun ErrorScreen(){
    Text(text = "Looks like you didn't create your user yet, please make sure to create one before using PokeHike!")
}

// Helper Composable function for displaying text.
@Composable
fun TextBox(text:String){
    Text(text = text)
}

// Composable function for the main screen of the app.
@Composable
fun mainScreen(mainViewModel: MainViewModel){
    // Displaying text, potentially a placeholder for more dynamic content.
    Text(text = "This will be the entrance with a description")
}

// Helper Composable function for displaying the background.
@Composable
fun SetBackgroundMain() {
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.hills_background),
            contentDescription = "Login_Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
    }
}

// Composable function to display values related to a Pokemon Trainer.
@Composable
fun TrainerValues(mainViewModel: MainViewModel) {
    // Retrieves declared fields from the PokemonTrainer class for reflection.
    val pokemonTrainer = PokemonTrainer::class.java.declaredFields
    // Sorts the attributes based on the length of their names.
    val sortedAttributes = pokemonTrainer.sortedBy { it.name.length }

    // LazyColumn is used for efficient, scrollable lists.
    LazyColumn(
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        // Iterates over the sorted attributes to create list items.
        items(sortedAttributes) { pokemonTrainerField ->
            // Ensures that the field is accessible.
            pokemonTrainerField.isAccessible
            // Filter out fields with specific names.
            if (!pokemonTrainerField.name.contains("stable", false)
                && !pokemonTrainerField.name.contains("sprite", false)) {
                // Calls TrainerItem Composable for each field.
                TrainerItem(pokemonTrainerField.name, mainViewModel)
            }
        }
    }
}

// This function uses reflection to dynamically create UI components based on the fields of the PokemonTrainer class.
// It filters out certain fields and displays the rest using the TrainerItem Composable.


// Composable function to display individual trainer items, using the ExperimentalLayoutApi.
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun TrainerItem(trainerValue: String, mainViewModel: MainViewModel) {
    // Collects the state from the MainViewModel.
    val state = mainViewModel.mainViewState.collectAsState()

    // Accesses a specific property of the PokemonTrainer class using reflection.
    val trainerProperty = PokemonTrainer::class.java.getDeclaredField(trainerValue)
    trainerProperty.isAccessible = true

    // FlowRow is used for arranging items in a row that can wrap onto multiple lines.
    FlowRow(
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .background(color = Color(255, 255, 255, 125))
            .border(color = Color.Black, width = 1.dp),
        maxItemsInEachRow = 4 // Maximum items in each row.
    ) {
        // Box for the label of the trainer property.
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = trainerValue, // The name of the trainer property.
                fontSize = 16.sp,
                color = Color.Black
            )
        }
        // Box for the value of the trainer property.
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "${trainerProperty.get(state.value.pokemonTrainers[0])}",
                fontSize = 16.sp,
                color = Color.Black
            )
        }
    }
}

// This function utilizes reflection to dynamically access properties of the PokemonTrainer class.
// It displays each property as a text label with its corresponding value in a flow layout.


// Suppresses lint warnings for discouraged API usage.
@SuppressLint("DiscouragedApi")
// Composable function to display the profile of a Pokemon Trainer.
@Composable
fun DisplayTrainerProfile(mainViewModel: MainViewModel, pokemonViewModel: PokemonViewModel) {
    // Collects the current state from the MainViewModel.
    val state = mainViewModel.mainViewState.collectAsState()

    // A Column layout to vertically arrange elements.
    Column(verticalArrangement = Arrangement.Center, modifier = Modifier.fillMaxSize()) {
        // A LazyColumn for efficiently displaying a scrollable list.
        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth().weight(1f)
        ) {
            // Displaying the trainer's image.
            item {
                Box() {
                    // Retrieve the resource ID for the trainer's sprite.
                    val resourceId = LocalContext.current.resources.getIdentifier(
                        state.value.pokemonTrainers[0].sprite,
                        "drawable",
                        LocalContext.current.packageName
                    )
                    // Construct the image URL.
                    val imageUrl = "android.resource://${LocalContext.current.packageName}/$resourceId"
                    val painter = rememberAsyncImagePainter(model = imageUrl)
                    // Display the image.
                    Image(
                        painter = painter,
                        contentDescription = "Pokemon Image",
                        contentScale = ContentScale.FillBounds,
                        modifier = Modifier
                            .size(150.dp)
                            .clip(MaterialTheme.shapes.medium)
                            .padding(10.dp)
                    )
                }
            }
        }

        // Display trainer's attributes using TrainerValues composable.
        TrainerValues(mainViewModel = mainViewModel)

        // LazyColumn for 'Update Trainer' button.
        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth().weight(0.3f)
        ) {
            item {
                // Button to update the trainer.
                Button(
                    onClick = { mainViewModel.editPokemonTrainer(state.value.pokemonTrainers[0]) },
                    modifier = Modifier.padding(top = 20.dp)
                ) {
                    Text(text = "Update Trainer", fontSize = 20.sp)
                }
            }
        }

        // LazyColumn for 'Delete Trainer' button.
        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth().weight(0.3f)
        ) {
            item {
                // Button to delete the trainer.
                Button(
                    onClick = {
                        mainViewModel.deletePokemonTrainer(state.value.pokemonTrainers[0])
                        pokemonViewModel.deleteAllFavedPokemon()
                    },
                    modifier = Modifier.padding(top = 20.dp)
                ) {
                    Text(text = "deleteTrainer", fontSize = 20.sp)
                }
            }
        }
    }

    // Call to the editTrainerModel function, possibly for editing trainer details.
    Column {
        editTrainerModel(mainViewModel)
    }
}

// This function is designed to display detailed information about a Pokemon Trainer,
// including an image, attributes, and options to update or delete the trainer.



// Opt-in for Experimental Material3 API and define the Composable function for editing a trainer's model.
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun editTrainerModel(mainViewModel: MainViewModel) {
    // Collect state from the MainViewModel.
    val state = mainViewModel.mainViewState.collectAsState()

    // Check if the dialog state is open.
    if (state.value.openDialog) {
        // Remember saveable states for the trainer's properties.
        var id by rememberSaveable { mutableStateOf(state.value.editPokemonTrainer.id) }
        var name by rememberSaveable { mutableStateOf(state.value.editPokemonTrainer.name) }
        var gender by rememberSaveable { mutableStateOf(state.value.editPokemonTrainer.gender) }
        var sprite by rememberSaveable { mutableStateOf(state.value.editPokemonTrainer.sprite) }

        // AlertDialog to show the editing interface.
        AlertDialog(
            onDismissRequest = { mainViewModel.dismissDialog() }, // Handle dialog dismissal.
            text = {
                // Column layout for the text fields.
                Column {
                    // Text field for editing the username.
                    TextField(
                        modifier = Modifier.padding(top = 20.dp),
                        value = name,
                        onValueChange = { newText -> name = newText },
                        label = { Text(text = "Change Username") }
                    )
                    // Text field for editing the gender.
                    TextField(
                        modifier = Modifier.padding(top = 20.dp),
                        value = gender,
                        onValueChange = { newText -> gender = newText },
                        label = { Text(text = "Change Gender") }
                    )
                }
            },
            confirmButton = {
                // Button for saving changes.
                Button(onClick = {
                    mainViewModel.savePokemonTrainer(PokemonTrainer(id, name, gender, sprite))
                }) {
                    Text(stringResource(R.string.editmodal_button_save))
                }
            }
        )
    }
}

// This function provides an interface for editing a Pokemon Trainer's details, using an AlertDialog for input.
// It uses saveable states to preserve data during configuration changes and interacts with the MainViewModel for data handling.

// Composable function to display a list of Pokemon.
@Composable
fun MyPokemonList(pokemonViewModel: PokemonViewModel, favorite: Boolean) {
    // Collecting the list of Pokemons from the ViewModel.
    val pokemonList = pokemonViewModel.pokemonViewState.collectAsState().value.pokemons

    // Using a Column to layout elements vertically.
    Column {
        // A Row for displaying the title, with dynamic text based on the 'favorite' flag.
        Row(modifier = Modifier.height(50.dp).fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center) {

            val text = if (favorite) "My Favorites" else "Pokedex"
            Text(text = text, fontSize = 40.sp, color = Color.White)
        }

        // A Row to display the list of Pokemon.
        Row {
            // Calling PokemonList Composable to display the actual list.
            PokemonList(pokemonList = pokemonList, pokemonViewModel, favorite)
        }
    }
}

// This function decides whether to display the user's favorite Pokemon or the entire Pokedex based on the 'favorite' flag.
// It uses a Column for vertical arrangement and dynamically sets the title text.


// Composable function to display a list of Pokemon.
@Composable
fun PokemonList(pokemonList: List<Pokemon?>, pokemonViewModel: PokemonViewModel, favorite: Boolean) {
    // LazyColumn is used for efficiently displaying a list that can be scrolled.
    // It only renders the items that are currently visible on screen.
    LazyColumn {
        // Iterating over each Pokemon in the pokemonList.
        items(pokemonList) { pokemon ->
            // PokemonItem Composable is called for each Pokemon in the list.
            // It displays individual Pokemon details.
            PokemonItem(pokemon = pokemon, pokemonViewModel = pokemonViewModel, favorite)
        }
    }
}

// This function creates a scrollable list of Pokemon, leveraging LazyColumn for performance.
// Each item in the list is represented by the PokemonItem Composable.

// Opt-in for Experimental Layout API and define the Composable function for displaying individual Pokemon items.
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun PokemonItem(pokemon: Pokemon?, pokemonViewModel: PokemonViewModel, favorite: Boolean) {
    // Spacer to add some space before the item starts.
    Spacer(modifier = Modifier.height(5.dp).fillMaxWidth())

    // FlowRow is used to arrange items in a horizontal flow that wraps.
    FlowRow(
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .background(color = Color(255, 255, 255, 125))
            .border(color = Color.Black, width = 1.dp),
        maxItemsInEachRow = 4 // Sets the max number of items in each row.
    ) {
        // Modifier for individual items in the FlowRow.
        val itemModifier = Modifier.clip(RoundedCornerShape(8.dp))

        // Box for displaying the Pokemon image.
        Box(contentAlignment = Alignment.Center) {
            if (pokemon != null) {
                AsyncImage(
                    model = pokemon.imageUrl,
                    contentDescription = "Pokemon Image",
                    contentScale = ContentScale.FillHeight,
                    modifier = Modifier.size(100.dp).clip(MaterialTheme.shapes.medium)
                )
            }
        }

        // Box for displaying the Pokemon's name.
        Box(modifier = itemModifier.fillMaxHeight().weight(1f), contentAlignment = Alignment.Center) {
            if (pokemon != null) {
                Text(
                    text = pokemon.name.replaceFirstChar { it.titlecase() },
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(top = 8.dp),
                )
            }
        }

        // Box for displaying the Pokemon's types.
        Box(modifier = itemModifier.fillMaxHeight().weight(1f), contentAlignment = Alignment.Center) {
            // List of specific Pokemon types.
            val specificTypes = listOf("fire", "water", "electric", "grass", "bug", "normal", "poison", "ground", "ghost", "psychic", "fairy", "fighting", "rock", "dragon", "ice")
            if (pokemon != null) {
                val matchingTypes = listOf(pokemon.type0, pokemon.type1).mapNotNull { type ->
                    specificTypes.find { specificType -> type.toString().contains(specificType, ignoreCase = true) }?.toLowerCase(Locale.ROOT)
                }
                if (matchingTypes.isNotEmpty()) {
                    Column {
                        matchingTypes.forEach { pokemonType ->
                            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
                                Text(text = pokemonType.replaceFirstChar { it.uppercase() })
                            }
                        }
                    }
                }
            }
        }

        // Box for the like/unlike button.
        Box(modifier = itemModifier.fillMaxHeight().weight(1f), contentAlignment = Alignment.Center) {
            if (pokemon != null) {
                IconButton(onClick = {
                    if (pokemon.liked == "true") {
                        pokemonViewModel.unlikePokemon(pokemon, favorite)
                    } else {
                        pokemonViewModel.likePokemon(pokemon, favorite)
                    }
                }) {
                    val tint = if (pokemon.liked == "true") Color.Red else Color.Gray
                    Icon(Icons.Default.Favorite, "Like", tint = tint, modifier = Modifier.size(20.dp))
                }
            }
        }
    }
}

// This function creates a UI for each Pokemon item. It includes an image, name, types, and a like button.
// The layout is done using FlowRow for a responsive design. The like button interacts with the PokemonViewModel for state changes.





