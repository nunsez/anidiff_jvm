package com.example.anidiff_jvm.fetchers

import com.example.anidiff_jvm.entities.AnimeEntity
import com.example.anidiff_jvm.entities.MangaEntity
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import kotlinx.serialization.json.Json

object MalFetcher: Fetcher {
    private val jsonFormat = Json { ignoreUnknownKeys = true }
    private val client = HttpClient(CIO)

    override suspend fun mangaList(): Array<out MangaEntity> {
        TODO("Not yet implemented")
    }

    override suspend fun animeList(): Array<out AnimeEntity> {
        TODO("Not yet implemented")
    }
}
