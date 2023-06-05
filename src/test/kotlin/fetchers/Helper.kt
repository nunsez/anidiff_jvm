package fetchers

import com.example.anidiff_jvm.settings.MalSettings
import com.example.anidiff_jvm.settings.ShikiSettings
import io.ktor.client.*
import io.ktor.client.engine.mock.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

internal fun buildHttpClientMock(content: String): HttpClient {
    val mockEngine = buildHttpMockEngine(content)

    return HttpClient(mockEngine) {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                isLenient = true
            })
        }
    }
}

internal fun buildHttpClientMock(mockEngine: MockEngine): HttpClient {
    return HttpClient(mockEngine) {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                isLenient = true
            })
        }
    }
}

internal fun buildHttpMockEngine(
    content: String,
    status: HttpStatusCode = HttpStatusCode.OK
): MockEngine {
    val headers = headersOf(HttpHeaders.ContentType, "application/json")

    return MockEngine {
        respond(content, status, headers)
    }
}

internal fun shikiSettingsMock(): ShikiSettings {
    return object : ShikiSettings {
        override fun shikiMangaUrl(): String {
            return "/mangas"
        }

        override fun shikiAnimeUrl(): String {
            return "/animes"
        }
    }
}

internal fun malSettingsMock(): MalSettings {
    return object : MalSettings {
        override fun malProfileUrl(): String {
            return "/profile"
        }

        override fun malAnimeUrl(offset: Int): String {
            return "/animes/$offset"
        }

        override fun malMangaUrl(offset: Int): String {
            return "/mangas/$offset"
        }
    }
}

internal fun malMangaListHttpMockEngine() = MockEngine {
    val headers = headersOf(HttpHeaders.ContentType, "application/json")

    when (val path = it.url.fullPath) {
        "/profile" -> {
            respond(malProfilePageMock(), headers = headers)
        }

        "/mangas/0" -> {
            val content = """
                [{"manga_id":1,"manga_title":"test","status":1,"score":5,"num_read_chapters":10,"num_read_volumes":1}]
            """.trimIndent()
            respond(content, headers = headers)
        }

        "/mangas/300" -> {
            val content = """
                [{"manga_id":15,"manga_title":86,"status":6,"score":0,"num_read_chapters":0,"num_read_volumes":0,"extra":true}]
            """.trimIndent()
            respond(content, headers = headers)
        }

        else -> {
            throw RuntimeException("Unexpected path: $path")
        }
    }
}

internal fun malAnimeListHttpMockEngine() = MockEngine {
    val headers = headersOf(HttpHeaders.ContentType, "application/json")

    when (val path = it.url.fullPath) {
        "/profile" -> {
            respond(malProfilePageMock(), headers = headers)
        }

        "/animes/0" -> {
            val content = """
                [{"anime_id":1,"anime_title":"test","status":1,"score":5,"num_watched_episodes":10}]
            """.trimIndent()
            respond(content, headers = headers)
        }

        "/animes/300" -> {
            val content = """
                [{"anime_id":15,"anime_title":86,"status":6,"score":0,"num_watched_episodes":0,"extra":true}]
            """.trimIndent()
            respond(content, headers = headers)
        }

        else -> {
            throw RuntimeException("Unexpected path: $path")
        }
    }
}

internal fun malProfilePageMock(animeTotal: Int = 500, mangaTotal: Int = 500): String {
    return """
    <div class="">
        <div class="">
            <h5>Anime Stats</h5>
            <ul class="">
                <li class="">
                    <span class="">Total Entries</span><span class="">$animeTotal</span>
                </li>
            </ul>
        </div>
        <div class="">
            <h5>Manga Stats</h5>
            <ul class="">
                <li class="">
                    <span class="">Total Entries</span><span class="">$mangaTotal</span>
                </li>
            </ul>
        </div>
    </div>
    """.trimIndent()
}
