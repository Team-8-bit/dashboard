package org.team9432.dashboard.lib.server

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.team9432.dashboard.lib.Dashboard
import org.team9432.dashboard.shared.InitialUpdateMessage

fun Application.configureRoutes() {
    routing {
        get("/") {
            call.respondText("Hello World!")
        }

        get("/currentstate") {
            val widgetDefinitions = Dashboard.getAllWidgets()
            val widgetData = Dashboard.getAllWidgetData()
            val tabData = Dashboard.getAllTabs()

            try {
                call.respond(InitialUpdateMessage(tabData, widgetDefinitions, widgetData))
            } catch (e: Exception) {
                println("Error when sending initial data: ${e.message}")
            }
        }
    }
}