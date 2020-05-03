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

    fun calculateGradeDrops(gradeType: String): Double {
        when (gradeType) {
            "Lab" -> {
                return labs.reduce { result, value ->
                    result + value
                } / labs.size
            }
            "Lecture" -> {
                return lectures.reduce { result, value ->
                    result + value
                } / lectures.size
            }
            "MP" -> {
                return mps.reduce { result, value ->
                    result + value
                } / mps.size
            }
            "Homework" -> {
                return homeworks.reduce { result, value ->
                    result + value
                } / homeworks.size
            }
            "Exam" -> {
                return exams.reduce { result, value ->
                    result + value
                } / exams.size
            }
            "Quiz" -> {
                return quizzes.reduce { result, value ->
                    result + value
                } / quizzes.size
            }
            "Final Project" -> {
                return finalProject
            }
        }

        return 0.0
    }

    fun calculateGradeNoDrops(gradeType: String): Double {
        when (gradeType) {
            "Lab" -> {

            }
            "Lecture" -> {

            }
            "MP" -> {

            }
            "Homework" -> {

            }
            "Exam" -> {

            }
            "Quiz" -> {

            }
            "Final Project" -> {
                return finalProject
            }
        }

        return 0.0
    }


}