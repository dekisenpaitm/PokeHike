# Demo II: Pokémon Trainer App

## Overview
This Android application, `Demo II`, is a Kotlin-based uni-project utilizing Jetpack Compose. It's designed for Pokémon enthusiasts, allowing users to view, edit, and manage their favorite Pokémon and trainers. It will build the base for my upcoming team-project: PokéHike.

## Planned Features
The main idea was to create a fully functioning app that covers all necessary CRUD functions for my semester assignment in mobile coding. It should be possible for the user to create/update/delete a trainer profile (name/gender), fetching the pokemon API to have the first 151 Pokemon,
tagging pokemon as favorites and basically building the foundation for the CodeLabs3.

## Additional Features
- **PokemonTrainerImages**: Add functionality for the user to pick a trainer picture when creating the trainer for the first time.
- **LocalListStorage**: Since its designed for hiking internet might be not available, PokemonLists get stored locally. Pictures are still being fetched online.

## Future Features (CodeLab3)
- **WeatherInfo**: Adding an API to fetch informations about the current weather situation.
- **PokeStore**: Adding a feature to buy Pokeballs (LootBoxes) based on the current weather.
- **OwnedPokemon**: Adding a new table for pokemon the user ownes after opening the pokeballs.
- **GeneralReworkOfUI**: UI is still only a placeholder.

## Basic Features
- **Trainer Profiles**: View, edit and delete your Pokémon Trainer profile, including images, names, and other attributes.
- **Pokémon Lists**: Browse through a comprehensive list of Pokémon, with support for marking favorites.
- **Dynamic UI**: Utilize the power of Jetpack Compose for a responsive and interactive user experience.

## Tech Stack
- **Language**: Kotlin
- **UI Toolkit**: Jetpack Compose
- **Networking**: Retrofit, OkHttp
- **Image Loading**: Coil-kt, Glide

## Setup and Installation
Ensure you have the latest version of Android Studio and the Kotlin plugin installed.

1. Clone the repository:
   ```
   git clone https://github.com/yourusername/demo_ii.git
   ```
2. Open the project in Android Studio.
3. Sync the Gradle files and wait for the process to complete.
4. Run the application on an emulator or upload it on a physical device.

## User Guide
- Start the app on your emulator or physical device
- Create your user on the landing page to enable all other features (make sure you're connected to the internet)
- On screen3(NavBarButton3) scroll through a list of Pokemon and press the ♥-icon to add them to your favorites.
- Scroll through your favorites on screen2(NavBarButton2)
- Edit your user profile on screen4(NavBarButton4) or delete the user and start over

## Dependencies
- Jetpack Compose for modern UI design.
- Coil-kt and Glide for image loading.
- Retrofit with Gson for network operations.
- Various other AndroidX libraries for enhanced functionality.

## Images
All images used for trainers:
https://archives.bulbagarden.net/wiki/Main_Page
(All rights reserved to bulbagarden)

## Contact
For queries, please open an issue or contact dekisenpaitm@gmail.com.

---
