package hello

class Student(val netid: String, var grade: Double) {
    private var grades = mutableMapOf<String, Double>()
    private var labs = mutableListOf<Double>()
    private var homeworks = mutableListOf<Double>()
    private var mps = mutableListOf<Double>()
    private var finalProject: Double = 0.0
    private var quizzes = mutableListOf<Double>()
    private var exams = mutableListOf<Double>()
    private var lectures = mutableListOf<Double>()

    // getters

    fun getLabs(): MutableList<Double> {
        return labs
    }

    fun getHomeworks(): MutableList<Double> {
        return homeworks
    }

    fun getMps(): MutableList<Double> {
        return mps
    }

    fun getFinalProject(): Double {
        return finalProject
    }

    fun getQuizzes(): MutableList<Double> {
        return quizzes
    }

    fun getExams(): MutableList<Double> {
        return exams
    }

    fun getLectures(): MutableList<Double> {
        return lectures
    }

    // setters
    fun setLabs(setList: MutableList<Double>): Unit {
        this.labs = setList
    }

    fun setHomeworks(setList: MutableList<Double>): Unit {
        this.homeworks = setList
    }

    fun setMps(setList: MutableList<Double>): Unit {
        this.mps = setList
    }

    fun setQuizzes(setList: MutableList<Double>): Unit {
        this.quizzes = setList
    }

    fun setExams(setList: MutableList<Double>): Unit {
        this.exams = setList
    }

    fun setLectures(setList: MutableList<Double>): Unit {
        this.lectures = setList
    }

    fun setFinalProject(setProject: Double): Unit {
        this.finalProject = setProject
    }

    // logistics

    fun addGrade(assignment: Double, assnType: String): Unit {
        when (assnType) {
            "Lab" -> labs.add(assignment)
            "Lecture" -> lectures.add(assignment)
            "MP" -> mps.add(assignment)
            "Homework" -> homeworks.add(assignment)
            "Exam" -> exams.add(assignment)
            "Quiz" -> exams.add(assignment)
            // use setter to set final project grade
        }
    }

    fun calculateGradeDrops(gradeType: String): Double? {
        when (gradeType) {
            "Lab" -> {
                if (labs.size > 3) {
                    labs.sort()
                    val labDrops = labs.subList(3, labs.size) // 3 lowest labs dropped

                    grades["LabWithDrops"] = (labDrops.reduce { result, value ->
                        result + value
                    } / labDrops.size)*100.0

                    return grades["LabWithDrops"]
                } else {
                    calculateGradeNoDrops("Lab")
                }

            }
            "Lecture" -> {
                if (lectures.size > 6) {
                    lectures.sort()
                    val lectureDrops = lectures.subList(6, lectures.size)
                    grades["LectureWithDrops"] = (lectureDrops.reduce { result, value ->
                        result + value
                    } / lectureDrops.size)*100.0

                    return grades["LectureWithDrops"]
                } else {
                    calculateGradeNoDrops("Lecture")
                }

            }
            "MP" -> {
                if (mps.size > 1) {
                    mps.sort()
                    val mpDrops = mps.subList(1, mps.size)
                    grades["MPWithDrops"] = (mpDrops.reduce { result, value ->
                        result + value
                    } / mpDrops.size)*100.0
                    return grades["MPWithDrops"]
                } else {
                    calculateGradeNoDrops("MP")
                }

            }
            "Homework" -> {
                if (homeworks.size > 12) {
                    homeworks.sort()
                    val hwDrops = homeworks.subList(12, homeworks.size)
                    grades["HomeworkWithDrops"] = (hwDrops.reduce { result, value ->
                        result + value
                    } / hwDrops.size)*100.0
                    return grades["HomeworkWithDrops"]
                } else {
                    calculateGradeNoDrops("Homework")
                }

            }
            "Exam" -> {
                grades["Exam"] = (exams.reduce { result, value ->
                    result + value
                } / exams.size)*100.0
                return grades["Exam"]
            }
            "Quiz" -> {
                if (quizzes.size > 3) {
                    quizzes.sort()
                    val quizDrops = quizzes.subList(3, quizzes.size)
                    grades["QuizWithDrops"] = (quizDrops.reduce { result, value ->
                        result + value
                    } / quizDrops.size)*100.0
                    return grades["QuizWithDrops"]
                } else {
                    return calculateGradeDrops("Quiz")
                }

            }
            "Final Project" -> {
                grades["Final Project"] = finalProject
                return finalProject
            }
        }

        return 100.0
    }

    fun calculateGradeNoDrops(gradeType: String): Double? {
        when (gradeType) {
            "Lab" -> {
                if (labs.isNotEmpty()) {
                    grades["Lab"] = (labs.reduce { result, value ->
                        result + value
                    } / labs.size)*100.0

                    return grades["Lab"]
                }

            }
            "Lecture" -> {
                if (lectures.isNotEmpty()) {
                    grades["Lecture"] = (lectures.reduce { result, value ->
                        result + value
                    } / lectures.size)*100.0

                    return grades["Lecture"]
                }
            }
            "MP" -> {
                if (mps.isNotEmpty()) {
                    grades["MP"] = (mps.reduce { result, value ->
                        result + value
                    } / mps.size)*100.0
                    return grades["MP"]
                }

            }
            "Homework" -> {
                if (homeworks.isNotEmpty()) {
                    grades["Homework"] = (homeworks.reduce { result, value ->
                        result + value
                    } / homeworks.size)*100.0
                    return grades["Homework"]
                }
            }
            "Exam" -> {
                if (exams.isNotEmpty()) {
                    grades["Exam"] = (exams.reduce { result, value ->
                        result + value
                    } / exams.size)*100.0
                    return grades["Exam"]
                }
            }
            "Quiz" -> {
                if (quizzes.isNotEmpty()) {
                    grades["Quiz"] = (quizzes.reduce { result, value ->
                        result + value
                    } / quizzes.size)*100.0
                    return grades["Quiz"]
                }
            }
            "Final Project" -> {
                if (finalProject > 0.0) {
                    grades["Final Project"] = finalProject
                    return finalProject
                }
            }
        }

        return 100.0
    }

    fun getOverallGradeDrops(): Double? {
        var grade = 0.0
        // calculate components first
        // calculate lab grade
        // calculate lecture grade
        // calculate
    }

    fun getOverallGradeNoDrops(): Double? {
       return 0.0
    }


}