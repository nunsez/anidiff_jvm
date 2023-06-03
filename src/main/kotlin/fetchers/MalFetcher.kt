package com.example.anidiff_jvm.fetchers

import com.example.anidiff_jvm.entities.MalAnimeEntity
import com.example.anidiff_jvm.entities.MalMangaEntity
import com.example.anidiff_jvm.settings.Settings
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.coroutines.*
import kotlin.RuntimeException
import kotlin.math.ceil

class MalFetcher(
    override val client: HttpClient = defaultHttpClient(),
    override val settings: Settings = Settings
): Fetcher {
    private val exceptDigitsRegex = Regex("""\D""")
    private val animeTotalRegex = Regex(
        """Anime.Stats.+?Total.Entries</span><span.*?>([\d,]{1,7})</span>""",
        RegexOption.DOT_MATCHES_ALL
    )
    private val mangaTotalRegex = Regex(
        """Manga.Stats.+?Total.Entries</span><span.*?>([\d,]{1,7})</span>""",
        RegexOption.DOT_MATCHES_ALL
    )

    @OptIn(ExperimentalCoroutinesApi::class)
    private val malCoroutineContext = Dispatchers.Default.limitedParallelism(4)

    private var homePage: String = ""
        get() {
            if (field == "") {
                field = runBlocking { fetchHomePage() }
            }

            return field
        }

    override suspend fun mangaList(): List<MalMangaEntity> {
        val mangaTotal = getTotalValue(mangaTotalRegex, "No manga total")
        val offsets = offsets(mangaTotal)

        val jobs = runBlocking {
            offsets.map {
                async(malCoroutineContext) { fetchMangaChunk(it) }
            }
        }

        return jobs.awaitAll().flatten()
    }


    override suspend fun animeList(): List<MalAnimeEntity> {
        val animeTotal = getTotalValue(animeTotalRegex, "No anime total")
        val offsets = offsets(animeTotal)

        val jobs = runBlocking {
            offsets.map {
                async(malCoroutineContext) { fetchAnimeChunk(it) }
            }
        }

        return jobs.awaitAll().flatten()
    }

    private suspend fun fetchAnimeChunk(offset: Int): List<MalAnimeEntity> {
        val url = settings.malAnimeUrl(offset)
        return client.get(url).body()
    }

    private suspend fun fetchMangaChunk(offset: Int): List<MalMangaEntity> {
        val url = settings.malMangaUrl(offset)
        return client.get(url).body()
    }

    private fun getTotalValue(regex: Regex, message: String): Int {
        val matchResult = regex.find(homePage)
        val value = matchResult
            ?.groups
            ?.get(1)
            ?.value
            ?: throw RuntimeException(message)

        return valueToInt(value)
    }

    private fun valueToInt(value: String): Int {
        return value.replace(exceptDigitsRegex, "").toInt()
    }

    private suspend fun fetchHomePage(): String {
        val url = settings.malProfileUrl()
        return client.get(url).bodyAsText()
    }

    private fun offsets(total: Int): List<Int> {
        val chunkSize = 300
        val iterations = ceil(total / chunkSize.toDouble()).toInt()

        return List(iterations) { it * chunkSize }
    }
}
