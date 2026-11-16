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
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import io.github.atipurde.devradar.ui.trends.TrendsScreen

@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            DevRadarTheme {
                val interestsViewModel: InterestsViewModel = viewModel()
                val uiState by interestsViewModel.uiState.collectAsStateWithLifecycle()
                var selectedTab by rememberSaveable { mutableStateOf(0) }


                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        PrimaryTabRow(
                            selectedTabIndex = selectedTab,
                            modifier = Modifier.statusBarsPadding()
                        ) {
                            Tab(
                                selected = selectedTab == 0,
                                onClick = { selectedTab = 0 },
                                text = { Text("Interessen") }
                            )
                            Tab(
                                selected = selectedTab == 1,
                                onClick = { selectedTab = 1 },
                                text = { Text("Trends") }
                            )
                        }
                    }
                ) { innerPadding ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    ) {
                        val errorMessage = uiState.errorMessage

                        when {
                            selectedTab == 1 -> {
                                TrendsScreen(trends = emptyList())
                            }
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