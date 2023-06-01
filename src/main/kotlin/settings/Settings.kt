package com.example.anidiff_jvm.settings

object Settings {
    private const val shikiPrefix = "https://shikimori.me"
    private const val malPrefix = "https://myanimelist.net"

    private lateinit var shikiName: String
    private lateinit var malName: String

    fun init() {
        println("Enter Shikimori Name:")
        shikiName = readln()

        println("Enter MyAnimelist Name:")
        malName = readln()
    }

    fun shikiMangaUrl(): String {
        return "$shikiPrefix/$shikiName/list_export/mangas.json"
    }

    fun shikiAnimeUrl(): String {
        return "$shikiPrefix/$shikiName/list_export/animes.json"
    }

    fun malMangaUrl(offset: Int): String {
        return "$malPrefix/mangalist/$malName/load.json?offset=${offset}"
    }

    fun malAnimeUrl(offset: Int): String {
        return "$malPrefix/animelist/$malName/load.json?offset=${offset}"
    }

    fun malProfileUrl(): String {
        return "$malPrefix/profile/$malName"
    }

    override fun toString(): String {
        return """
            Settings(shikiPrefix="$shikiPrefix", shikiName="$shikiName", malPrefix="$malPrefix", malName="$malName")
        """.trimIndent()
    }
}
