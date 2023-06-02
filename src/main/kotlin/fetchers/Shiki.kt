package com.example.anidiff_jvm.fetchers

import com.example.anidiff_jvm.entities.ShikiAnimeEntity
import com.example.anidiff_jvm.settings.Settings
import com.example.anidiff_jvm.entities.ShikiMangaEntity
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

object ShikiFetcher: Fetcher {
    private val jsonFormat = Json {
        ignoreUnknownKeys = true
        isLenient = true
    }
    private val client = HttpClient(CIO)

    override suspend fun mangaList(): List<ShikiMangaEntity> {
        val url = Settings.shikiMangaUrl()
        val response = client.get(url)
        val content = response.bodyAsText()

        return jsonFormat.decodeFromString<List<ShikiMangaEntity>>(content)
    }

    override suspend fun animeList(): List<ShikiAnimeEntity> {
        val url = Settings.shikiAnimeUrl()
        val response = client.get(url)
        val content = response.bodyAsText()

        return jsonFormat.decodeFromString<List<ShikiAnimeEntity>>(content)
    }
}
