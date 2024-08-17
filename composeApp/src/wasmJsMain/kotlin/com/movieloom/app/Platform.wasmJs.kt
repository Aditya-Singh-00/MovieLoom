package com.movieloom.app

import io.ktor.client.HttpClient

class WasmPlatform: Platform {
    override val name: String = "Web with Kotlin/Wasm"
}

actual fun getPlatform(): Platform = WasmPlatform()

actual fun getClient(): HttpClient = HttpClient()