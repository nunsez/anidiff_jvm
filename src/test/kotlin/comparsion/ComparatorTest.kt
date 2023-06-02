package comparsion

import com.example.anidiff_jvm.comparsion.Comparator
import com.example.anidiff_jvm.entities.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*

class ComparatorTest {
    @Test
    fun compareMangaEquals() {
        val shiki = ShikiMangaEntity(1, "shiki", EntityStatus.Completed, 5, 30, 2)
        val mal = MalMangaEntity(1, "mal", EntityStatus.Completed, 5, 30, 2)

        val comparator = Comparator(malList = listOf(mal), shikiList = listOf(shiki))
        val result = comparator.compare()

        assertEquals(emptyList<MangaEntity>(), result)
    }

    @Test
    fun compareMangaNotEquals() {
        val shiki = ShikiMangaEntity(1, "shiki", EntityStatus.Completed, 3, 30, 2)
        val mal = MalMangaEntity(1, "mal", EntityStatus.Completed, 5, 30, 2)

        val comparator = Comparator(malList = listOf(mal), shikiList = listOf(shiki))
        val result = comparator.compare()

        println(shiki.equals(mal))

        assertEquals(listOf(mal), result)
    }

    @Test
    fun compareAnimeEquals() {
        val shiki = ShikiAnimeEntity(1, "shiki", EntityStatus.Completed, 5, 30)
        val mal = MalAnimeEntity(1, "mal", EntityStatus.Completed, 5, 30)

        val comparator = Comparator(malList = listOf(mal), shikiList = listOf(shiki))
        val result = comparator.compare()

        assertEquals(emptyList<AnimeEntity>(), result)
    }

    @Test
    fun compareAnimeNotEquals() {
        val shiki = ShikiAnimeEntity(1, "shiki", EntityStatus.Completed, 3, 30)
        val mal = MalAnimeEntity(1, "mal", EntityStatus.Completed, 5, 30)

        val comparator = Comparator(malList = listOf(mal), shikiList = listOf(shiki))
        val result = comparator.compare()

        assertEquals(listOf(mal), result)
    }
}