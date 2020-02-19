import io.ktor.application.call
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
            get("/test") {
                call.respondText("Testing")
            }
        }
    }.start(wait = true)
}