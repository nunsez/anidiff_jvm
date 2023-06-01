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
2
object ShikiFetcher {
    private val jsonFormat = Json { ignoreUnknownKeys = true }
    private val client = HttpClient(CIO)

    suspend fun mangaList(): Array<ShikiMangaEntity> {
        val url = Settings.shikiMangaUrl()
        val response = client.get(url)
        val content = response.bodyAsText()

        return jsonFormat.decodeFromString<Array<ShikiMangaEntity>>(content)
    }

    suspend fun animeList(): Array<ShikiAnimeEntity> {
        val url = Settings.shikiAnimeUrl()
        val response = client.get(url)
        val content = response.bodyAsText()

        return jsonFormat.decodeFromString<Array<ShikiAnimeEntity>>(content)
    }
}
