package hello

import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.ContentNegotiation
import io.ktor.gson.gson
import io.ktor.http.HttpStatusCode
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.response.respondText
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty

fun hello(): String {
    return "Hello, world!"
}

data class Result(val operation: String, val first: Int, val second: Int, val result: Int)
data class CalculatorRequest(
    val operation: String, val first: Int, val second: Int
) {
    val result: Result
    init {
        val mathResult = when (operation) {
            "add" -> first + second
            "multiply" -> first * second
            else -> throw Exception("${operation} is not supported")
        }
        result = Result(operation, first, second, mathResult)
    }
}

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
        post("/calculate") {
            try {
                val request = call.receive<CalculatorRequest>()
                call.respond(request.result)
            } catch (e: Exception) {
                call.respond(HttpStatusCode.BadRequest)
            }
        }
    }
}
fun main() {
    embeddedServer(Netty, port = 8008, module = Application::adder).start(wait = true)
}
