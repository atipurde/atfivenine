package io.github.atipurde.devradar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import io.github.atipurde.devradar.ui.interests.InterestsScreen
import io.github.atipurde.devradar.ui.interests.InterestsViewModel
import io.github.atipurde.devradar.ui.theme.DevRadarTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            DevRadarTheme {
                val interestsViewModel: InterestsViewModel = viewModel()
                val uiState by interestsViewModel.uiState.collectAsStateWithLifecycle()

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    ) {
                        val errorMessage = uiState.errorMessage

                        when {
                            uiState.isLoading -> {
                                CircularProgressIndicator(
                                    modifier = Modifier.align(Alignment.Center)
                                )
                            }

                            errorMessage != null -> {
                                Column(
                                    modifier = Modifier
                                        .align(Alignment.Center)
                                        .padding(16.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Text(text = errorMessage)

                                    TextButton(
                                        onClick = interestsViewModel::loadInterests
                                    ) {
                                        Text(text = "Erneut versuchen")
                                    }
                                }
                            }

                            else -> {
                                InterestsScreen(
                                    selectedInterests = uiState.selectedInterests,
                                    onInterestToggle = interestsViewModel::toggleInterest
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}