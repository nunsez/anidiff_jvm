package com.example.anidiff_jvm.fetchers

import com.example.anidiff_jvm.entities.MalAnimeEntity
import com.example.anidiff_jvm.entities.MalMangaEntity
import com.example.anidiff_jvm.settings.Settings
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.io.File
import java.lang.RuntimeException
import kotlin.math.ceil

object MalFetcher: Fetcher {
    private val jsonFormat = Json {
        ignoreUnknownKeys = true
        isLenient = true
    }
    private val client = HttpClient(CIO)
    private val exceptDigitsRegex = Regex("""\D""")
    private val animeTotalRegex = Regex("""<a.+?class=".*?anime.*?">Completed</a><span.*?>([\d,]{1,7})</span>""")
    private val mangaTotalRegex = Regex("""<a.+?class=".*?manga.*?">Completed</a><span.*?>([\d,]{1,7})</span>""")

    private var homePage: String = ""
        get() {
            if (field == "") {
                field = fetchHomePage()
            }
            return field
        }


    override suspend fun mangaList(): List<MalMangaEntity> {
        val mangaTotal = getMangaTotal()
        val offsets = offsets(mangaTotal)

        return offsets.flatMap { fetchMangaChunk(it) }
    }

    override suspend fun animeList(): List<MalAnimeEntity> {
        val animeTotal = getMangaTotal()
        val offsets = offsets(animeTotal)

        return offsets.flatMap { fetchAnimeChunk(it) }
    }

    suspend fun fetchAnimeChunk(offset: Int): List<MalAnimeEntity> {
        val url = Settings.malAnimeUrl(offset)
        val response = client.get(url)
        val content = response.bodyAsText()

        return jsonFormat.decodeFromString<List<MalAnimeEntity>>(content)
    }

    suspend fun fetchMangaChunk(offset: Int): List<MalMangaEntity> {
        val url = Settings.malMangaUrl(offset)
        val response = client.get(url)
        val content = response.bodyAsText()

        return jsonFormat.decodeFromString<List<MalMangaEntity>>(content)
    }

    fun getAnimeTotal(): Int {
        val matchResult = animeTotalRegex.find(homePage)
        val value = matchResult
            ?.groups
            ?.get(1)
            ?.value
            ?: throw RuntimeException("No anime total")

        return valueToInt(value)
    }

    fun valueToInt(value: String): Int {
        return value.replace(exceptDigitsRegex, "").toInt()
    }

    fun getMangaTotal(): Int {
        val matchResult = mangaTotalRegex.find(homePage)
        val value = matchResult
            ?.groups
            ?.get(1)
            ?.value
            ?: throw RuntimeException("No manga total")

        return valueToInt(value)
    }

    fun fetchHomePage(): String {
        return runBlocking {
            val file = File("mal_profile.html")
            return@runBlocking file.readText()
        }
    }

    fun fetchHomePage2() {
        val url = Settings.malProfileUrl()

        runBlocking {
            val response = client.get(url)
            val content = response.bodyAsText()
            this@MalFetcher.homePage = content
        }
    }

    private fun offsets(total: Int): List<Int> {
        val chunkSize = 300
        val iterations = ceil(total / chunkSize.toDouble()).toInt()
        return List(iterations) { it * chunkSize }
    }
}
