package com.example.solvesudokueasy

import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.graphics.ImageDecoder
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.solvesudokueasy.ui.theme.SolveSudokuEasyTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SolveSudokuEasyTheme {
                MainScreen()
            }
        }
    }
}

@Composable
fun MainScreen() {
    val context = LocalContext.current
    val bitmap = remember { mutableStateOf<Bitmap?>(null) }

    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri ->
        uri?.let {
            bitmap.value = MediaStore.Images.Media.getBitmap(context.contentResolver, uri)
        }
    }

    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Button(onClick = { launcher.launch("image/*") }) {
                Text("Nhập ảnh")
            }

            bitmap.value?.let {
                // Hiển thị hoặc xử lý Sudoku tại đây
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    SolveSudokuEasyTheme {
        MainScreen()
    }
}
