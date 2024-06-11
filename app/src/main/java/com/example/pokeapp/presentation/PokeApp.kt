package com.example.pokeapp.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.pokeapp.AppRoutes
import com.example.pokeapp.presentation.details.DetailsScreen
import com.example.pokeapp.presentation.home.HomeScreen

@Composable
fun PokeApp(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = AppRoutes.HOME.name,
        modifier = modifier,
    ) {
        composable(AppRoutes.HOME.name) {
            HomeScreen(navController = navController)
        }
        composable(AppRoutes.DETAILS.name) {
            DetailsScreen(navController = navController)
        }
    }
}
