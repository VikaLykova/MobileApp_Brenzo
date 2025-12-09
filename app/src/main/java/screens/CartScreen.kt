package com.example.brenzoapp.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.brenzoapp.R
import com.example.brenzoapp.ui.theme.Poppins

// Playfair Display (как на главном экране)
private val Playfair = FontFamily(
    Font(R.font.playfair)
)

@Composable
fun CartScreen(onBackClick: () -> Unit) {

    // Палитра
    val backgroundColor = Color(0xFF3C2921)
    val cardColor = Color(0xFFF4E6D8)
    val accentBrown = Color(0xFF6B3F24)
    val accentGold = Color(0xFFB68B4A)

    val cardTextMain = Color(0xFF4A3A2A)
    val cardTextSecondary = Color(0xFF6B5647)

    val headerTextColor = Color(0xFFF4E8D8)
    val headerSubtitleColor = headerTextColor.copy(alpha = 0.8f)
    val headerIconColor = headerTextColor

    // 🔹 ВНЕШНИЙ Column — только фон и разбиение на контент + нижний бар
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
    ) {

        // 🔹 ВЕРХ + СПИСОК — с паддингами 24dp
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 24.dp, vertical = 24.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {


            Spacer(modifier = Modifier.height(12.dp))

            // ===== ВЕРХ: стрелка + иконки на одном уровне, ниже — заголовок =====
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    Image(
                        painter = painterResource(id = R.drawable.arrow),
                        contentDescription = "Назад",
                        modifier = Modifier
                            .size(22.dp)
                            .clickable { onBackClick() },
                        colorFilter = ColorFilter.tint(headerIconColor)
                    )

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.bookmark),
                            contentDescription = "Збережені",
                            modifier = Modifier.size(22.dp),
                            colorFilter = ColorFilter.tint(headerIconColor)
                        )

                        Image(
                            painter = painterResource(id = R.drawable.search),
                            contentDescription = "Пошук",
                            modifier = Modifier.size(22.dp),
                            colorFilter = ColorFilter.tint(headerIconColor)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(18.dp))

                Text(
                    text = "Кошик",
                    fontFamily = Playfair,
                    fontSize = 26.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = headerTextColor
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "Ваші вибрані напої та десерти.",
                    fontFamily = Poppins,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Normal,
                    color = headerSubtitleColor
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // ===== СЕРЕДИНА: карточки товарів =====
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                CartItemCard(
                    title = "Капучино (на звичайному молоці)",
                    price = "₴ 90",
                    quantity = 1,
                    accentColor = accentGold,
                    cardColor = cardColor,
                    textMainColor = cardTextMain,
                    textSecondaryColor = cardTextSecondary,
                    imageRes = R.drawable.cappuccino
                )

                CartItemCard(
                    title = "Десерт Тірамісу",
                    price = "₴ 120",
                    quantity = 1,
                    accentColor = accentGold,
                    cardColor = cardColor,
                    textMainColor = cardTextMain,
                    textSecondaryColor = cardTextSecondary,
                    imageRes = R.drawable.tiramisu
                )

                CartItemCard(
                    title = "Крем-мед з бананом 90 г",
                    price = "₴ 108",
                    quantity = 2,        // две баночки
                    accentColor = accentGold,
                    cardColor = cardColor,
                    textMainColor = cardTextMain,
                    textSecondaryColor = cardTextSecondary,
                    imageRes = R.drawable.honeybanana
                )
            }
        }

        // 🔹 НИЖНИЙ БАР — БЕЗ внешнего horizontal padding, фон на всю ширину
        Spacer(modifier = Modifier.height(14.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0x1AFFFFFF))
                .padding(horizontal = 24.dp, vertical = 28.dp)
        ) {
            // Тонкая светлая линия-разделитель
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(Color(0x33FFFFFF))
            )

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Column {
                    Text(
                        text = "Разом",
                        fontFamily = Poppins,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Normal,
                        color = headerSubtitleColor
                    )
                    Text(
                        text = "₴ 318",
                        fontFamily = Poppins,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = headerTextColor
                    )
                }

                Button(
                    onClick = { /* TODO: оформити замовлення */ },
                    modifier = Modifier
                        .height(50.dp)
                        .width(150.dp),
                    shape = RoundedCornerShape(25.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = accentBrown,
                        contentColor = Color(0xFFFFF4E5)
                    ),
                    elevation = ButtonDefaults.buttonElevation(
                        defaultElevation = 4.dp,
                        pressedElevation = 6.dp
                    )
                ) {
                    Text(
                        text = "Оформити",
                        fontFamily = Poppins,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.SemiBold,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}

// ===== Картка товару з картинкою =====

@Composable
private fun CartItemCard(
    title: String,
    price: String,
    quantity: Int,
    accentColor: Color,
    cardColor: Color,
    textMainColor: Color,
    textSecondaryColor: Color,
    imageRes: Int
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(containerColor = cardColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {

        Box(modifier = Modifier.fillMaxWidth()) {

            // ---------- КРЕСТИК (справа сверху) ----------
            Image(
                painter = painterResource(id = R.drawable.cross),
                contentDescription = "Видалити",
                modifier = Modifier
                    .size(12.dp)
                    .align(Alignment.TopEnd)
                    .offset(x = (-12).dp, y = 12.dp),   // ← ВАЖНО: смещаем внутрь и выше
                colorFilter = ColorFilter.tint(Color(0xFF4A3A2A))
            )


            // ---------- ОСНОВНОЙ КОНТЕНТ КАРТОЧКИ ----------
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 14.dp, vertical = 14.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Image(
                    painter = painterResource(id = imageRes),
                    contentDescription = title,
                    modifier = Modifier.size(56.dp)
                )

                Spacer(modifier = Modifier.width(12.dp))

                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = title,
                        fontFamily = Poppins,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = textMainColor
                    )

                    Spacer(modifier = Modifier.height(2.dp))

                    Text(
                        text = price,
                        fontFamily = Poppins,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = accentColor
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        QuantityChip(text = "–")
                        Text(
                            text = quantity.toString(),
                            fontFamily = Poppins,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Medium,
                            color = textMainColor
                        )
                        QuantityChip(text = "+")
                    }
                }
            }
        }
    }
}


// ===== Кнопки + / - =====

@Composable
private fun QuantityChip(text: String) {
    Box(
        modifier = Modifier
            .size(24.dp)
            .background(
                color = Color(0xFFEBD7C0),
                shape = RoundedCornerShape(12.dp)
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            fontFamily = Poppins,
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color(0xFF6B3F24)
        )
    }
}

// ПРЕВ’Ю

@androidx.compose.ui.tooling.preview.Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun CartScreenPreview() {
    CartScreen(onBackClick = {})
}
