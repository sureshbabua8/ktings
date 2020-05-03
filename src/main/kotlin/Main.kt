@file:Suppress("SpellCheckingInspection")

package hello

import com.fasterxml.jackson.databind.SerializationFeature
import com.google.gson.Gson
import com.google.gson.JsonObject
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.ContentNegotiation
import io.ktor.gson.gson
import io.ktor.http.HttpStatusCode
import io.ktor.jackson.jackson
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.response.respondText
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import java.lang.Compiler.enable

data class Course(val students: MutableMap<String, Student>) {
    init {
        addStudent("as43")
        addStudent("hdeep2")
        addStudent("nikhilg4")
        addStudent("challen")
    }

    fun welcome(): String {
        return "Welcome to CS 125!"
    }

    private fun addStudent(netid: String): Unit {
        students[netid] = Student(netid)
    }
}

private var course = Course(mutableMapOf())

fun Application.analyzeCourse() {

    install(ContentNegotiation) {
        gson {
            setPrettyPrinting()
        }
    }

    routing {
        get("/") {
            call.respondText(course.welcome())
        }

        get("/addStudent/{netid}") {
            val student = call.parameters["netid"]!!
            course.students.putIfAbsent(student, Student(student))
            call.respondText("Added $student to CS 125!")
        }
        get("/{netid}/{component}") {
            val netid = call.parameters["netid"]!!
            val component = call.parameters["component"]!!
            if (course.students.containsKey(call.parameters["netid"])) {
                val gradeNoDrops = course.students[netid]!!.calculateGradeNoDrops(component)
                val gradeDrops = course.students[netid]!!.calculateGradeDrops(component)
                call.respondText("$netid's $component grade without drops is $gradeNoDrops \n$netid's $component " +
                        "grade with drops is $gradeDrops")
            }
        }

        post("/gradebook") {
            try {
                call.respond(call.receive<JsonObject>())
            } catch (e: Exception) {
                call.respond(HttpStatusCode.BadRequest)
            }
        }

        get("/gradebook") {
            call.respond(course)
        }

    }
}
fun main() {
//    println(Gson().toJson(course.students).toString())
    embeddedServer(Netty, port = 8000, module = Application::analyzeCourse).start(wait = true)
}
