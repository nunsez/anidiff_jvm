package com.example.anidiff_jvm.fetchers

import com.example.anidiff_jvm.entities.AnimeEntity
import com.example.anidiff_jvm.entities.MangaEntity

interface Fetcher {
    suspend fun mangaList(): Array<out MangaEntity>
    suspend fun animeList(): Array<out AnimeEntity>
}
