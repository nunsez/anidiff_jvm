package com.example.anidiff_jvm.entities

import kotlinx.serialization.SerialName

interface Entity {
    val id: Int
    val title: String
    val status: Status
    val score: Int

    @Suppress("unused")
    enum class Status {
        @SerialName("watching") Watching,
        @SerialName("completed") Completed,
        @SerialName("on_hold") OnHold,
        @SerialName("dropped") Dropped,
        @SerialName("planned") Planned
    }
}
