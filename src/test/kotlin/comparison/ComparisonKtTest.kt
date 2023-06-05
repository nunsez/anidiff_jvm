package comparison

import com.example.anidiff_jvm.comparison.compareEntityLists
import com.example.anidiff_jvm.entities.*
import kotlin.test.*

class ComparisonKtTest {
    @Test
    fun compareManga() {
        val shiki = listOf(
            ShikiMangaEntity(1, "same", EntityStatus.Completed, 3, 30, 2),
            ShikiMangaEntity(3, "diff status", EntityStatus.Dropped, 7, 15, 1)
        )

        val mal = listOf(
            MalMangaEntity(1, "same", EntityStatus.Completed, 3, 30, 2),
            MalMangaEntity(2, "extra entity", EntityStatus.Planned, 0, 0, 0),
            MalMangaEntity(3, "diff status", EntityStatus.Watching, 7, 15, 1),
        )

        val expected = listOf(2, 3)
        val actual = compareEntityLists(mal, shiki).map { it.id }.sorted()

        assertEquals(expected, actual)
    }

    @Test
    fun compareAnime() {
        val shiki = listOf(
            ShikiAnimeEntity(1, "same", EntityStatus.Completed, 8, 12),
            ShikiAnimeEntity(2, "extra entity", EntityStatus.Planned, 0, 0),
            ShikiAnimeEntity(3, "diff score", EntityStatus.Watching, 7, 4),
        )

        val mal = listOf(
            MalAnimeEntity(1, "same", EntityStatus.Completed, 8, 12),
            MalAnimeEntity(3, "diff score", EntityStatus.Watching, 5, 4)
        )

        val expected = listOf(2, 3)
        val actual = compareEntityLists(mal, shiki).map { it.id }.sorted()

        assertEquals(expected, actual)
    }
}
