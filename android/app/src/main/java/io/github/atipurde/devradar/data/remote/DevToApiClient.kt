
package io.github.atipurde.devradar.data.remote

import java.io.IOException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import okhttp3.Request

class DevToApiClient {

    private val client = OkHttpClient()

    private val json = Json {
        ignoreUnknownKeys = true
    }

    suspend fun fetchLatestArticles(): List<DevToArticleDto> {

        return withContext(Dispatchers.IO) {

            val request = Request.Builder()
                .url("https://dev.to/api/articles/latest?per_page=10")
                .build()

            client.newCall(request).execute().use { response ->

                if (!response.isSuccessful) {
                    throw IOException(
                        "API-Fehler: HTTP ${response.code}"
                    )
                }

                val responseBody = response.body?.string()
                    ?: throw IOException("Leere API-Antwort")

                json.decodeFromString<List<DevToArticleDto>>(
                    responseBody
                )
            }
        }
    }
}
