package com.example.unit3_pathway3_30app.ui.theme


import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.unit3_pathway3_30app.data.TipList

@Composable
fun TipListScreen() {
    Surface {
        LazyColumn(modifier = Modifier.padding(8.dp)) {
            items(TipList.tips) { tip ->
                TipItem(tip = tip)
            }
        }
    }
}
