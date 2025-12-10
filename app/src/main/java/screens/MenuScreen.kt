package com.example.brenzoapp.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
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
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.brenzoapp.R
import com.example.brenzoapp.ui.theme.Poppins

// Шрифт заголовков
private val Playfair = FontFamily(
    Font(R.font.playfair)
)

// Модель для одного напою
data class CoffeeItem(
    val id: Int,
    val name: String,
    val price: Int,
    val imageRes: Int
)

@Composable
fun MenuScreen(
    coffeeList: List<CoffeeItem>,
    cartCount: Int,
    onCoffeeClick: (CoffeeItem) -> Unit,
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
            .padding(horizontal = 20.dp, vertical = 12.dp)
    ) {
        // ===== Верхняя панель =====
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp),
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
                fontSize = 26.sp,
                fontWeight = FontWeight.SemiBold,
                color = accentBrown
            )

            // 👉 корзина + бейдж
            Box(
                modifier = Modifier
                    .size(28.dp)
                    .clickable { onCartClick() },
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.cart),
                    contentDescription = "Cart",
                    modifier = Modifier.size(24.dp),
                    colorFilter = ColorFilter.tint(Color(0xFF3C2F2F))
                )

                if (cartCount > 0) {
                    CartBadge(
                        count = cartCount,
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .offset(x = 4.dp, y = (-4).dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Заголовок
        Text(
            text = "Обери свою каву",
            fontFamily = Playfair,
            fontSize = 24.sp,
            fontWeight = FontWeight.SemiBold,
            color = accentBrown
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = "Час обирати свій ідеальний смак",
            fontFamily = Poppins,
            fontSize = 13.sp,
            color = Color(0xFF6B5647)
        )

        Spacer(modifier = Modifier.height(10.dp))

        // Категории
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            CategoryChip("Кава", selectedCategory == "Coffee") { selectedCategory = "Coffee" }
            CategoryChip("Десерти", selectedCategory == "Dessert") { selectedCategory = "Dessert" }
            CategoryChip("Інше", selectedCategory == "Other") { selectedCategory = "Other" }
        }

        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            color = dividerColor,
            thickness = 1.dp
        )

        // ===== Список кави (LazyColumn) =====
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(max = 220.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(coffeeList) { coffee ->
                CoffeeMiniRow(
                    item = coffee,
                    onClick = { onCoffeeClick(coffee) }
                )
            }
        }

        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp),
            color = dividerColor,
            thickness = 1.dp
        )

        // ===== Наша продукція =====
        Text(
            text = "Наша продукція",
            fontFamily = Playfair,
            fontSize = 22.sp,
            fontWeight = FontWeight.SemiBold,
            color = accentBrown
        )

        Spacer(modifier = Modifier.height(10.dp))

        ProductCard()

        Spacer(modifier = Modifier.height(12.dp))

        TopSellerBanner()

        Spacer(modifier = Modifier.weight(1f))

        // ===== Нижняя навигация =====
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
            .padding(horizontal = 14.dp, vertical = 8.dp)
    ) {
        Text(
            text = text,
            fontFamily = Poppins,
            fontSize = 13.sp,
            color = fg
        )
    }
}

@Composable
fun CoffeeMiniRow(
    item: CoffeeItem,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(18.dp))
            .background(Color(0xFFF7EFE3))
            .clickable { onClick() }
            .padding(horizontal = 10.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = item.imageRes),
            contentDescription = item.name,
            modifier = Modifier
                .size(52.dp)
                .clip(RoundedCornerShape(16.dp)),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.width(10.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = item.name,
                fontFamily = Poppins,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF4A3A2A)
            )
            Text(
                text = "₴${item.price}",
                fontFamily = Poppins,
                fontSize = 12.sp,
                color = Color(0xFF6B3F24)
            )
        }
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
            modifier = Modifier.padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(
                text = "Кава в зернах-Арабіка Еліта 250 г",
                fontFamily = Playfair,
                fontSize = 17.sp,
                fontWeight = FontWeight.SemiBold,
                color = accentBrown
            )

            Image(
                painter = painterResource(id = R.drawable.pack),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(110.dp)
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
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF6BBF59)
                    )
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "₴495",
                        fontFamily = Poppins,
                        fontSize = 12.sp,
                        color = Color(0xFFB0AAA0),
                        textDecoration = TextDecoration.LineThrough
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "₴440",
                        fontFamily = Poppins,
                        fontSize = 16.sp,
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
            modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp),
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

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "Крем-мед з бананом 90 г",
                    fontFamily = Poppins,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium,
                    color = accentBrown
                )
                Text(
                    text = "108 ₴",
                    fontFamily = Poppins,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = accentBrown
                )
            }

            Image(
                painter = painterResource(id = R.drawable.honeybanana),
                contentDescription = null,
                modifier = Modifier
                    .size(72.dp)
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
            .padding(horizontal = 8.dp, vertical = 8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp)
                .clip(RoundedCornerShape(40.dp))
                .background(accentBrown)
                .padding(horizontal = 32.dp, vertical = 10.dp),
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
            modifier = Modifier.size(if (selected) 26.dp else 24.dp)
        )
    }
}

// 🔹 маленький бейдж с количеством товаров
@Composable
private fun CartBadge(
    count: Int,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(16.dp)
            .clip(RoundedCornerShape(50))
            .background(Color(0xFFE45757)),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = if (count > 9) "9+" else count.toString(),
            fontFamily = Poppins,
            fontSize = 10.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color.White,
            textAlign = TextAlign.Center
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MenuScreenPreview() {
    val demoList = listOf(
        CoffeeItem(1, "Капучино", 90, R.drawable.cappuccino),
        CoffeeItem(2, "Лате", 95, R.drawable.latte),
        CoffeeItem(3, "Американо", 70, R.drawable.amerikano),
    )

    MenuScreen(
        coffeeList = demoList,
        cartCount = 2,          // 👈 добавили параметр для превью
        onCoffeeClick = {},
        onCartClick = {},
        onBackClick = {}
    )
}
