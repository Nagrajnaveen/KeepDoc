package com.nags.keepdoc

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform