package com.example.anidiff_jvm

import com.example.anidiff_jvm.comparison.compareEntityLists
import com.example.anidiff_jvm.entities.AnimeEntity
import com.example.anidiff_jvm.entities.MangaEntity
import com.example.anidiff_jvm.fetchers.MalFetcher
import com.example.anidiff_jvm.fetchers.ShikiFetcher
import com.example.anidiff_jvm.settings.Settings
import kotlinx.coroutines.runBlocking

fun main() {
    Settings.init()
    println(Settings)

    runBlocking {
        val malAnime = MalFetcher.animeList()
        val shikiAnime = ShikiFetcher.animeList()

        val malManga = MalFetcher.mangaList()
        val shikiManga = ShikiFetcher.mangaList()

        val animeDiff: List<AnimeEntity> = compareEntityLists(malAnime, shikiAnime)
        val mangaDiff: List<MangaEntity> = compareEntityLists(malManga, shikiManga)

        println("Anime Diff:")
        animeDiff.forEach(::println)

        println("Manga Diff:")
        mangaDiff.forEach(::println)
    }
}
