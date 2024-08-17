package com.movieloom.app

import io.ktor.client.HttpClient
import io.ktor.client.engine.apache5.Apache5

class JVMPlatform: Platform {
    override val name: String = "Java ${System.getProperty("java.version")}"
}

actual fun getPlatform(): Platform = JVMPlatform()

actual fun getClient(): HttpClient = HttpClient(Apache5)