package io.github.atipurde.devradar.data.remote

import android.util.Log
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DevToApiClientLiveTest {

    @Test
    fun fetchArticlesFromDevTo() = runBlocking {

        // Unseren API-Client erstellen
        val apiClient = DevToApiClient()

        // Echte Artikel von DEV.to abrufen
        val articles = apiClient.fetchLatestArticles()

        // Prüfen, ob Artikel angekommen sind
        assertTrue(
            "Es wurden keine Artikel geladen",
            articles.isNotEmpty()
        )

        // Titel und Tags im Logcat ausgeben
        articles.forEach { article ->
            Log.d("DevRadarApi", "Titel: ${article.title}")
            Log.d(
                "DevRadarApi",
                "Tags: ${article.tags.joinToString()}"
            )
        }
    }
}
