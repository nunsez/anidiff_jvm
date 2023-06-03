package com.example.anidiff_jvm.fetchers

import com.example.anidiff_jvm.entities.AnimeEntity
import com.example.anidiff_jvm.entities.MangaEntity
import com.example.anidiff_jvm.settings.Settings
import io.ktor.client.*

interface Fetcher {
    val client: HttpClient
    val settings: Settings

    suspend fun mangaList(): List<MangaEntity>
    suspend fun animeList(): List<AnimeEntity>
}
