package io.github.atipurde.devradar.data.preferences

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.interestsDataStore by preferencesDataStore(
    name = "interests"
)

private val selectedInterestsKey = stringSetPreferencesKey("selected_interests")

class InterestsRepository(context: Context) {
    private val dataStore = context.applicationContext.interestsDataStore

    val selectedInterests: Flow<Set<String>> = dataStore.data.map { preferences ->
        preferences[selectedInterestsKey] ?: emptySet()
    }

    suspend fun toggleInterest(interestId: String) {
        dataStore.edit { preferences ->
            val selected = preferences[selectedInterestsKey] ?: emptySet()

            preferences[selectedInterestsKey] = if (interestId in selected) {
                selected - interestId
            } else {
                selected + interestId
            }
        }
    }
}