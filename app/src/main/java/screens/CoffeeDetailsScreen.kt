package com.example.brenzoapp.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
    onBackClick: () -> Unit = {}
) {
    var isFavorite by remember { mutableStateOf(false) }

    var selectedMilk by remember { mutableStateOf("Classic") }
    var selectedSize by remember { mutableStateOf("350 мл") }
    var quantity by remember { mutableStateOf(1) }


    val headerIconColor = Color(0xFFF4E8D8)
    val coffeeIconTint = Color(0xFF4A3A2A)

    val basePrice = when (selectedSize) {
        "250 мл" -> 80
        "350 мл" -> 90
        "450 мл" -> 100
        else -> 90
    }
    val totalPrice = basePrice * quantity

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFEDE3D2))
    ) {

        // ----- Верх: фото + назад + обране -----
        Box {
            Image(
                painter = painterResource(id = R.drawable.cappuccino),
                contentDescription = "Капучино",
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

        // ----- Назва + опис -----
        Text(
            text = "Капучино",
            fontFamily = Poppins,
            fontSize = 24.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color(0xFF4A3A2A),
            modifier = Modifier.padding(horizontal = 24.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Ніжне молоко та насичений еспресо. Ідеальний напій для затишної паузи в Brenzo.",
            fontFamily = Poppins,
            fontSize = 14.sp,
            color = Color(0xFF6B5647),
            modifier = Modifier.padding(horizontal = 24.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // ----- Міцність + калорії -----
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Міцність
            Column {
                Text(
                    text = "Міцність",
                    fontFamily = Poppins,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF6B5647)
                )

                // чуть ниже, как на референсе
                Spacer(modifier = Modifier.height(6.dp))

                Row {
                    // 3 заповнені зерна
                    repeat(3) {
                        Image(
                            painter = painterResource(id = R.drawable.coffee),
                            contentDescription = "Сила кави",
                            modifier = Modifier
                                .size(18.dp)
                                .padding(end = 3.dp),
                            colorFilter = ColorFilter.tint(coffeeIconTint)
                        )
                    }
                    // 2 порожні зерна
                    repeat(2) {
                    Image(
                        painter = painterResource(id = R.drawable.coffee_p),
                        contentDescription = "Слабше зерно",
                        modifier = Modifier.size(16.dp),
                        colorFilter = ColorFilter.tint(coffeeIconTint)
                    )
                    }
                }
            }

            // Калорії
            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = "Калорії",
                    fontFamily = Poppins,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF6B5647)
                )
                Text(
                    text = "180 ккал",
                    fontFamily = Poppins,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF4A3A2A)
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // ----- Вибір молока -----
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
                .padding(horizontal = 24.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OptionChip("Класичне", selectedMilk == "Classic") { selectedMilk = "Classic" }
            OptionChip("Вівсяне", selectedMilk == "Oat") { selectedMilk = "Oat" }
            OptionChip("Кокосове", selectedMilk == "Coconut") { selectedMilk = "Coconut" }
            OptionChip("Бананове", selectedMilk == "Almond") { selectedMilk = "Almond" }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // ----- Розмір кави -----
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
            OptionChip("250 мл", selectedSize == "250 мл") { selectedSize = "250 мл" }
            OptionChip("350 мл", selectedSize == "350 мл") { selectedSize = "350 мл" }
            OptionChip("450 мл", selectedSize == "450 мл") { selectedSize = "450 мл" }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // ----- Кількість + ціна -----
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            // кількість
            Row(verticalAlignment = Alignment.CenterVertically) {

                SmallCircleButton(text = "–") {
                    if (quantity > 1) quantity--
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
                    quantity++
                }
            }

            // ціна
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

        Spacer(modifier = Modifier.height(16.dp))

        // ----- Кнопка з тінню знизу -----
        Button(
            onClick = { /* TODO: додати в кошик */ },
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

/** Маленька кругла кнопка (+ / -) */
@Composable
private fun SmallCircleButton(
    text: String,
    darkBackground: Boolean = false,
    onClick: () -> Unit
) {
    val bg = if (darkBackground) Color(0x66000000) else Color(0xFFF0DCC5)
    val fg = if (darkBackground) Color.White else Color(0xFF6B3F24)

    Box(
        modifier = Modifier
            .size(32.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(bg)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            fontSize = 16.sp,
            fontFamily = Poppins,
            fontWeight = FontWeight.SemiBold,
            color = fg
        )
    }
}

/** Чіп-опція (молоко / розмір) */
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

@androidx.compose.ui.tooling.preview.Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun CoffeeDetailsPreview() {
    CoffeeDetailsScreen()
}
