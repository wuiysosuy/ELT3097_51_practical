package com.example.unit2_pathway3_artspace

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.unit2_pathway3_artspace.ui.theme.Unit2_pathway3_ArtSpaceTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Unit2_pathway3_ArtSpaceTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    ArtSpaceApp()
                }
            }
        }
    }
}

@Composable
fun ArtSpaceApp() {
    val artworks = listOf(
        ArtItem(R.drawable.dice_1, "Xúc xắc mặt số 1", "Hoàng Quang Huy, 2003"),
        ArtItem(R.drawable.dice_2, "Xúc xắc mặt số 2", "Hoàng Quang Huy, 2003"),
        ArtItem(R.drawable.huy2, "Chàng trai nam tính bên điếu thuốc", "Hoàng Quang Huy, 2003"),
        ArtItem(R.drawable.huy1, "Nhìn vào mắt của anh", "Hoàng Quang Huy, 2003"),
        ArtItem(R.drawable.vet, "Chú vẹt nhiều màu", "Hoàng Quang Huy, 2003"),
        ArtItem(R.drawable.profile_picture, "Huy đẹp trai", "Hoàng Quang Huy, 2003")
    )

    var currentIndex by remember { mutableStateOf(0) }
    val currentArt = artworks[currentIndex]

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = currentArt.imageRes),
            contentDescription = currentArt.title,
            modifier = Modifier.size(300.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = currentArt.title, fontSize = 24.sp)
        Text(text = currentArt.artist, fontSize = 16.sp)
        Spacer(modifier = Modifier.height(16.dp))
        Row {
            Button(onClick = {
                if (currentIndex > 0) currentIndex--
            }) {
                Text("Previous")
            }
            Spacer(modifier = Modifier.width(16.dp))
            Button(onClick = {
                if (currentIndex < artworks.size - 1) currentIndex++
            }) {
                Text("Next")
            }
        }
    }
}

data class ArtItem(val imageRes: Int, val title: String, val artist: String)

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    Unit2_pathway3_ArtSpaceTheme {
        ArtSpaceApp()
    }
}