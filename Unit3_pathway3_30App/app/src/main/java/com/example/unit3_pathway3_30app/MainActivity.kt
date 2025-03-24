package com.example.unit3_pathway3_30app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.unit3_pathway3_30app.ui.theme.TipListScreen
import com.example.unit3_pathway3_30app.ui.theme.Unit3_pathway3_30AppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Unit3_pathway3_30AppTheme {
                TipListScreen()
            }
        }
    }
}