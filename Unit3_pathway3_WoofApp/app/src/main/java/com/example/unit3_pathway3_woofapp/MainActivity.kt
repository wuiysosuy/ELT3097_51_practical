package com.example.unit3_pathway3_woofapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.*
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.unit3_pathway3_woofapp.ui.theme.Unit3_pathway3_WoofAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Unit3_pathway3_WoofAppTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    WoofApp()
                }
            }
        }
    }
}

// Dữ liệu danh sách chó
val dogs = listOf(
    Dog(R.string.dog_name_1, 2, R.string.dog_description_1, R.drawable.koda),
    Dog(R.string.dog_name_2, 3, R.string.dog_description_2, R.drawable.lola),
    Dog(R.string.dog_name_3, 4, R.string.dog_description_3, R.drawable.frankie),
    Dog(R.string.dog_name_4, 5, R.string.dog_description_4, R.drawable.nox),
    Dog(R.string.dog_name_5, 1, R.string.dog_description_5, R.drawable.faye),
    Dog(R.string.dog_name_6, 6, R.string.dog_description_6, R.drawable.bella),
    Dog(R.string.dog_name_7, 2, R.string.dog_description_7, R.drawable.moana),
    Dog(R.string.dog_name_8, 4, R.string.dog_description_8, R.drawable.tzeitel),
    Dog(R.string.dog_name_9, 3, R.string.dog_description_9, R.drawable.leroy)
)

data class Dog(
    @StringRes val name: Int,
    val age: Int,
    @StringRes val hobbies: Int,
    @DrawableRes val imageResourceId: Int
)

@Composable
fun WoofApp() {
    Scaffold(topBar = { WoofTopAppBar() }) { paddingValues ->
        LazyColumn(contentPadding = paddingValues) {
            items(dogs) { dog ->
                DogItem(dog, modifier = Modifier.padding(8.dp))
            }
        }
    }
}

@Composable
fun DogItem(dog: Dog, modifier: Modifier = Modifier) {
    var expanded by remember { mutableStateOf(false) }
    Card(modifier = modifier) {
        Column(modifier = Modifier.animateContentSize(animationSpec = spring(Spring.DampingRatioNoBouncy, Spring.StiffnessMedium))) {
            Row(modifier = Modifier.fillMaxWidth().padding(8.dp)) {
                DogIcon(dog.imageResourceId)
                DogInformation(dog.name, dog.age)
                Spacer(Modifier.weight(1f))
                DogItemButton(expanded = expanded, onClick = { expanded = !expanded })
            }
            if (expanded) {
                DogHobby(dog.hobbies, modifier = Modifier.padding(8.dp))
            }
        }
    }
}

@Composable
private fun DogItemButton(expanded: Boolean, onClick: () -> Unit, modifier: Modifier = Modifier) {
    IconButton(onClick = onClick, modifier = modifier) {
        Icon(imageVector = if (expanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore, contentDescription = stringResource(R.string.expand_button_content_description), tint = MaterialTheme.colorScheme.secondary)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WoofTopAppBar(modifier: Modifier = Modifier) {
    CenterAlignedTopAppBar(
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    modifier = Modifier.size(48.dp).padding(8.dp),
                    painter = painterResource(R.drawable.ic_woof_logo),
                    contentDescription = null
                )
                Text(text = stringResource(R.string.app_name), style = MaterialTheme.typography.displayLarge)
            }
        },
        modifier = modifier
    )
}

@Composable
fun DogIcon(@DrawableRes dogIcon: Int, modifier: Modifier = Modifier) {
    Image(
        modifier = modifier.size(72.dp).padding(8.dp).clip(MaterialTheme.shapes.small),
        contentScale = ContentScale.Crop,
        painter = painterResource(dogIcon),
        contentDescription = null
    )
}

@Composable
fun DogInformation(@StringRes dogName: Int, dogAge: Int, modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Text(text = stringResource(dogName), style = MaterialTheme.typography.displayMedium, modifier = Modifier.padding(top = 4.dp))
        Text(text = stringResource(R.string.years_old, dogAge), style = MaterialTheme.typography.bodyLarge)
    }
}

@Composable
fun DogHobby(@StringRes dogHobby: Int, modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Text(text = stringResource(R.string.about), style = MaterialTheme.typography.labelSmall)
        Text(text = stringResource(dogHobby), style = MaterialTheme.typography.bodyLarge)
    }
}

@Preview
@Composable
fun WoofPreview() {
    Unit3_pathway3_WoofAppTheme(darkTheme = false) { WoofApp() }
}

@Preview
@Composable
fun WoofDarkThemePreview() {
    Unit3_pathway3_WoofAppTheme(darkTheme = true) { WoofApp() }
}