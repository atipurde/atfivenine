package io.github.atipurde.devradar.ui.trends

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.atipurde.devradar.data.remote.DevToApiClient
import io.github.atipurde.devradar.domain.trends.TagTrend
import io.github.atipurde.devradar.domain.trends.TrendAnalyzer
import java.io.IOException
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.serialization.SerializationException

data class TrendsUiState(
    val trends: List<TagTrend> = emptyList(),
    val isLoading: Boolean = true,
    val errorMessage: String? = null
)

class TrendsViewModel : ViewModel() {

    private val apiClient = DevToApiClient()

    private val _uiState = MutableStateFlow(TrendsUiState())
    val uiState = _uiState.asStateFlow()

    private var loadingJob: Job? = null

    init {
        loadTrends()
    }

    fun loadTrends() {
        loadingJob?.cancel()
        _uiState.update {
            it.copy(isLoading = true, errorMessage = null)
        }

        loadingJob = viewModelScope.launch {
            try {
                val articles = apiClient.fetchLatestArticles()

                val trends = TrendAnalyzer.topTags(
                    articleTags = articles.map { it.tags }
                )

                _uiState.value = TrendsUiState(
                    trends = trends,
                    isLoading = false
                )
            } catch (exception: IOException) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = "Die Artikel konnten nicht geladen werden."
                    )
                }
            } catch (exception: SerializationException) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = "Die Artikeldaten konnten nicht verarbeitet werden."
                    )
                }
            }
        }
    }
}