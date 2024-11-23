package com.example.inwakeapp.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.inwakeapp.R
import com.example.inwakeapp.navigation.Screen

@Composable
fun HomeScreen(navController: NavController, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.Background))
            .systemBarsPadding(),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Content(modifier = Modifier.weight(1f))
        BottomButton(navController,modifier = Modifier.padding(bottom = 62.dp))
    }
}


@Composable
fun Content(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 82.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Notes Image",
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
        )
        Text(
            text = "InkWave",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = colorResource(id = R.color.Primary),
            modifier = Modifier.padding(bottom = 8.dp, top = 16.dp)
        )
        Text(
            text = "Capture ideas.",
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
            color = Color.DarkGray,
            modifier = Modifier.padding(top = 16.dp)
        )
        Text(
            text = "Anytime, anywhere.",
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
            color = Color.DarkGray,
            modifier = Modifier.padding(bottom = 16.dp)
        )

    }
}

@Composable
fun BottomButton(navController: NavController, modifier: Modifier = Modifier) {
    Button(
        onClick = { navController.navigate(Screen.ListNotesModule.route) },
        colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.Primary)),
        modifier = modifier
            .width(180.dp)
            .height(42.dp)
    ) {
        Text(text = "Notes")
    }
}

@Preview
@Composable
private fun prevHome() {
    HomeScreen(rememberNavController())
}