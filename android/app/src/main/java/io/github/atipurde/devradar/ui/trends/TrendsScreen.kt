package io.github.atipurde.devradar.ui.trends

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.atipurde.devradar.domain.trends.TagTrend



@Composable
fun TrendsScreen(
    trends: List<TagTrend>,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            Text(
                text = "Trends",
                style = MaterialTheme.typography.headlineMedium
            )
        }

        item {
            Text(
                text = "Deine Top 5 aus den Artikeln",
                style = MaterialTheme.typography.bodyLarge
            )
        }

        if (trends.isEmpty()) {
            item {
                Text(text = "Keine Trends verfügbar")
            }
        }

        itemsIndexed(
            items = trends,
            key = { _, trend -> trend.tag }
        ) { index, trend ->
            ListItem(
                headlineContent = {
                    Text(text = trend.tag)
                },
                leadingContent = {
                    Text(text = "${index + 1}.")
                },
                trailingContent = {
                    Text(text = "${trend.articleCount} Artikel")
                }
            )
        }
    }
}