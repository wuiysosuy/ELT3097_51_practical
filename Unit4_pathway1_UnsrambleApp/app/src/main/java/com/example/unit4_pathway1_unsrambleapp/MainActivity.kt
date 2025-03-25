package com.example.unit4_pathway1_unsrambleapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.unit4_pathway1_unsrambleapp.GameScreen
import com.example.unit4_pathway1_unsrambleapp.ui.theme.Unit4_pathway1_UnsrambleAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            Unit4_pathway1_UnsrambleAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    GameScreen()
                }
            }
        }
    }
}