package ru.kulishov.markweave

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform