package com.example.anidiff_jvm.fetchers

import com.example.anidiff_jvm.entities.AnimeEntity
import com.example.anidiff_jvm.entities.MangaEntity

interface Fetcher {
    suspend fun mangaList(): List<MangaEntity>
    suspend fun animeList(): List<AnimeEntity>
}
