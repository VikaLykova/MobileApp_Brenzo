package com.example.brenzoapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.brenzoapp.screens.HomeScreen
import com.example.brenzoapp.screens.MenuScreen
import com.example.brenzoapp.screens.CartScreen
import com.example.brenzoapp.screens.CoffeeDetailsScreen

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


    var currentScreen by remember { mutableStateOf("home") }

    when (currentScreen) {

        //  Главный экран
        "home" -> HomeScreen(
            onOrderClick = {
                currentScreen = "menu"
            }
        )

        //  Экран меню
        "menu" -> MenuScreen(
            onCoffeeClick = {
                // Переход в детали напитка
                currentScreen = "details"
            },
            onCartClick = {
                // Переход в корзину
                currentScreen = "cart"
            },
            onBackClick = {
                // Возврат на главный
                currentScreen = "home"
            }
        )

        //  Детали кофе
        "details" -> CoffeeDetailsScreen(
            onBackClick = {
                currentScreen = "menu"
            }
        )

        //  Корзина
        "cart" -> CartScreen(
            onBackClick = {
                currentScreen = "menu"
            }
        )
    }
}
