@file:Suppress("SpellCheckingInspection")

package hello

import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.ContentNegotiation
import io.ktor.gson.gson
import io.ktor.response.respondText
import io.ktor.routing.get
import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty

fun initCourse(): String {
    return "Welcome to CS 125!"
}

fun Application.analyzeCourse() {
    val students: MutableMap<String, Student> = mutableMapOf()
    install(ContentNegotiation) {
        gson { }
    }
    routing {
        get("/") {
            call.respondText(initCourse())
        }

        get("/addStudent/{netid}") {
            val student = call.parameters["netid"]!!
            students.putIfAbsent(student, Student(student))
            call.respondText("Added $student to CS 125!")
        }
        get("/{netid}/{component}") {
            val netid = call.parameters["netid"]!!
            val component = call.parameters["component"]!!
            if (students.containsKey(call.parameters["netid"])) {
                val gradeNoDrops = students[netid]!!.calculateGradeNoDrops(component)
                val gradeDrops = students[netid]!!.calculateGradeDrops(component)
                call.respondText("$netid's $component grade without drops is $gradeNoDrops \n$netid's $component " +
                        "grade with drops is $gradeDrops")
            }
        }


    }
}
fun main() {
    embeddedServer(Netty, port = 8000, module = Application::analyzeCourse).start(wait = true)
}
