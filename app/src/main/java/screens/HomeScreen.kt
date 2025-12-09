package com.example.brenzoapp.screens

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.brenzoapp.R
import com.example.brenzoapp.ui.theme.Poppins

@Composable
fun HomeScreen(
    onOrderClick: () -> Unit
) {

    var isPressed by remember { mutableStateOf(false) }
    val interactionSource = remember { MutableInteractionSource() }


    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.96f else 1f,
        label = "scale"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFEDE3D2))
            .padding(horizontal = 2.dp, vertical = 32.dp)
    ) {

        // верх: логотип + текст + ілюстрація
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Image(
                painter = painterResource(id = R.drawable.brenzo_logo),
                contentDescription = "Brenzo logo",
                modifier = Modifier.size(260.dp)
            )

            Spacer(modifier = Modifier.height(2.dp))

            Text(
                text = "Brenzo — кава, що створює настрій.",
                fontFamily = Poppins,
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center,
                color = Color(0xFF4A3A2A)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Відкрийте світ своїх смаків.",
                fontFamily = Poppins,
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal,
                textAlign = TextAlign.Center,
                color = Color(0xFF6B5647)
            )

            Spacer(modifier = Modifier.height(40.dp))

            Image(
                painter = painterResource(id = R.drawable.barista_brenzo),
                contentDescription = "Бариста готує каву",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(260.dp),
                contentScale = ContentScale.Fit
            )
        }

        // кнопка «Замовити зараз» з тінню й анімацією масштабу
        Button(
            onClick = { onOrderClick() },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(horizontal = 40.dp, vertical = 24.dp)
                .height(56.dp)
                .scale(scale),                // тільки scale, БЕЗ alpha
            shape = RoundedCornerShape(32.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF6B3F24),
                contentColor = Color(0xFFFFF4E5)
            ),
            elevation = ButtonDefaults.buttonElevation(
                defaultElevation = 10.dp,
                pressedElevation = 14.dp,
                focusedElevation = 12.dp,
                hoveredElevation = 12.dp
            ),
            interactionSource = interactionSource
        ) {
            Text(
                text = "Замовити зараз",
                fontFamily = Poppins,
                fontSize = 17.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@androidx.compose.ui.tooling.preview.Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun HomeScreenPreview() {
    HomeScreen(onOrderClick = {})
}
