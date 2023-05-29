package com.sekthdroid.projek.template.domain.model

data class Season(
    val number: Int,
    val name: String,
    val episodes: List<Episode> = emptyList()
)

fun List<Episode>.asSeasons(): List<Season> {
    return groupBy { it.episode.takeWhile { each -> each != 'E' } }
        .map {
            Season(
                it.key.replace("S", "").toInt(),
                it.key,
                it.value
            )
        }
        .sortedBy { it.number }
}