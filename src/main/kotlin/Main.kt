import io.ktor.application.call
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
fun main() {
    embeddedServer(Netty, 8008) {
        routing {
            get("/") {
                call.respondText(hello())
            }
            get("/add/{first}/{second}") {
                try {
                    val first = call.parameters["first"]!!.toInt()
                    val second = call.parameters["second"]!!.toInt()
                    call.respondText((first + second).toString())
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.BadRequest)
                }
            }
        }
    }.start(wait = true)
}