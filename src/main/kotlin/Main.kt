@file:Suppress("SpellCheckingInspection")

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

    fun addStudent(student: Student): Unit {
        students[student.netid] = student
    }

    fun getStudentComps(type: String): Pair<Sequence<Double>, Sequence<Double>> {
        when (type) {
            "overview" -> {
                return Pair(students.values.map { it.getOverallGradeNoDrops()!! }.asSequence(),
                    students.values.map { it.getOverallGradeDrops()!! }.asSequence())
            }
            else -> {
                return Pair(students.values.map { it.calculateGradeNoDrops(type)!! }.asSequence(),
                    students.values.map { it.calculateGradeDrops(type)!! }.asSequence())
            }
        }
    }

}

private var course = Course(mutableMapOf())

fun Application.viewCourse() {
    var compTypes: MutableList<String> = compNames.values().map { it.type } as MutableList<String>
    compTypes.add("overview")
    install(ContentNegotiation) {
        gson {
            setPrettyPrinting()
        }
    }

    routing {
        get("/") {
            call.respondText(course.welcome())
        }

        get("/{value}") {
            when (val id = call.parameters["value"]) {
                "course" -> {
                    call.respond(course)
                }
                else -> {
                    if (course.students.containsKey(id)) {
                        call.respond(course.students[id]!!)
                    } else {
                        call.respondText("Invalid call!")
                    }
                }
            }

        }

        post("/addStudent/{netid}") {
            try {
                val request = call.receive<Student>()
                val id = request.netid
                course.addStudent(request)
                call.respondText("Successfully added $id to the course!")
            } catch (e: Exception) {
                call.respond(HttpStatusCode.BadRequest)
            }
        }

        get("/{value}/{component}") {
            val comp = call.parameters["component"]!!
            if (compTypes.contains(comp)) {
                when (val id = call.parameters["value"]) {
                    "course" -> {
                        val dropAvg = course.getStudentComps(comp).first.average()
                        val noDropAvg = course.getStudentComps(comp).second.average()

                        call.respondText("Course average $comp without drops: $noDropAvg \nCourse average $comp with drops: $dropAvg")
                    }
                    else -> {
                        val component = call.parameters["component"]!!
                        if (course.students.containsKey(id)) {
                            val gradeNoDrops = course.students[id]!!.calculateGradeNoDrops(component)
                            val gradeDrops = course.students[id]!!.calculateGradeDrops(component)
                            call.respondText("$id's $component grade without drops is $gradeNoDrops \n$id's $component " +
                                    "grade with drops is $gradeDrops")
                        } else {
                            call.respond(HttpStatusCode.BadRequest)
                        }
                    }
                }
            } else {
                call.respond(HttpStatusCode.BadRequest)
            }
        }

    }
}
fun main() {
    embeddedServer(Netty, port = 8000, module = Application::viewCourse).start(wait = true)
}
