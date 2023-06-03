package com.example.anidiff_jvm.fetchers

import com.example.anidiff_jvm.entities.ShikiAnimeEntity
import com.example.anidiff_jvm.settings.Settings
import com.example.anidiff_jvm.entities.ShikiMangaEntity
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*

class ShikiFetcher(
    override val client: HttpClient = defaultHttpClient(),
    override val settings: Settings = Settings
): Fetcher {
    override suspend fun mangaList(): List<ShikiMangaEntity> {
        val url = settings.shikiMangaUrl()
        return client.get(url).body()
    }

    override suspend fun animeList(): List<ShikiAnimeEntity> {
        val url = settings.shikiAnimeUrl()
        return client.get(url).body()
    }
}
