
package com.example.campusmobileapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.campusmobileapp.ui.theme.CampusMobileAppTheme

data class Event(
    val id: Int,
    val title: String,
    val category: String,
    val location: String?,
    val capacity: Int,
    val description: String
)

val events = listOf(
    Event(1, "Conférence Kotlin", "TECH", "Salle 204", 30,
        "Une conférence pour découvrir le langage Kotlin et son utilisation dans les applications Android."),
    Event(2, "Atelier Android", "TECH", "Salle 105", 15,
        "Un atelier pour apprendre à créer une application Android avec Kotlin."),
    Event(3, "Tournoi de football", "SPORT", "Gymnase", 20,
        "Un tournoi de football organisé pour les étudiants du campus."),
    Event(4, "Rencontre étudiants", "CAMPUS", null, 40,
        "Une rencontre entre étudiants pour discuter et faire connaissance."),
    Event(5, "Atelier Compose", "TECH", "Salle 301", 0,
        "Un atelier pour découvrir Jetpack Compose et créer des interfaces Android.")
)

val categories = listOf("TECH", "SPORT", "CAMPUS")

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CampusMobileAppTheme {
                EventListScreen(events)
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CampusMobileAppTheme {
        Greeting("Android")
    }
}

@Composable
fun EventCard(
    event: Event,
    onClick: () -> Unit,
    onBook: () -> Unit
) {
    Card(onClick = onClick) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(event.title)
                Text(event.category)
            }

            Text(event.location ?: "Lieu à confirmer")

            if (event.capacity == 0) {
                Text("Complet")
            } else {
                Text("${event.capacity} places disponibles")
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                TextButton(
                    onClick = onBook,
                    enabled = event.capacity > 0
                ) {
                    Text("Réserver")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun EventCardCompletPreview() {
    CampusMobileAppTheme {
        EventCard(
            event = events[4],
            onClick = {},
            onBook = {}
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventListScreen(events: List<Event>) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Campus") }
            )
        }
    ) { innerPadding ->
        LazyColumn(
            contentPadding = innerPadding
        ) {
            item {
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(categories) { category ->
                        FilterChip(
                            selected = false,
                            onClick = {},
                            label = { Text(category) }
                        )
                    }
                }
            }

            items(events, key = { it.id }) { event ->
                EventCard(
                    event = event,
                    onClick = {
                        Log.d("Campus", "Clic sur ${event.title}")
                    },
                    onBook = {
                        Log.d("Campus", "Réservation ${event.title}")
                    }
                )
            }
        }
    }
}

@Composable
fun EventDetailScreen(
    event: Event,
    onBook: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Image(
            painter = painterResource(R.drawable.ic_launcher_foreground),
            contentDescription = "Image de l'événement",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(160.dp)
        )

        Text(event.title)
        Text(event.category)
        Text(event.location ?: "Lieu à confirmer")

        Text(
            text = event.description,
            modifier = Modifier.padding(top = 16.dp)
        )

        TextButton(
            onClick = onBook,
            enabled = event.capacity > 0
        ) {
            Text("Réserver")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun EventDetailPreview() {
    CampusMobileAppTheme {
        EventDetailScreen(event = events[0])
    }
}
