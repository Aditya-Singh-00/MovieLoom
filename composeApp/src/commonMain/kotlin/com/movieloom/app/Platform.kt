package com.movieloom.app

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform