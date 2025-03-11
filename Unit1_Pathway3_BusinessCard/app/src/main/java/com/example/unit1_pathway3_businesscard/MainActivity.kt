package com.example.unit1_pathway3_businesscard

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.unit1_pathway3_businesscard.ui.theme.Unit1_Pathway3_BusinessCardTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Unit1_Pathway3_BusinessCardTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    BusinessCard()
                }
            }
        }
    }
}

@Composable
fun BusinessCard() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.profile_picture),
            contentDescription = "Profile Picture",
            modifier = Modifier
                .size(150.dp)
                .padding(16.dp),
            contentScale = ContentScale.Crop
        )
        Text(text = "Hoàng Quang Huy", fontSize = 30.sp, color = Color.Black)
        Text(text = "Android Developer", fontSize = 18.sp, color = Color.Green)
        Spacer(modifier = Modifier.height(16.dp))
        ContactInfo()
    }
}

@Composable
fun ContactInfo() {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "📧 huyhotboyk113@gmail.com", fontSize = 16.sp, color = Color.Black)
        Text(text = "📞 0829879928", fontSize = 16.sp, color = Color.Black)
        Text(text = "🌍 https://github.com/wuiysosuy", fontSize = 16.sp, color = Color.Black)
    }
}

@Preview(showBackground = true)
@Composable
fun BusinessCardPreview() {
    Unit1_Pathway3_BusinessCardTheme {
        BusinessCard()
    }
}