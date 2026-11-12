package io.github.atipurde.devradar.ui.interests

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp

private val availableInterests = listOf(
    "android" to "Android",
    "kotlin" to "Kotlin",
    "webdev" to "Webentwicklung",
    "ai" to "Künstliche Intelligenz",
    "security" to "IT-Sicherheit",
    "cloud" to "Cloud",
    "database" to "Datenbanken",
    "devops" to "DevOps"
)

@Composable
fun InterestsScreen(
    selectedInterests: Set<String>,
    onInterestToggle: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            Text(
                text = "Deine Interessen",
                style = MaterialTheme.typography.headlineMedium
            )
        }
        item {
            Text(
                text = "Wähle die Themen aus, die dich interessieren.",
                style = MaterialTheme.typography.bodyLarge
            )
        }
        item {
            Text(
                text = "${selectedInterests.size} Themen ausgewählt",
                style = MaterialTheme.typography.labelLarge
            )
        }
        items(availableInterests, key = { it.first }) { (id, label) ->
            val selected = id in selectedInterests
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 56.dp)
                    .toggleable(
                        value = selected,
                        role = Role.Checkbox,
                        onValueChange = { onInterestToggle(id) }
                    )
                    .padding(horizontal = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(checked = selected, onCheckedChange = null)
                Spacer(modifier = Modifier.width(12.dp))
                Text(text = label, style = MaterialTheme.typography.bodyLarge)
            }
        }
    }
}
