
package com.example.campusmobileapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.campusmobileapp.ui.theme.CampusMobileAppTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Card
import androidx.compose.material3.TextButton
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import android.util.Log

data class Event(
    val id: Int,
    val title: String,
    val category: String,
    val location: String?,
    val capacity: Int
)

val events = listOf(
    Event(1, "Conférence Kotlin", "TECH", "Salle 204", 30),
    Event(2, "Atelier Android", "TECH", "Salle 105", 15),
    Event(3, "Tournoi de football", "SPORT", "Gymnase", 20),
    Event(4, "Rencontre étudiants", "CAMPUS", null, 40),
    Event(5, "Atelier Compose", "TECH", "Salle 301", 0)
)

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
