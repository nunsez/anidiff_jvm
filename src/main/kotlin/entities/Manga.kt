package com.example.anidiff_jvm.entities

import com.example.anidiff_jvm.serializers.MalStatusSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Suppress("EqualsOrHashCode")
abstract class MangaEntity : Entity {
    abstract val chaptersRead: Int
    abstract val volumesRead: Int

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
    @SerialName("target_id")
    override val id: Int,

    @SerialName("target_title")
    override val title: String,

    override val status: EntityStatus,

    override val score: Int,

    @SerialName("chapters")
    override val chaptersRead: Int,

    @SerialName("volumes")
    override val volumesRead: Int
) : MangaEntity()

@Serializable
data class MalMangaEntity(
    @SerialName("manga_id")
    override val id: Int,

    @SerialName("manga_title")
    override val title: String,

    @Serializable(with = MalStatusSerializer::class)
    override val status: EntityStatus,

    override val score: Int,

    @SerialName("num_read_chapters")
    override val chaptersRead: Int,

    @SerialName("num_read_volumes")
    override val volumesRead: Int
) : MangaEntity()
