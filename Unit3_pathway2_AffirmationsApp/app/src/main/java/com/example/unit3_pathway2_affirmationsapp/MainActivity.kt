package com.example.unit3_pathway2_affirmationsapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.unit3_pathway2_affirmationsapp.ui.theme.Unit3_pathway2_AffirmationsAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Unit3_pathway2_AffirmationsAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AffirmationsApp()
                }
            }
        }
    }
}

@Composable
fun AffirmationsApp() {
    val affirmationList = listOf(
        AffirmationData(R.string.affirmation1, R.drawable.image1),
        AffirmationData(R.string.affirmation2, R.drawable.image2),
        AffirmationData(R.string.affirmation3, R.drawable.image3),
        AffirmationData(R.string.affirmation4, R.drawable.image4),
        AffirmationData(R.string.affirmation5, R.drawable.image5),
        AffirmationData(R.string.affirmation6, R.drawable.image6),
        AffirmationData(R.string.affirmation7, R.drawable.image7),
        AffirmationData(R.string.affirmation8, R.drawable.image8),
        AffirmationData(R.string.affirmation9, R.drawable.image9),
        AffirmationData(R.string.affirmation10, R.drawable.image10)
    )

    AffirmationList(affirmationList)
}

@Composable
fun AffirmationList(affirmationList: List<AffirmationData>, modifier: Modifier = Modifier) {
    LazyColumn(modifier = modifier.padding(16.dp)) {
        items(affirmationList) { affirmation ->
            AffirmationCard(
                affirmation = affirmation,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}

@Composable
fun AffirmationCard(affirmation: AffirmationData, modifier: Modifier = Modifier) {
    Card(modifier = modifier.fillMaxWidth()) {
        Column {
            Image(
                painter = painterResource(affirmation.imageResourceId),
                contentDescription = stringResource(affirmation.stringResourceId),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(194.dp),
                contentScale = ContentScale.Crop
            )
            Text(
                text = LocalContext.current.getString(affirmation.stringResourceId),
                modifier = Modifier.padding(16.dp),
                style = MaterialTheme.typography.headlineSmall
            )
        }
    }
}

data class AffirmationData(val stringResourceId: Int, val imageResourceId: Int)

@Preview
@Composable
private fun AffirmationCardPreview() {
    AffirmationCard(AffirmationData(R.string.affirmation1, R.drawable.image1))
}
