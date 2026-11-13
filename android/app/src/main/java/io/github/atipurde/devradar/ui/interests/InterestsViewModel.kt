package io.github.atipurde.devradar.ui.interests

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import io.github.atipurde.devradar.data.preferences.InterestsRepository
import java.io.IOException
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class InterestsUiState(
    val selectedInterests: Set<String> = emptySet(),
    val isLoading: Boolean = true,
    val errorMessage: String? = null
)

class InterestsViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = InterestsRepository(application)

    private val _uiState = MutableStateFlow(InterestsUiState())
    val uiState = _uiState.asStateFlow()

    private var loadingJob: Job? = null

    init {
        loadInterests()
    }

    fun loadInterests() {
        loadingJob?.cancel()
        _uiState.update {
            it.copy(isLoading = true, errorMessage = null)
        }

        loadingJob = viewModelScope.launch {
            try {
                repository.selectedInterests.collect { selected ->
                    _uiState.update {
                        it.copy(
                            selectedInterests = selected,
                            isLoading = false,
                            errorMessage = null
                        )
                    }
                }
            } catch (exception: IOException) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = "Die Interessen konnten nicht geladen werden."
                    )
                }
            }
        }
    }

    fun toggleInterest(interestId: String) {
        if (_uiState.value.isLoading || _uiState.value.errorMessage != null) {
            return
        }

        viewModelScope.launch {
            try {
                repository.toggleInterest(interestId)
            } catch (exception: IOException) {
                _uiState.update {
                    it.copy(
                        errorMessage = "Die Auswahl konnte nicht gespeichert werden."
                    )
                }
            }
        }
    }
}