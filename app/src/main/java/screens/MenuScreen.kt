package com.example.brenzoapp.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.brenzoapp.R
import com.example.brenzoapp.ui.theme.Poppins


private val Playfair = FontFamily(
    Font(R.font.playfair)
)

@Composable
fun MenuScreen(
    onCoffeeClick: () -> Unit,
    onCartClick: () -> Unit,
    onBackClick: () -> Unit
) {
    var selectedCategory by remember { mutableStateOf("Coffee") }

    val accentBrown = Color(0xFF4A3A2A)
    val dividerColor = accentBrown.copy(alpha = 0.08f)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFEDE3D2))
            .padding(horizontal = 24.dp, vertical = 24.dp)
    ) {

        // ===== Верхняя панель =====
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Стрелка назад
            Image(
                painter = painterResource(id = R.drawable.arrow),
                contentDescription = "Назад",
                modifier = Modifier
                    .size(22.dp)
                    .clickable { onBackClick() },
                colorFilter = ColorFilter.tint(Color(0xFFF4E8D8))
            )

            Text(
                text = "Brenzo",
                fontFamily = Playfair,
                fontSize = 28.sp,
                fontWeight = FontWeight.SemiBold,
                color = accentBrown
            )

            Image(
                painter = painterResource(id = R.drawable.cart),
                contentDescription = "Cart",
                modifier = Modifier
                    .size(26.dp)
                    .clickable { onCartClick() },
                colorFilter = ColorFilter.tint(Color(0xFF3C2F2F))
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Заголовок
        Text(
            text = "Обери свою каву",
            fontFamily = Playfair,
            fontSize = 26.sp,
            fontWeight = FontWeight.SemiBold,
            color = accentBrown
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Час обирати свій ідеальний смак",
            fontFamily = Poppins,
            fontSize = 14.sp,
            color = Color(0xFF6B5647)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Категории
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            CategoryChip("Кава", selectedCategory == "Coffee") { selectedCategory = "Coffee" }
            CategoryChip("Десерти", selectedCategory == "Dessert") { selectedCategory = "Dessert" }
            CategoryChip("Інше", selectedCategory == "Other") { selectedCategory = "Other" }
        }

        Spacer(modifier = Modifier.height(10.dp))
        Divider(color = dividerColor, thickness = 1.dp)
        Spacer(modifier = Modifier.height(12.dp))


        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            CoffeeMiniCard(
                title = "Капучино",
                imageRes = R.drawable.cappuccino,
                modifier = Modifier.clickable { onCoffeeClick() }
            )
            CoffeeMiniCard(title = "Лате", imageRes = R.drawable.latte)
            CoffeeMiniCard(title = "Американо", imageRes = R.drawable.amerikano)
        }

        Spacer(modifier = Modifier.height(16.dp))
        Divider(color = dividerColor, thickness = 1.dp)
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Наша продукція",
            fontFamily = Playfair,
            fontSize = 24.sp,
            fontWeight = FontWeight.SemiBold,
            color = accentBrown
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Товар
        ProductCard()

        Spacer(modifier = Modifier.height(16.dp))

        TopSellerBanner()

        Spacer(modifier = Modifier.weight(1f))

        // Нижняя навигация
        BottomNavigationBar()
    }
}


// ------------------ КОМПОНЕНТЫ ------------------

@Composable
fun CategoryChip(text: String, selected: Boolean, onClick: () -> Unit) {
    val bg = if (selected) Color(0xFF4A3A2A) else Color.Transparent
    val fg = if (selected) Color.White else Color(0xFF4A3A2A)

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(999.dp))
            .background(bg)
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 10.dp)
    ) {
        Text(
            text = text,
            fontFamily = Poppins,
            fontSize = 14.sp,
            color = fg
        )
    }
}

@Composable
fun CoffeeMiniCard(
    title: String,
    imageRes: Int,
    modifier: Modifier = Modifier
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = modifier) {
        Image(
            painter = painterResource(id = imageRes),
            contentDescription = title,
            modifier = Modifier
                .size(60.dp)
                .clip(RoundedCornerShape(16.dp)),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = title,
            fontFamily = Poppins,
            fontSize = 12.sp,
            color = Color(0xFF4A3A2A),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun ProductCard() {
    val accentBrown = Color(0xFF4A3A2A)

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF7EFE3)),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "Кава в зернах-Арабіка Еліта 250 г",
                fontFamily = Playfair,
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = accentBrown
            )

            Image(
                painter = painterResource(id = R.drawable.pack),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(128.dp)
                    .clip(RoundedCornerShape(16.dp)),
                contentScale = ContentScale.Crop
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .clip(CircleShape)
                            .background(Color(0xFF6BBF59))
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "У наявності",
                        fontFamily = Poppins,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF6BBF59)
                    )
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "₴495",
                        fontFamily = Poppins,
                        fontSize = 13.sp,
                        color = Color(0xFFB0AAA0),
                        textDecoration = TextDecoration.LineThrough
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "₴440",
                        fontFamily = Poppins,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = accentBrown
                    )
                }
            }
        }
    }
}

@Composable
fun TopSellerBanner() {
    val accentBrown = Color(0xFF4A3A2A)
    val pillBrown = Color(0xFF6B3F24)

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF7EFE3))
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Column(modifier = Modifier.weight(1f)) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(999.dp))
                        .background(pillBrown)
                        .padding(horizontal = 10.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = "Топ продажу",
                        fontFamily = Poppins,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White
                    )
                }

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "Крем-мед з бананом 90 г",
                    fontFamily = Poppins,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = accentBrown
                )
                Text(
                    text = "108 ₴",
                    fontFamily = Poppins,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = accentBrown
                )
            }

            Image(
                painter = painterResource(id = R.drawable.honeybanana),
                contentDescription = null,
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(20.dp)),
                contentScale = ContentScale.Crop
            )
        }
    }
}

@Composable
fun BottomNavigationBar() {
    val accentBrown = Color(0xFF4A3A2A)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .clip(RoundedCornerShape(40.dp))
                .background(accentBrown)
                .padding(horizontal = 32.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            BottomNavItem(R.drawable.home, "", selected = true)
            BottomNavItem(R.drawable.fav, "", selected = false)
            BottomNavItem(R.drawable.profile, "", selected = false)
        }
    }
}

@Composable
fun BottomNavItem(
    iconRes: Int,
    label: String,
    selected: Boolean
) {
    val iconColor = if (selected) Color(0xFFF7EFE3) else Color(0xFFBFAFA0)

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = iconRes),
            contentDescription = label,
            colorFilter = ColorFilter.tint(iconColor),
            modifier = Modifier.size(if (selected) 28.dp else 24.dp)
        )
    }
}

@androidx.compose.ui.tooling.preview.Preview(showBackground = true, showSystemUi = true)
@Composable
fun MenuScreenPreview() {
    MenuScreen(
        onCoffeeClick = {},
        onCartClick = {},
        onBackClick = {}
    )
}
