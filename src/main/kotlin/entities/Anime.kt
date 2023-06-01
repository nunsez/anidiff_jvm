package com.example.anidiff_jvm.entities

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Suppress("EqualsOrHashCode")
abstract class AnimeEntity : Entity {
    abstract override val id: Int
    abstract override val title: String
    abstract override val status: Entity.Status
    abstract override val score: Int
    abstract val episodesWatched: Int

    fun equals(other: AnimeEntity): Boolean {
        return episodesWatched == other.episodesWatched
                && status == other.status
                && score == other.score
    }

    override fun equals(other: Any?): Boolean {
        return when (other) {
            is AnimeEntity -> equals(other)
            else -> false
        }
    }
}

@Serializable
data class ShikiAnimeEntity(
    @SerialName("target_id") override val id: Int,
    @SerialName("target_title") override val title: String,
    override val status: Entity.Status,
    override val score: Int,
    @SerialName("episodes") override val episodesWatched: Int
) : AnimeEntity()

@Serializable
data class MalAnimeEntity(
    @SerialName("anime_id") override val id: Int,
    @SerialName("anime_title") override val title: String,
    override val status: Entity.Status,
    override val score: Int,
    @SerialName("num_watched_episodes") override val episodesWatched: Int
) : AnimeEntity()
