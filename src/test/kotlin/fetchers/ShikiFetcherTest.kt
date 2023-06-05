package fetchers

import com.example.anidiff_jvm.entities.EntityStatus
import com.example.anidiff_jvm.entities.ShikiAnimeEntity
import com.example.anidiff_jvm.entities.ShikiMangaEntity
import com.example.anidiff_jvm.fetchers.ShikiFetcher
import com.example.anidiff_jvm.settings.ShikiSettings
import kotlinx.coroutines.runBlocking
import kotlin.test.*

class ShikiFetcherTest {
    private lateinit var shikiSettings: ShikiSettings

    @BeforeTest
    fun setup() {
        shikiSettings = shikiSettingsMock()
    }

    @Test
    fun mangaList() {
        val responseContent = """
            [
                {"target_id":1,"target_title":"test","status":"watching","score":5,"chapters":10,"volumes":1},
                {"target_id":15,"target_title":86,"status":"planned","score":0,"chapters":0,"volumes":0,"extra":true}
            ]
        """.trimIndent()

        val client = buildHttpClientMock(responseContent)
        val shikiFetcher = ShikiFetcher(client = client, settings = shikiSettings)

        val expected = listOf(
            ShikiMangaEntity(1, "test", EntityStatus.Watching, 5, 10, 1),
            ShikiMangaEntity(15, "86", EntityStatus.Planned, 0, 0, 0)
        )
        val actual = runBlocking { shikiFetcher.mangaList() }

        assertContentEquals(expected, actual)
    }

    @Test
    fun animeList() {
        val responseContent = """
            [
                {"target_id":1,"target_title":"test","status":"watching","score":5,"episodes":10},
                {"target_id":15,"target_title":86,"status":"planned","score":0,"episodes":0,"extra":true}
            ]
        """.trimIndent()

        val client = buildHttpClientMock(responseContent)
        val shikiFetcher = ShikiFetcher(client = client, settings = shikiSettings)

        val expected = listOf(
            ShikiAnimeEntity(1, "test", EntityStatus.Watching, 5, 10),
            ShikiAnimeEntity(15, "86", EntityStatus.Planned, 0, 0)
        )
        val actual = runBlocking { shikiFetcher.animeList() }

        assertContentEquals(expected, actual)
    }
}
