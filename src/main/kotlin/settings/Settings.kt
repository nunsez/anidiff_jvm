package com.example.anidiff_jvm.settings

interface ShikiSettings {
    fun shikiAnimeUrl(): String
    fun shikiMangaUrl(): String
}

interface MalSettings {
    fun malProfileUrl(): String
    fun malAnimeUrl(offset: Int): String
    fun malMangaUrl(offset: Int): String
}

object Settings : ShikiSettings, MalSettings {
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

    override fun shikiMangaUrl(): String {
        return "$shikiPrefix/$shikiName/list_export/mangas.json"
    }

    override fun shikiAnimeUrl(): String {
        return "$shikiPrefix/$shikiName/list_export/animes.json"
    }

    override fun malMangaUrl(offset: Int): String {
        return "$malPrefix/mangalist/$malName/load.json?offset=${offset}"
    }

    override fun malAnimeUrl(offset: Int): String {
        return "$malPrefix/animelist/$malName/load.json?offset=${offset}"
    }

    override fun malProfileUrl(): String {
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
