package com.example.brenzoapp.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.brenzoapp.R

// 🔹 Одна позиція в кошику: напій + ціна за 1 шт + кількість
data class CartItem(
    val coffee: CoffeeItem,
    val unitPrice: Int,    // ціна ОДНІЄЇ чашки з урахуванням молока/обʼєму
    val quantity: Int
)

class CoffeeViewModel : ViewModel() {

    // 1) Список напоїв (для меню)
    val coffeeList = listOf(
        CoffeeItem(1, "Капучино", 90, R.drawable.cappuccino),
        CoffeeItem(2, "Лате", 95, R.drawable.latte),
        CoffeeItem(3, "Американо", 70, R.drawable.amerikano),
        CoffeeItem(4, "Флет вайт", 95, R.drawable.cappuccino),
        CoffeeItem(5, "Еспресо", 60, R.drawable.amerikano)
    )

    // 2) Обраний напій (для екрану деталей)
    var selectedCoffee by mutableStateOf<CoffeeItem?>(null)
        private set

    fun selectCoffee(item: CoffeeItem) {
        selectedCoffee = item
    }

    // 3) Обрана категорія (якщо захочеш фільтрацію)
    var selectedCategory by mutableStateOf("Coffee")
        private set

    fun setCategory(category: String) {
        selectedCategory = category
    }

    // 4) Кошик
    var cartItems by mutableStateOf(listOf<CartItem>())
        private set

    // кількість товарів у кошику (з урахуванням quantity)
    val cartCount: Int
        get() = cartItems.sumOf { it.quantity }

    // 👉 Додати в кошик: напій + ціна за ОДНУ чашку + кількість
    fun addToCart(coffee: CoffeeItem, unitPrice: Int, quantityToAdd: Int = 1) {
        if (quantityToAdd <= 0) return

        val existing = cartItems.find { it.coffee.id == coffee.id }

        cartItems = if (existing == null) {
            cartItems + CartItem(
                coffee = coffee,
                unitPrice = unitPrice,
                quantity = quantityToAdd
            )
        } else {
            cartItems.map {
                if (it.coffee.id == coffee.id) {
                    it.copy(quantity = it.quantity + quantityToAdd)
                } else it
            }
        }
    }

    // змінити кількість (якщо потім додамо +/- у кошику)
    fun setItemQuantity(coffee: CoffeeItem, newQuantity: Int) {
        cartItems = if (newQuantity <= 0) {
            cartItems.filterNot { it.coffee.id == coffee.id }
        } else {
            cartItems.map {
                if (it.coffee.id == coffee.id) {
                    it.copy(quantity = newQuantity)
                } else it
            }
        }
    }

    // прибрати позицію повністю
    fun removeFromCart(coffee: CoffeeItem) {
        cartItems = cartItems.filterNot { it.coffee.id == coffee.id }
    }

    // очистити кошик
    fun clearCart() {
        cartItems = emptyList()
    }
}
