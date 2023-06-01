package com.example.anidiff_jvm.entities

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Suppress("EqualsOrHashCode")
abstract class MangaEntity {
    abstract val id: Int
    abstract val title: String
    abstract val status: Status
    abstract val score: Int
    abstract val chaptersRead: Int
    abstract val volumesRead: Int

    @Suppress("unused")
    enum class Status {
        @SerialName("watching") Watching,
        @SerialName("completed") Completed,
        @SerialName("on_hold") OnHold,
        @SerialName("dropped") Dropped,
        @SerialName("planned") Planned
    }

    fun equals(other: MangaEntity): Boolean {
        return chaptersRead == other.chaptersRead
                && volumesRead == other.volumesRead
                && status == other.status
                && score == other.score
    }

    override fun equals(other: Any?): Boolean {
        return when (other) {
            is MangaEntity -> equals(other)
            else -> false
        }
    }
}

@Serializable
data class ShikiMangaEntity(
    @SerialName("target_id") override val id: Int,
    @SerialName("target_title") override val title: String,
    override val status: Status,
    override val score: Int,
    @SerialName("chapters") override val chaptersRead: Int,
    @SerialName("volumes") override val volumesRead: Int
) : MangaEntity()

@Serializable
data class MalMangaEntity(
    @SerialName("manga_id") override val id: Int,
    @SerialName("manga_title") override val title: String,
    override val status: Status,
    override val score: Int,
    @SerialName("num_read_chapters") override val chaptersRead: Int,
    @SerialName("num_read_volumes") override val volumesRead: Int
) : MangaEntity()
