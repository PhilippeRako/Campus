
package com.example.campusmobileapp

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.pluralStringResource
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
        text = stringResource(R.string.hello, name),
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
    Card(
        onClick = onClick,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = event.title,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(event.category)
            }

            Text(event.location ?: stringResource(R.string.location_unknown))

            if (event.capacity == 0) {
                Text(stringResource(R.string.full))
            } else {
                Text(
                    pluralStringResource(
                        R.plurals.available_places,
                        event.capacity,
                        event.capacity
                    )
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                TextButton(
                    onClick = onBook,
                    enabled = event.capacity > 0
                ) {
                    Text(stringResource(R.string.book))
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
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.app_name),
                        color = MaterialTheme.colorScheme.primary,
                        style = MaterialTheme.typography.headlineMedium
                    )
                }
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
            contentDescription = stringResource(R.string.event_image),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(160.dp)
        )

        Text(
            text = event.title,
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary
        )

        Text(event.category)
        Text(event.location ?: stringResource(R.string.location_unknown))

        Text(
            text = event.description,
            modifier = Modifier.padding(top = 16.dp)
        )

        TextButton(
            onClick = onBook,
            enabled = event.capacity > 0
        ) {
            Text(stringResource(R.string.book))
        }
    }
}

@Preview(
    name = "Liste - Clair",
    showBackground = true,
    uiMode = UI_MODE_NIGHT_NO
)
@Composable
fun EventListLightPreview() {
    CampusMobileAppTheme(darkTheme = false) {
        EventListScreen(events)
    }
}

@Preview(
    name = "Liste - Sombre",
    showBackground = true,
    uiMode = UI_MODE_NIGHT_YES
)
@Composable
fun EventListDarkPreview() {
    CampusMobileAppTheme(darkTheme = true) {
        EventListScreen(events)
    }
}

@Preview(
    name = "Detail - Clair",
    showBackground = true,
    uiMode = UI_MODE_NIGHT_NO
)
@Composable
fun EventDetailLightPreview() {
    CampusMobileAppTheme(darkTheme = false) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            EventDetailScreen(event = events[0])
        }
    }
}

@Preview(
    name = "Detail - Sombre",
    showBackground = true,
    uiMode = UI_MODE_NIGHT_YES
)
@Composable
fun EventDetailDarkPreview() {
    CampusMobileAppTheme(darkTheme = true) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            EventDetailScreen(event = events[0])
        }
    }
}
