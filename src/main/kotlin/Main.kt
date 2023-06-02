package com.example.anidiff_jvm

import com.example.anidiff_jvm.comparsion.Comparator
import com.example.anidiff_jvm.fetchers.MalFetcher
import com.example.anidiff_jvm.fetchers.ShikiFetcher
import com.example.anidiff_jvm.settings.Settings
import kotlinx.coroutines.runBlocking

fun main(args: Array<String>) {
    Settings.init()
    println(Settings)

    runBlocking {
        val malAnime = MalFetcher.animeList()
        val shikiAnime = ShikiFetcher.animeList()

        val malManga = MalFetcher.mangaList()
        val shikiManga = ShikiFetcher.mangaList()

        val animeDiff = Comparator(malList = malAnime, shikiList = shikiAnime).compare()
        val mangaDiff = Comparator(malList = malManga, shikiList = shikiManga).compare()

        println("Anime Diff:")
        animeDiff.forEach(::println)

        println("Manga Diff:")
        mangaDiff.forEach(::println)
    }
}
