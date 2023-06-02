package com.example.anidiff_jvm.entities

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class EntityStatus {
    @SerialName("watching")
    Watching,

    @SerialName("completed")
    Completed,

    @SerialName("on_hold")
    OnHold,

    @SerialName("dropped")
    Dropped,

    @SerialName("planned")
    Planned
}
interface Entity {
    val id: Int
    val title: String
    val status: EntityStatus
    val score: Int
}
