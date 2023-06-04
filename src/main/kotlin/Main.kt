package com.example.anidiff_jvm

import com.example.anidiff_jvm.comparison.compareEntityLists
import com.example.anidiff_jvm.entities.AnimeEntity
import com.example.anidiff_jvm.entities.MangaEntity
import com.example.anidiff_jvm.fetchers.MalFetcher
import com.example.anidiff_jvm.fetchers.ShikiFetcher
import com.example.anidiff_jvm.settings.Settings

suspend fun main() {
    Settings.init()

    val malFetcher = MalFetcher()
    val shikiFetcher = ShikiFetcher()

    val malAnime = malFetcher.animeList()
    val shikiAnime = shikiFetcher.animeList()

    val malManga = malFetcher.mangaList()
    val shikiManga = shikiFetcher.mangaList()

    val animeDiff: List<AnimeEntity> = compareEntityLists(malAnime, shikiAnime)
    val mangaDiff: List<MangaEntity> = compareEntityLists(malManga, shikiManga)

    println("Anime Diff:")
    animeDiff.forEach(::println)

    println("Manga Diff:")
    mangaDiff.forEach(::println)

    println("Press enter to exit")
    readln()
}
