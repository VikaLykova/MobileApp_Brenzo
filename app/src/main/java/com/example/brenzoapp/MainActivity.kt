package com.example.brenzoapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.brenzoapp.screens.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppNavigation()
        }
    }
}

@Composable
fun AppNavigation() {
    // один ViewModel на весь граф
    val coffeeViewModel: CoffeeViewModel = viewModel()
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "home"
    ) {

        composable("home") {
            HomeScreen(
                onOrderClick = { navController.navigate("menu") }
            )
        }

        composable("menu") {
            MenuScreen(
                coffeeList = coffeeViewModel.coffeeList,
                cartCount = coffeeViewModel.cartCount,
                onCoffeeClick = { item ->
                    coffeeViewModel.selectCoffee(item)
                    navController.navigate("details")
                },
                onCartClick = { navController.navigate("cart") },
                onBackClick = { navController.popBackStack() }
            )
        }

        composable("details") {
            val coffee = coffeeViewModel.selectedCoffee
            if (coffee != null) {
                CoffeeDetailsScreen(
                    coffee = coffee,
                    viewModel = coffeeViewModel,
                    onBackClick = { navController.popBackStack() }
                )
            } else {
                navController.popBackStack()
            }
        }

        composable("cart") {
            CartScreen(
                viewModel = coffeeViewModel,
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}
