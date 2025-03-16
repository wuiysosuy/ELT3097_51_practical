package com.example.unit2_pathway2_lemonadeapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.unit2_pathway2_lemonadeapp.ui.theme.Unit2_pathway2_LemonadeAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Unit2_pathway2_LemonadeAppTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    LemonadeApp()
                }
            }
        }
    }
}

@Composable
fun LemonadeApp() {
    var currentStep by remember { mutableStateOf(1) }
    var squeezeCount by remember { mutableStateOf(0) }

    val imageResource = when (currentStep) {
        1 -> R.drawable.lemon_tree
        2 -> R.drawable.lemon_squeeze
        3 -> R.drawable.lemon_drink
        else -> R.drawable.lemon_restart
    }

    val contentDescription = when (currentStep) {
        1 -> "Cây chanh"
        2 -> "Chanh"
        3 -> "Ly nước chanh"
        else -> "Ly trống"
    }

    val textLabel = when (currentStep) {
        1 -> "Nhấn vào cây chanh để hái chanh."
        2 -> "Nhấn vào chanh để vắt nước."
        3 -> "Nhấn vào ly để uống nước chanh."
        else -> "Nhấn vào ly trống để bắt đầu lại."
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = textLabel)
        Spacer(modifier = Modifier.height(16.dp))
        Image(
            painter = painterResource(id = imageResource),
            contentDescription = contentDescription,
            modifier = Modifier
                .clickable {
                    currentStep = when (currentStep) {
                        1 -> {
                            squeezeCount = (2..4).random()
                            2
                        }
                        2 -> {
                            squeezeCount--
                            if (squeezeCount == 0) 3 else 2
                        }
                        3 -> 4
                        else -> 1
                    }
                }
                .size(200.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    Unit2_pathway2_LemonadeAppTheme {
        LemonadeApp()
    }
}
