package com.example.unit3_pathway3_30app.ui.theme


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.unit3_pathway3_30app.model.Tip

@Composable
fun TipItem(tip: Tip) {
    Card(
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
    ) {
        Column {
            Image(
                painter = painterResource(id = tip.imageRes),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = stringResource(id = tip.titleRes),
                    style = MaterialTheme.typography.headlineSmall
                )
                Text(
                    text = stringResource(id = tip.descriptionRes),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}
