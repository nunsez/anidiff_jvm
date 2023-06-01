package com.example.anidiff_jvm

import com.example.anidiff_jvm.fetchers.MalFetcher
import com.example.anidiff_jvm.settings.Settings
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File

fun main(args: Array<String>) {
    Settings.init()
    println(Settings)

//    runBlocking {
//        val manga = MalFetcher.mangaList()
//        val content = Json.encodeToString(manga)
//        println(manga)
//
//        File("mal_json.txt").writeText(content)
//    }

}
