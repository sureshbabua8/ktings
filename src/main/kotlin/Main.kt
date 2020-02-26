package hello

import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.ContentNegotiation
import io.ktor.gson.gson
import io.ktor.http.HttpStatusCode
import io.ktor.response.respond
import io.ktor.response.respondText
import io.ktor.routing.get
import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty

fun hello(): String {
    return "Hello, world!"
}
data class AddResult(val first: Int, val second: Int, val result: Int)

fun Application.adder() {
    val counts: MutableMap<String, Int> = mutableMapOf()
    install(ContentNegotiation) {
        gson { }
    }
    routing {
        get("/") {
            call.respondText(hello())
        }
        get("/count/{first}") {
            val firstCount = counts.getOrDefault(call.parameters["first"], 0) + 1
            counts[call.parameters["first"].toString()] = firstCount
            call.respondText(firstCount.toString())
        }
        get("/add/{first}/{second}") {
            try {
                val first = call.parameters["first"]!!.toInt()
                val second = call.parameters["second"]!!.toInt()
                val addResult = AddResult(first, second, first + second)
                call.respond(addResult)
            } catch (e: Exception) {
                call.respond(HttpStatusCode.BadRequest)
            }
        }
    }
}
fun main() {
    embeddedServer(Netty, port = 8008, module = Application::adder).start(wait = true)
}
