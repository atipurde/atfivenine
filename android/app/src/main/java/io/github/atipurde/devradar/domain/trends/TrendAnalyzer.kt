package io.github.atipurde.devradar.domain.trends

import java.util.Locale

data class TagTrend(
    val tag: String,
    val articleCount: Int
)

object TrendAnalyzer {

    fun topTags(articleTags: List<List<String>>): List<TagTrend> {
        val counts = mutableMapOf<String, Int>()

        for (tags in articleTags) {
            val uniqueTags = tags
                .map { it.trim().lowercase(Locale.ROOT) }
                .filter { it.isNotEmpty() }
                .toSet()

            for (tag in uniqueTags) {
                counts[tag] = (counts[tag] ?: 0) + 1
            }
        }

        return counts
            .map { (tag, count) ->
                TagTrend(
                    tag = tag,
                    articleCount = count
                )
            }
            .sortedWith(
                compareByDescending<TagTrend> { it.articleCount }
                    .thenBy { it.tag }
            )
            .take(5)
    }
}