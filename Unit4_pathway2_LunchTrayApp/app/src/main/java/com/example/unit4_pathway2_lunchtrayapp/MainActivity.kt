package com.example.unit4_pathway2_lunchtrayapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.unit4_pathway2_lunchtrayapp.ui.theme.Unit4_pathway2_LunchTrayAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Unit4_pathway2_LunchTrayAppTheme {
                LunchTrayApp()
            }
        }
    }
}