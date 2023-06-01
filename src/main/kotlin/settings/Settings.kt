package com.example.anidiff_jvm.settings

object Settings {
    private const val shikiPrefix = "https://shikimori.me"
    private const val malPrefix = "https://myanimelist.net"

    private var shikiName: String = ""
    private var malName: String = ""

    fun init() {
        if (shikiName.isBlank()) {
            println("Enter Shikimori Name:")
            shikiName = readValue("SHIKI_NAME")
        }

        if (malName.isBlank()) {
            println("Enter MyAnimelist Name:")
            malName = readValue("MAL_NAME")
        }
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

    private fun readValue(name: String): String {
        val envValue = System.getenv(name)

        return when {
            envValue.isNullOrBlank() -> readln()
            else -> envValue
        }
    }
}
