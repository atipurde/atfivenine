package io.github.atipurde.devradar.domain.relevance

import java.util.Locale

object RelevanceScorer {

    private val wordSeparator = Regex("[^\\p{L}\\p{N}]+")

    fun score(
        title: String,
        tags: List<String>,
        selectedInterests: Set<String>
    ): Int {
        val titleWords = title
            .lowercase(Locale.ROOT)
            .split(wordSeparator)
            .toSet()

        val normalizedTags = tags
            .map { it.trim().lowercase(Locale.ROOT) }
            .toSet()

        val normalizedInterests = selectedInterests
            .map { it.trim().lowercase(Locale.ROOT) }
            .filter { it.isNotEmpty() }
            .toSet()

        return normalizedInterests.sumOf { interest ->
            val tagPoints = if (interest in normalizedTags) 3 else 0
            val titlePoints = if (interest in titleWords) 1 else 0

            tagPoints + titlePoints
        }
    }

    fun <T> sortByRelevance(
        articles: List<T>,
        selectedInterests: Set<String>,
        titleOf: (T) -> String,
        tagsOf: (T) -> List<String>
    ): List<T> {
        return articles
            .map { article ->
                val points = score(
                    title = titleOf(article),
                    tags = tagsOf(article),
                    selectedInterests = selectedInterests
                )

                article to points
            }
            .sortedByDescending { it.second }
            .map { it.first }
    }
}