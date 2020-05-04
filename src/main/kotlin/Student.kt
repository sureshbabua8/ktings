package hello

enum class compNames(val type: String, val minDropped: Int, val weight: Double) {
    Lab("Lab", 3, 0.05), Homework("Homework", 12, 0.20), MP("MP", 1, 0.30),
    final_Project("Final Project", 0, 0.10), Quiz("Quiz", 3, 0.24),
    Exam("Exam", 0, 0.06), Lecture("Lecture", 6, 0.05)
}

data class Component(val type: compNames, var grade: Double) {
    private var assignments = mutableListOf<Double>()

    fun getAssignments(): MutableList<Double> {
        return assignments
    }

    fun setAssignment(list: MutableList<Double>): Unit {
        this.assignments = list
    }

    fun addAssignment(assignment: Double): Unit {
        assignments.add(assignment)
    }

    fun gradeWithDrops(): Double {
        return if (assignments.size > type.minDropped) {
            assignments.sort()
            val drops = assignments.subList(type.minDropped, assignments.size) // excludes dropped assignments

            drops.reduce { result, value ->
                result + value
            } / drops.size
        } else {
            gradeWOutDrops()
        }
    }

    fun gradeWOutDrops(): Double {
        return if (assignments.isNotEmpty()) {
            assignments.reduce { result, value ->
                result + value
            } / assignments.size
        } else {
            100.0
        }
    }
}

class Student(val netid: String) {

    private var grades = mutableMapOf<String, Component>()
    private var gradeWithDrops: Double
    private var gradeNoDrops: Double

    init {
        for (comp in compNames.values()) {
            grades[comp.type] = Component(comp, 100.0)
        }

        gradeWithDrops = 100.0
        gradeNoDrops = 100.0
    }

    // getters

    fun getLabs(): MutableList<Double> {
        return grades["Lab"]!!.getAssignments()
    }

    fun getHomeworks(): MutableList<Double> {
        return grades["Homework"]!!.getAssignments()
    }

    fun getMps(): MutableList<Double> {
        return grades["MP"]!!.getAssignments()
    }

    fun getFinalProject(): MutableList<Double> {
        return grades["Final Project"]!!.getAssignments()
    }

    fun getQuizzes(): MutableList<Double> {
        return grades["Quiz"]!!.getAssignments()
    }

    fun getExams(): MutableList<Double> {
        return grades["Exam"]!!.getAssignments()
    }

    fun getLectures(): MutableList<Double> {
        return grades["Lecture"]!!.getAssignments()
    }

    // setters
    fun setLabs(setList: MutableList<Double>): Unit {
        grades["Lab"]!!.setAssignment(setList)
    }

    fun setHomeworks(setList: MutableList<Double>): Unit {
        grades["Homework"]!!.setAssignment(setList)
    }

    fun setMps(setList: MutableList<Double>): Unit {
        grades["MP"]!!.setAssignment(setList)
    }

    fun setQuizzes(setList: MutableList<Double>): Unit {
        grades["Quiz"]!!.setAssignment(setList)
    }

    fun setExams(setList: MutableList<Double>): Unit {
        grades["Exam"]!!.setAssignment(setList)
    }

    fun setLectures(setList: MutableList<Double>): Unit {
        grades["Lecture"]!!.setAssignment(setList)
    }

    fun setFinalProject(setProject: Double): Unit {
        grades["Lab"]!!.setAssignment(mutableListOf(setProject))
    }

    // logistics

    fun addGrade(assignment: Double, assnType: String): Unit {
        if (grades.containsKey(assnType)) {
            grades[assnType]!!.addAssignment(assignment)
        }
    }

    fun calculateGradeDrops(gradeType: String): Double? {
        return if (grades.containsKey(gradeType)) {
            grades[gradeType]!!.gradeWithDrops()
        } else {
            0.0
        }
    }

    fun calculateGradeNoDrops(gradeType: String): Double? {
        return if (grades.containsKey(gradeType)) {
            grades[gradeType]!!.gradeWOutDrops()
        } else {
            0.0
        }
    }

    fun getOverallGradeDrops(): Double? {
        // update components first
        for (comp in grades.values) {
            comp.gradeWithDrops()
        }

        var grade = 0.0

        for (comp in grades.values) {
            grade += comp.gradeWithDrops()*comp.type.weight
        }

        return grade
    }

    fun getOverallGradeNoDrops(): Double? {
        // update components first
        for (comp in grades.values) {
            comp.gradeWOutDrops()
        }

        var grade = 0.0

        for (comp in grades.values) {
            grade += comp.gradeWOutDrops()*comp.type.weight
        }

        return grade
    }


}