package fetchers

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

internal fun buildHttpMockEngine(content: String): MockEngine {
    return MockEngine {
        respond(
            content = content,
            status = HttpStatusCode.OK,
            headers = headersOf(HttpHeaders.ContentType, "application/json")
        )
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
