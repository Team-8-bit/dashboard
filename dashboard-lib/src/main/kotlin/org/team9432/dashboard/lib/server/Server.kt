package org.team9432.dashboard.lib.server

import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import org.team9432.dashboard.lib.Dashboard
import org.team9432.dashboard.lib.server.Websocket.configureSocket
import org.team9432.dashboard.shared.Sendable

object Server {
    private lateinit var coroutineScope: CoroutineScope

    suspend fun run(): Unit = coroutineScope {
        coroutineScope = this

        embeddedServer(Netty, port = 8080, host = "0.0.0.0") {
            install(ContentNegotiation) {
                json(Json { encodeDefaults = false })
            }

            configureRoutes()
            configureSocket()
        }.start(wait = false)
    }

    fun sendToAll(value: Sendable) {
        coroutineScope.launch(Dashboard.coroutineContext) {
            Websocket.sendToAll(value)
        }
    }
}