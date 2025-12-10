package com.example.brenzoapp.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.brenzoapp.R
import com.example.brenzoapp.ui.theme.Poppins

// шрифт заголовка
private val Playfair = FontFamily(Font(R.font.playfair))

private val AccentBrown = Color(0xFF4A3A2A)
private val LightBeige = Color(0xFFEDE3D2)
private val CardBeige = Color(0xFFF7EFE3)

@Composable
fun CartScreen(
    viewModel: CoffeeViewModel,
    onBackClick: () -> Unit
) {
    val cartItems = viewModel.cartItems
    val totalPrice = cartItems.sumOf { it.unitPrice * it.quantity }

    // тёмно-коричневый фон
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AccentBrown)
            .padding(horizontal = 16.dp, vertical = 16.dp)
    ) {
        // ----- верхняя панель -----
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp, bottom = 24.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.arrow),
                contentDescription = "Назад",
                modifier = Modifier
                    .size(22.dp)
                    .clickable { onBackClick() },
                colorFilter = ColorFilter.tint(Color(0xFFF4E8D8))
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = "Кошик",
                fontFamily = Playfair,
                fontSize = 22.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFFF4E8D8)
            )
        }

        // ----- светлый блок контента -----
        Card(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp)),
            colors = CardDefaults.cardColors(containerColor = LightBeige)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp, vertical = 16.dp)
            ) {
                if (cartItems.isEmpty()) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Ваш кошик порожній",
                            fontFamily = Poppins,
                            fontSize = 14.sp,
                            color = Color(0xFF6B5647)
                        )
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        items(cartItems) { item ->
                            CartItemRow(
                                item = item,
                                onRemove = { viewModel.removeFromCart(item.coffee) }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))
                Divider(color = Color(0x14000000), thickness = 1.dp)
                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Всього:",
                        fontFamily = Poppins,
                        fontSize = 14.sp,
                        color = Color(0xFF6B5647)
                    )
                    Text(
                        text = "₴$totalPrice",
                        fontFamily = Poppins,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = AccentBrown
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                Button(
                    onClick = { /* TODO: логіка оплати */ },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp)
                        .padding(bottom = 8.dp),
                    shape = RoundedCornerShape(26.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = AccentBrown,
                        contentColor = LightBeige
                    )
                ) {
                    Text(
                        text = "Оплатити",
                        fontFamily = Poppins,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    }
}

@Composable
private fun CartItemRow(
    item: CartItem,
    onRemove: () -> Unit
) {
    val itemTotal = item.unitPrice * item.quantity

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = CardBeige),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = item.coffee.imageRes),
                    contentDescription = item.coffee.name,
                    modifier = Modifier
                        .size(52.dp)
                        .clip(RoundedCornerShape(16.dp)),
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.width(12.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = item.coffee.name,
                        fontFamily = Poppins,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = AccentBrown
                    )
                    Text(
                        text = "₴${item.unitPrice} × ${item.quantity}",
                        fontFamily = Poppins,
                        fontSize = 12.sp,
                        color = Color(0xFF6B5647)
                    )
                }

                Text(
                    text = "₴$itemTotal",
                    fontFamily = Poppins,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = AccentBrown
                )
            }

            Image(
                painter = painterResource(id = R.drawable.cross),
                contentDescription = "Прибрати з кошика",
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .size(18.dp)
                    .clickable { onRemove() },
                colorFilter = ColorFilter.tint(Color(0xFFB0AAA0))
            )
        }
    }
}
