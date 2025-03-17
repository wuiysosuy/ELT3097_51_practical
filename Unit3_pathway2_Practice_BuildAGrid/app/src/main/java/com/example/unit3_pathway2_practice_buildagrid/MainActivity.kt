package com.example.unit3_pathway2_practice_buildagrid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.unit3_pathway2_practice_buildagrid.ui.theme.Unit3_pathway2_Practice_BuildAGridTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            Unit3_pathway2_Practice_BuildAGridTheme {
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .statusBarsPadding(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    TopicGrid(
                        modifier = Modifier.padding(dimensionResource(R.dimen.padding_small))
                    )
                }
            }
        }
    }
}

// Danh sách tĩnh chứa thông tin về các chủ đề
val topics = listOf(
    Triple(R.string.architecture, 250, R.drawable.architecture),
    Triple(R.string.automotive, 180, R.drawable.automotive),
    Triple(R.string.business, 300, R.drawable.business),
    Triple(R.string.crafts, 120, R.drawable.crafts),
    Triple(R.string.culinary, 200, R.drawable.culinary),
    Triple(R.string.design, 150, R.drawable.design),
    Triple(R.string.fashion, 170, R.drawable.fashion),
    Triple(R.string.film, 220, R.drawable.film),
    Triple(R.string.gaming, 400, R.drawable.gaming),
    Triple(R.string.lifestyle, 280, R.drawable.lifestyle),
    Triple(R.string.music, 210, R.drawable.music),
    Triple(R.string.photography, 321, R.drawable.photography),
    Triple(R.string.tech, 500, R.drawable.tech)
)

@Composable
fun TopicGrid(modifier: Modifier = Modifier) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_small)),
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_small)),
        modifier = modifier
    ) {
        items(topics) { (nameRes, courses, imageRes) ->
            TopicCard(nameRes, courses, imageRes)
        }
    }
}

@Composable
fun TopicCard(nameRes: Int, availableCourses: Int, imageRes: Int, modifier: Modifier = Modifier) {
    Card(
        modifier = Modifier.padding(dimensionResource(R.dimen.padding_small)),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row {
            Box {
                Image(
                    painter = painterResource(id = imageRes),
                    contentDescription = null,
                    modifier = modifier.size(68.dp).aspectRatio(1f),
                    contentScale = ContentScale.Crop
                )
            }
            Column {
                Text(
                    text = stringResource(id = nameRes),
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(dimensionResource(R.dimen.padding_medium))
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painter = painterResource(R.drawable.ic_grain),
                        contentDescription = null,
                        modifier = Modifier.padding(dimensionResource(R.dimen.padding_medium))
                    )
                    Text(
                        text = availableCourses.toString(),
                        style = MaterialTheme.typography.labelMedium,
                        modifier = Modifier.padding(start = dimensionResource(R.dimen.padding_small))
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TopicPreview() {
    Unit3_pathway2_Practice_BuildAGridTheme {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TopicCard(R.string.photography, 321, R.drawable.photography)
        }
    }
}