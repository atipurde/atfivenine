package io.github.atipurde.devradar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import io.github.atipurde.devradar.ui.interests.InterestsScreen
import io.github.atipurde.devradar.ui.theme.DevRadarTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DevRadarTheme {
                var selectedInterests by remember {
                    mutableStateOf(emptySet<String>())
                }

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    InterestsScreen(
                        selectedInterests = selectedInterests,
                        onInterestToggle = { id ->
                            selectedInterests = if (id in selectedInterests) {
                                selectedInterests - id
                            } else {
                                selectedInterests + id
                            }
                        },
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}
