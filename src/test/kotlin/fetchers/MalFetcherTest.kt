package fetchers

import com.example.anidiff_jvm.entities.EntityStatus
import com.example.anidiff_jvm.entities.MalAnimeEntity
import com.example.anidiff_jvm.entities.MalMangaEntity
import com.example.anidiff_jvm.fetchers.MalFetcher
import com.example.anidiff_jvm.settings.MalSettings
import kotlinx.coroutines.runBlocking
import kotlin.test.*

class MalFetcherTest {
    private lateinit var malSettings: MalSettings

    @BeforeTest
    fun setup() {
        malSettings = malSettingsMock()
    }

    @Test
    fun mangaList() {
        val mockEngine = malMangaListHttpMockEngine()
        val client = buildHttpClientMock(mockEngine)
        val malFetcher = MalFetcher(client = client, settings = malSettings)

        val expected = listOf(
            MalMangaEntity(1, "test", EntityStatus.Watching, 5, 10, 1),
            MalMangaEntity(15, "86", EntityStatus.Planned, 0, 0, 0)
        )
        val actual = runBlocking { malFetcher.mangaList() }

        assertContentEquals(expected, actual)
    }

    @Test
    fun animeList() {
        val mockEngine = malAnimeListHttpMockEngine()
        val client = buildHttpClientMock(mockEngine)
        val malFetcher = MalFetcher(client = client, settings = malSettings)

        val expected = listOf(
            MalAnimeEntity(1, "test", EntityStatus.Watching, 5, 10),
            MalAnimeEntity(15, "86", EntityStatus.Planned, 0, 0)
        )
        val actual = runBlocking { malFetcher.animeList() }

        assertContentEquals(expected, actual)
    }
}
