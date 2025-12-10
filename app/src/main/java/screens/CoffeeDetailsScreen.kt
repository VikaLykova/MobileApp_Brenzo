package com.example.brenzoapp.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.brenzoapp.R
import com.example.brenzoapp.ui.theme.Poppins

@Composable
fun CoffeeDetailsScreen(
    coffee: CoffeeItem,
    viewModel: CoffeeViewModel,
    onBackClick: () -> Unit = {}
) {
    var isFavorite by remember { mutableStateOf(false) }

    var selectedMilk by remember { mutableStateOf("Classic") }
    var selectedSize by remember { mutableStateOf("250 мл") }
    var quantity by remember { mutableStateOf(1) }

    val headerIconColor = Color(0xFFF4E8D8)
    val coffeeIconTint = Color(0xFF4A3A2A)

    // опис, міцність, калорії залежать від напою
    val (description, strengthFilled, calories) = when (coffee.name) {
        "Лате" -> Triple(
            "Ніжна кава з великою кількістю молока. Ідеальна для м’якого смаку.",
            2,
            210
        )
        "Американо" -> Triple(
            "Класичний чорний напій на основі еспресо, розбавлений гарячою водою.",
            4,
            10
        )
        else -> Triple(
            "Ніжне молоко та насичений еспресо. Ідеальний напій для затишної паузи в Brenzo.",
            3,
            180
        )
    }

    // базова ціна за 250 мл — це price з CoffeeItem
    val base250 = coffee.price

    val sizeExtra = when (selectedSize) {
        "250 мл" -> 0
        "350 мл" -> 10
        "450 мл" -> 20
        else -> 0
    }

    val milkExtra = when (selectedMilk) {
        "Classic" -> 0
        "Oat"     -> 40
        "Coconut" -> 35
        "Almond"  -> 30
        "None"    -> 0   // Американо без молока
        else      -> 0
    }

    val unitPrice = base250 + sizeExtra + milkExtra
    val totalPrice = unitPrice * quantity

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFEDE3D2))
    ) {
        // верхнє фото + назад + обране
        Box {
            Image(
                painter = painterResource(id = coffee.imageRes),
                contentDescription = coffee.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(340.dp)
                    .clip(
                        RoundedCornerShape(
                            bottomStart = 32.dp,
                            bottomEnd = 32.dp
                        )
                    ),
                contentScale = ContentScale.Crop
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 24.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.arrow),
                    contentDescription = "Назад",
                    modifier = Modifier
                        .size(24.dp)
                        .clickable { onBackClick() },
                    colorFilter = ColorFilter.tint(headerIconColor)
                )

                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(headerIconColor.copy(alpha = 0.4f))
                        .clickable { isFavorite = !isFavorite },
                    contentAlignment = Alignment.Center
                ) {
                    val favTint =
                        if (isFavorite) Color(0xFFE45757) else Color(0x66FFF4E5)

                    Image(
                        painter = painterResource(id = R.drawable.fav),
                        contentDescription = "У вибране",
                        modifier = Modifier.size(18.dp),
                        colorFilter = ColorFilter.tint(favTint)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = coffee.name,
            fontFamily = Poppins,
            fontSize = 24.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color(0xFF4A3A2A),
            modifier = Modifier.padding(horizontal = 24.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = description,
            fontFamily = Poppins,
            fontSize = 14.sp,
            color = Color(0xFF6B5647),
            modifier = Modifier.padding(horizontal = 24.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Міцність + калорії
        StrengthAndCaloriesRow(
            strengthFilled = strengthFilled,
            calories = calories,
            coffeeIconTint = coffeeIconTint
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Вибір молока
        MilkSection(
            coffeeName = coffee.name,
            selectedMilk = selectedMilk,
            onMilkChange = { selectedMilk = it }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Розмір кави
        SizeSection(
            selectedSize = selectedSize,
            onSizeChange = { selectedSize = it }
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Кількість + ціна
        QuantityAndPriceRow(
            quantity = quantity,
            onQuantityChange = { quantity = it },
            totalPrice = totalPrice
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Кнопка "Додати в кошик"
        Button(
            onClick = {
                viewModel.addToCart(coffee, unitPrice, quantity)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 24.dp)
                .height(56.dp),
            shape = RoundedCornerShape(30.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF6B3F24),
                contentColor = Color(0xFFFFF4E5)
            ),
            elevation = ButtonDefaults.buttonElevation(
                defaultElevation = 6.dp,
                pressedElevation = 2.dp
            )
        ) {
            Text(
                text = "Додати в кошик",
                fontFamily = Poppins,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center
            )
        }
    }
}

// ================== helper-компоненты ==================

@Composable
private fun StrengthAndCaloriesRow(
    strengthFilled: Int,
    calories: Int,
    coffeeIconTint: Color
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                text = "Міцність",
                fontFamily = Poppins,
                fontSize = 13.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF6B5647)
            )
            Spacer(modifier = Modifier.height(6.dp))
            Row {
                repeat(strengthFilled) {
                    Image(
                        painter = painterResource(id = R.drawable.coffee),
                        contentDescription = "Сила кави",
                        modifier = Modifier
                            .size(18.dp)
                            .padding(end = 3.dp),
                        colorFilter = ColorFilter.tint(coffeeIconTint)
                    )
                }
                repeat(5 - strengthFilled) {
                    Image(
                        painter = painterResource(id = R.drawable.coffee_p),
                        contentDescription = "Слабше зерно",
                        modifier = Modifier.size(16.dp),
                        colorFilter = ColorFilter.tint(coffeeIconTint)
                    )
                }
            }
        }

        Column(horizontalAlignment = Alignment.End) {
            Text(
                text = "Калорії",
                fontFamily = Poppins,
                fontSize = 13.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF6B5647)
            )
            Text(
                text = "$calories ккал",
                fontFamily = Poppins,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF4A3A2A)
            )
        }
    }
}

@Composable
private fun MilkSection(
    coffeeName: String,
    selectedMilk: String,
    onMilkChange: (String) -> Unit
) {
    Text(
        text = "Виберіть молоко",
        fontFamily = Poppins,
        fontSize = 14.sp,
        fontWeight = FontWeight.SemiBold,
        color = Color(0xFF4A3A2A),
        modifier = Modifier.padding(horizontal = 24.dp)
    )

    Spacer(modifier = Modifier.height(8.dp))

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
            .horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // тільки для Американо — "Без молока" першим
        if (coffeeName == "Американо") {
            OptionChip(
                text = "Без молока",
                selected = selectedMilk == "None",
                onClick = { onMilkChange("None") }
            )
        }

        OptionChip(
            text = "Класичне",
            selected = selectedMilk == "Classic",
            onClick = { onMilkChange("Classic") }
        )

        OptionChip(
            text = "Вівсяне",
            selected = selectedMilk == "Oat",
            onClick = { onMilkChange("Oat") }
        )

        OptionChip(
            text = "Кокосове",
            selected = selectedMilk == "Coconut",
            onClick = { onMilkChange("Coconut") }
        )

        // Бананове — тільки не для Американо
        if (coffeeName != "Американо") {
            OptionChip(
                text = "Бананове",
                selected = selectedMilk == "Almond",
                onClick = { onMilkChange("Almond") }
            )
        }
    }
}

@Composable
private fun SizeSection(
    selectedSize: String,
    onSizeChange: (String) -> Unit
) {
    Text(
        text = "Розмір кави",
        fontFamily = Poppins,
        fontSize = 14.sp,
        fontWeight = FontWeight.SemiBold,
        color = Color(0xFF4A3A2A),
        modifier = Modifier.padding(horizontal = 24.dp)
    )

    Spacer(modifier = Modifier.height(8.dp))

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        OptionChip("250 мл", selectedSize == "250 мл") { onSizeChange("250 мл") }
        OptionChip("350 мл", selectedSize == "350 мл") { onSizeChange("350 мл") }
        OptionChip("450 мл", selectedSize == "450 мл") { onSizeChange("450 мл") }
    }
}

@Composable
private fun QuantityAndPriceRow(
    quantity: Int,
    onQuantityChange: (Int) -> Unit,
    totalPrice: Int
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            SmallCircleButton(text = "–") {
                if (quantity > 1) onQuantityChange(quantity - 1)
            }

            Text(
                text = quantity.toString(),
                fontFamily = Poppins,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF4A3A2A),
                modifier = Modifier.padding(horizontal = 12.dp)
            )

            SmallCircleButton(text = "+") {
                onQuantityChange(quantity + 1)
            }
        }

        Column(horizontalAlignment = Alignment.End) {
            Text(
                text = "Ціна",
                fontFamily = Poppins,
                fontSize = 13.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF6B5647)
            )
            Text(
                text = "₴$totalPrice",
                fontFamily = Poppins,
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF4A3A2A)
            )
        }
    }
}

@Composable
private fun SmallCircleButton(
    text: String,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(32.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Color(0xFFF0DCC5))
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            fontSize = 16.sp,
            fontFamily = Poppins,
            fontWeight = FontWeight.SemiBold,
            color = Color(0xFF6B3F24)
        )
    }
}

@Composable
private fun OptionChip(
    text: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    val bg = if (selected) Color(0xFF6B3F24) else Color(0xFFF7EFE3)
    val fg = if (selected) Color(0xFFFFF4E5) else Color(0xFF4A3A2A)

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(999.dp))
            .background(bg)
            .clickable { onClick() }
            .padding(horizontal = 12.dp, vertical = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            fontFamily = Poppins,
            fontSize = 13.sp,
            fontWeight = FontWeight.Medium,
            color = fg
        )
    }
}
