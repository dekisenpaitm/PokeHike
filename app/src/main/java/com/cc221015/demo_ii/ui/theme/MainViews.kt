package com.cc221015.demo_ii.ui.theme

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
import androidx.compose.foundation.layout.FlowColumn
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.AlertDialog
import androidx.compose.material.BottomNavigation
import androidx.compose.material3.ButtonColors
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExposedDropdownMenuBox
import androidx.compose.material.ExposedDropdownMenuDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.cc221015.demo_ii.R
import com.cc221015.demo_ii.data.PokemonTrainer
import com.cc221015.demo_ii.domain.Pokemon
import com.cc221015.demo_ii.viewModel.MainViewModel
import com.cc221015.demo_ii.viewModel.PokemonViewModel
import java.lang.reflect.Field
import java.util.Locale


// https://kotlinlang.org/docs/sealed-classes.html
sealed class Screen(val route: String){
    object Zero: Screen("zero")
    object First: Screen("first")
    object Second: Screen("second")
    object Third: Screen("third")
    object Fourth: Screen("fourth")
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainView(mainViewModel: MainViewModel, pokemonViewModel: PokemonViewModel){
    val state = mainViewModel.mainViewState.collectAsState()
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {BottomNavigationBar(navController, state.value.selectedScreen)}
    ) {
        NavHost(
            navController = navController,
            modifier = Modifier.padding(it),
            startDestination = Screen.Zero.route
        ){
            composable(Screen.Zero.route){
                SetBackgroundMain()
                if(state.value.pokemonTrainers.count() > -1) {
                    mainViewModel.selectScreen(Screen.Zero)
                } else {
                    navController.navigate(Screen.First.route)
                }
                landingPage()
            }
            composable(Screen.First.route){
                SetBackgroundMain()
                mainViewModel.selectScreen(Screen.First)
                mainScreen(mainViewModel)
            }
            composable(Screen.Second.route){
                SetBackgroundMain()
                mainViewModel.selectScreen(Screen.Second)
                pokemonViewModel.getFavPokemon()
                MyPokemonList(pokemonViewModel, true)
            }
            composable(Screen.Third.route){
                SetBackgroundMain()
                mainViewModel.selectScreen(Screen.Third)
                pokemonViewModel.getPokemon()
                MyPokemonList(pokemonViewModel, false)
            }
            composable(Screen.Fourth.route){
                SetBackgroundMain()
                mainViewModel.selectScreen(Screen.Fourth)
                mainViewModel.getPokemonTrainer()
                DisplayTrainerProfile(mainViewModel)
            }
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavHostController, selectedScreen: Screen){
    BottomNavigation (
        backgroundColor = MaterialTheme.colorScheme.primary
    ) {
        NavigationBarItem(
            selected = (selectedScreen == Screen.First),
            onClick = { navController.navigate(Screen.First.route) },
            icon = { Icon(imageVector = Icons.Default.Home, contentDescription = "") })

        NavigationBarItem(
            selected = (selectedScreen == Screen.Second),
            onClick = { navController.navigate(Screen.Second.route) },
            icon = { Icon(imageVector = Icons.Default.Favorite, contentDescription = "") })

        NavigationBarItem(
            selected = (selectedScreen == Screen.Third),
            onClick = { navController.navigate(Screen.Third.route) },
            icon = { Icon(imageVector = Icons.Default.List, contentDescription = "") })

        NavigationBarItem(
            selected = (selectedScreen == Screen.Fourth),
            onClick = { navController.navigate(Screen.Fourth.route) },
            icon = { Icon(imageVector = Icons.Default.AccountCircle, contentDescription = "") })
    }
}

@SuppressLint("DiscouragedApi")
@OptIn(ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class)
@Composable
fun landingPage() {
    var isExpanded by remember { mutableStateOf(false) }
    var selectedTrainerIndex by remember { mutableStateOf("") }
    var trainerName by remember { mutableStateOf("") }
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

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
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

        ExposedDropdownMenuBox(
            expanded = isExpanded,
            onExpandedChange = { isExpanded = it }
        ) {
            TextField(
                value = trainerName,
                onValueChange = {},
                readOnly = true,
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded)
                },
            )

            ExposedDropdownMenu(
                expanded = isExpanded,
                onDismissRequest = { isExpanded = false }
            ) {
                for (i in trainerImageResources) {
                    var currentTrainerName = i
                    val resourceId = LocalContext.current.resources.getIdentifier(
                        i,
                        "drawable",
                        LocalContext.current.packageName
                    )
                    val imageUrl = "android.resource://${LocalContext.current.packageName}/$resourceId"
                    DropdownMenuItem(text = { TextBox(text = currentTrainerName)}, onClick = { selectedTrainerIndex = imageUrl; trainerName = currentTrainerName })
                }
            }
        }

        var name by remember { mutableStateOf("") }
        TextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Name") },
            modifier = Modifier.padding(top = 20.dp)
        )

        var gender by remember { mutableStateOf("") }
        TextField(
            value = gender,
            onValueChange = { gender = it },
            label = { Text("Gender") },
            modifier = Modifier.padding(top = 20.dp)
        )

        Button(
            onClick = { /* Perform action to create a new trainer */ },
            modifier = Modifier.padding(top = 20.dp)
        ) {
            Text(text = "Create New Trainer", fontSize = 20.sp)
        }
    }
}

@Composable
fun TextBox(text:String){
    Text(text = text)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun mainScreen(mainViewModel: MainViewModel){
    var name by rememberSaveable(stateSaver = TextFieldValue.Saver) { mutableStateOf(TextFieldValue("")) }
    var gender by rememberSaveable(stateSaver = TextFieldValue.Saver) { mutableStateOf(TextFieldValue("")) }
    var sprite by rememberSaveable(stateSaver = TextFieldValue.Saver) { mutableStateOf(TextFieldValue("")) }

    Column (
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.mainscreen_title),
            fontSize = 50.sp,
            style = TextStyle(
                fontFamily = FontFamily.Monospace,
                color = Color.White,
                shadow = Shadow(Color.Black, offset = Offset(1f,1f), 5f)
            )
        )

        Image(
            painter = painterResource(id = R.drawable.pokeball),
            modifier = Modifier.height(50.dp),
            contentScale = ContentScale.Inside,
            contentDescription = stringResource(R.string.decorative_android_icon)
        )

        Spacer(
            modifier = Modifier.height(50.dp)
        )

        // https://www.jetpackcompose.net/textfield-in-jetpack-compose
        TextField(
            value = name,
            onValueChange = {
                    newText -> name = newText
            },
            label = { Text(text = stringResource(R.string.mainscreen_field_name) ) }
        )

        TextField(
            modifier = Modifier.padding(top = 20.dp),
            value = gender,
            onValueChange = {
                    newText -> gender = newText
            },
            label = {
                Text(text = stringResource(R.string.mainscreen_field_uid))
            }
        )

        Button(
            onClick = { mainViewModel.save(PokemonTrainer(null,name.text,gender.text,sprite.text)) },
            modifier = Modifier.padding(top = 20.dp)
        ) {
            Text(text = stringResource(R.string.mainscreen_button_save), fontSize = 20.sp)
        }
    }
}

@Composable
fun SetBackgroundMain() {
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.pokeball_background),
            contentDescription = "Login_Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Composable
fun TrainerValues(mainViewModel: MainViewModel) {
    val pokemonTrainer = PokemonTrainer::class.java.declaredFields
    val sortedAttributes = pokemonTrainer.sortedBy { it.name.length}
    LazyColumn(
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        items(sortedAttributes){ pokemonTrainer ->
            pokemonTrainer.isAccessible
            if(!pokemonTrainer.name.contains("stable", false) && !pokemonTrainer.name.contains("sprite", false)) {
                    TrainerItem(pokemonTrainer.name, mainViewModel)
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun TrainerItem(trainerValue: String, mainViewModel: MainViewModel){
    val state = mainViewModel.mainViewState.collectAsState()
    val trainerProperty = PokemonTrainer::class.java.getDeclaredField(trainerValue)
    trainerProperty.isAccessible = true
    FlowRow(
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .background(color = Color(255, 255, 255, 125))
            .border(color = Color.Black, width = 1.dp),
        maxItemsInEachRow = 4
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = trainerValue,
                fontSize = 16.sp,
                color = Color.White
            )
        }
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "${trainerProperty.get(state.value.pokemonTrainers[0])}",
                fontSize = 16.sp,
                color = Color.White
            )

        }
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f),
            contentAlignment = Alignment.Center
        ) {
            IconButton(onClick = { mainViewModel.editPokemonTrainer(state.value.pokemonTrainers[0]) }) {
                Icon(
                    Icons.Default.Edit, "Edit",
                    tint = Color.White
                )
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun DisplayTrainerProfile(mainViewModel: MainViewModel) {
    val state = mainViewModel.mainViewState.collectAsState()
    Column(verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth().weight(1f)
        ) {
            item {
                Box() {
                    val painter =
                        rememberAsyncImagePainter(model = state.value.pokemonTrainers[0].sprite)
                    Image(
                        painter = painterResource(id = R.drawable.trainer1),
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
        TrainerValues(mainViewModel = mainViewModel)
        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth().weight(1f)
        ) {
            item {
                Button(
                    onClick = { /* implement delete profile */ },
                    modifier = Modifier.padding(top = 20.dp)
                ) {

                    Text(text = "deleteTrainer", fontSize = 20.sp)
                }
            }

        }
    }
}

@Composable
fun displayPokemonTrainer(mainViewModel: MainViewModel){
    val state = mainViewModel.mainViewState.collectAsState()

    // https://developer.android.com/jetpack/compose/lists
    LazyColumn (
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        item{
            Text(
                text = stringResource(R.string.displayPokemon_title),
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }

        items(state.value.pokemonTrainers){ pokemonTrainer ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.pokeball), // Replace with your image resource
                    contentDescription = "PokeballIcon",
                    modifier = Modifier
                        .height(30.dp)
                        .padding(1.dp) // Add padding
                )
                Column(modifier = Modifier
                    .weight(5f)
                    .padding(10.dp)) {
                    Text(text = "Name: ${pokemonTrainer.name}",
                        fontSize = 20.sp,
                        color = Color.White)

                    Text(text = "Gender: ${pokemonTrainer.gender}",
                        fontSize = 16.sp,
                        color = Color.White)

                    Text(text = "Sprite: ${pokemonTrainer.sprite}",
                        fontSize = 16.sp,
                        color = Color.White)

                }
                IconButton(onClick = { mainViewModel.editPokemonTrainer(pokemonTrainer) }) {
                    Icon(Icons.Default.Edit, "Edit",
                        tint = Color.White)
                }

                IconButton(onClick = { mainViewModel.clickDelete(pokemonTrainer) }) {
                    Icon(Icons.Default.Delete, "Delete",
                        tint = Color.White)
                }
            }
        }
    }
    Column {
        editPokemonModel(mainViewModel)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun editPokemonModel(mainViewModel: MainViewModel){
    val state = mainViewModel.mainViewState.collectAsState()

    if(state.value.openDialog){
        var name by rememberSaveable { mutableStateOf(state.value.editPokemonTrainer.name)}
        var gender by rememberSaveable { mutableStateOf(state.value.editPokemonTrainer.gender)}
        var sprite by rememberSaveable { mutableStateOf(state.value.editPokemonTrainer.sprite)}

        // https://developer.android.com/jetpack/compose/components/dialog
        AlertDialog(
            onDismissRequest = {
                mainViewModel.dismissDialog()
            },
            text = {
                Column {
                    // https://www.jetpackcompose.net/textfield-in-jetpack-compose
                    TextField(
                        modifier = Modifier.padding(top = 20.dp),
                        value = name,
                        onValueChange = { newText -> name = newText },
                        label = { Text(text = stringResource(R.string.editmodal_field_name) ) }
                    )

                    TextField(
                        modifier = Modifier.padding(top = 20.dp),
                        value = gender,
                        onValueChange = { newText -> gender = newText },
                        label = { Text(text = stringResource(R.string.editmodal_field_uid)) }
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        mainViewModel.savePokemonTrainer(
                            PokemonTrainer(
                                null,
                                name,
                                gender,
                                sprite,
                            )
                        )
                    }
                ) {
                    Text(stringResource(R.string.editmodal_button_save))
                }
            }
        )
    }
}

@Composable
fun MyPokemonList(pokemonViewModel: PokemonViewModel, favorite: Boolean) {
    val pokemonList = pokemonViewModel.pokemonViewState.collectAsState().value.pokemons
    Column {
        Row(modifier = Modifier
            .height(50.dp)
            .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center){
            val text = if(favorite){
                "My Favorites"
            } else {
                "Pokedex"
            }
            Text(text = text, fontSize = 40.sp, color = Color.White)
        }
        Row() {
            PokemonList(pokemonList = pokemonList, pokemonViewModel, favorite)
        }
    }

}

@Composable
fun PokemonList(pokemonList: List<Pokemon?>, pokemonViewModel: PokemonViewModel, favorite: Boolean) {
    LazyColumn {
        items(pokemonList) { pokemon ->
            PokemonItem(pokemon = pokemon, pokemonViewModel = pokemonViewModel, favorite)
        }
    }
}
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun PokemonItem(pokemon: Pokemon?, pokemonViewModel: PokemonViewModel, favorite: Boolean){
    Spacer(modifier = Modifier
        .height(5.dp)
        .fillMaxWidth())
    FlowRow(
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .background(color = Color(255, 255, 255, 125))
            .border(color = Color.Black, width = 1.dp),
        maxItemsInEachRow = 4
    ){
        val itemModifier = Modifier
            .clip(RoundedCornerShape(8.dp))
        Box(contentAlignment = Alignment.Center) {
            if (pokemon != null) {
                AsyncImage(
                    model = pokemon.imageUrl,
                    contentDescription = "Pokemon Image",
                    contentScale = ContentScale.FillHeight,
                    modifier = Modifier
                        .size(100.dp)
                        .clip(MaterialTheme.shapes.medium)
                )
            }
        }
        Box(modifier = itemModifier
            .fillMaxHeight()
            .weight(1f),
            contentAlignment = Alignment.Center){
            if (pokemon != null) {
                Text(
                    text = pokemon.name.replaceFirstChar { pokemon.name[0].titlecase()},
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(top = 8.dp),
                )
            }
        }
        Box(modifier = itemModifier
            .fillMaxHeight()
            .weight(1f),
            contentAlignment = Alignment.Center){
            val specificTypes = listOf(
                "fire", "water", "electric", "grass",
                "bug", "normal", "poison", "ground",
                "ghost", "psychic", "fairy", "fighting",
                "rock", "dragon", "ice"
            )
            if (pokemon != null) {
                val matchingTypes = listOf(pokemon.type0, pokemon.type1)
                    .mapNotNull { type ->
                        specificTypes.find { specificType ->
                            type.toString().contains(specificType, ignoreCase = true)
                        }?.toLowerCase(Locale.ROOT)
                    }
                if (matchingTypes.isNotEmpty()) {
                    Column {
                        matchingTypes.forEach { pokemonType ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Text(text = pokemonType.replaceFirstChar { it.uppercase() })
                            }
                        }
                    }
                }
            }
        }
        Box(modifier = itemModifier
            .fillMaxHeight()
            .weight(1f),
            contentAlignment = Alignment.Center){
            if (pokemon != null) {
                IconButton(onClick = {
                    if(pokemon.liked == "true"){
                        pokemonViewModel.unlikePokemon(pokemon, favorite)
                        println(pokemon.liked)
                        println("i happened")
                    } else {
                        pokemonViewModel.likePokemon(pokemon, favorite)
                    }
                }) {
                    var tint = if(pokemon.liked == "true"){
                        Color.Red
                    } else {
                        Color.White
                    }
                    Icon(Icons.Default.Favorite, "Like", tint = tint, modifier = Modifier.size(20.dp))
                }
            }
        }
    }
}




