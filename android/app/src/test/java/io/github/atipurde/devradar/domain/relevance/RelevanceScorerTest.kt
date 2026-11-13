package io.github.atipurde.devradar.domain.relevance

import org.junit.Assert.assertEquals
import org.junit.Test

class RelevanceScorerTest {

    @Test
    fun matchingTitleAndTagGiveFourPoints() {
        val points = RelevanceScorer.score(
            title = "kotlin für einsteiger",
            tags = listOf("kotlin"),
            selectedInterests = setOf("kotlin")
        )

        assertEquals(4, points)
    }

    @Test
    fun matchingTagGivesThreePoints() {
        val points = RelevanceScorer.score(
            title = "android apps entwickeln",
            tags = listOf("android", "kotlin"),
            selectedInterests = setOf("kotlin")
        )

        assertEquals(3, points)
    }

    @Test
    fun unrelatedArticleGivesZeroPoints() {
        val points = RelevanceScorer.score(
            title = "neues aus der cloud",
            tags = listOf("cloud"),
            selectedInterests = setOf("kotlin")
        )

        assertEquals(0, points)
    }

    @Test
    fun articlesAreSortedByDescendingRelevance() {
        data class TestArticle(
            val title: String,
            val tags: List<String>
        )

        val unrelated = TestArticle(
            "neues aus der cloud", listOf("cloud")
        )
        val titleOnly = TestArticle(
            "kotlin für einsteiger", listOf("programming")
        )
        val tagOnly = TestArticle(
            "android apps entwickeln", listOf("android", "kotlin")
        )
        val titleAndTag = TestArticle(
            "kotlin im alltag", listOf("kotlin")
        )

        val sortedArticles = RelevanceScorer.sortByRelevance(
            articles = listOf(unrelated, titleOnly, tagOnly, titleAndTag),
            selectedInterests = setOf("kotlin"),
            titleOf = { it.title },
            tagsOf = { it.tags }
        )

        assertEquals(
            listOf(titleAndTag, tagOnly, titleOnly, unrelated),
            sortedArticles
        )
    }
}