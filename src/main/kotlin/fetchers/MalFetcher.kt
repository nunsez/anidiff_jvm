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
import kotlin.RuntimeException
import kotlin.math.ceil

class MalFetcher(
    override val client: HttpClient = HttpClient(CIO),
    override val settings: Settings = Settings
): Fetcher {
    private val jsonFormat = Json {
        ignoreUnknownKeys = true
        isLenient = true
    }

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
        val mangaTotal = getTotalValue(mangaTotalRegex, "No manga total")
        val offsets = offsets(mangaTotal)

        return offsets.flatMap { fetchMangaChunk(it) }
    }

    override suspend fun animeList(): List<MalAnimeEntity> {
        val animeTotal = getTotalValue(animeTotalRegex, "No anime total")
        val offsets = offsets(animeTotal)

        return offsets.flatMap { fetchAnimeChunk(it) }
    }

    suspend fun fetchAnimeChunk(offset: Int): List<MalAnimeEntity> {
        val url = settings.malAnimeUrl(offset)
        val content = fetchChunk(url)

        return jsonFormat.decodeFromString<List<MalAnimeEntity>>(content)
    }

    suspend fun fetchMangaChunk(offset: Int): List<MalMangaEntity> {
        val url = settings.malMangaUrl(offset)
        val content = fetchChunk(url)

        return jsonFormat.decodeFromString<List<MalMangaEntity>>(content)
    }

    private suspend fun fetchChunk(url: String): String {
        return client.get(url).bodyAsText()
    }

    private fun getTotalValue(regex: Regex, message: String): Int {
        val matchResult = regex.find(homePage)
        val value = matchResult
            ?.groups
            ?.get(1)
            ?.value
            ?: throw RuntimeException(message)

        return  valueToInt(value)
    }

    private fun valueToInt(value: String): Int {
        return value.replace(exceptDigitsRegex, "").toInt()
    }

    private fun fetchHomePage(): String {
        val url = settings.malProfileUrl()

        return runBlocking {
            val response = client.get(url)
            return@runBlocking response.bodyAsText()
        }
    }

    private fun offsets(total: Int): List<Int> {
        val chunkSize = 300
        val iterations = ceil(total / chunkSize.toDouble()).toInt()
        return List(iterations) { it * chunkSize }
    }
}