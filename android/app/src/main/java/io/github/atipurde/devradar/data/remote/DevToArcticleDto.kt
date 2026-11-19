
package io.github.atipurde.devradar.data.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DevToArticleDto(
    val id: Long,
    val title: String,
    val url: String,

    @SerialName("tag_list")
    val tags: List<String> = emptyList()
)
