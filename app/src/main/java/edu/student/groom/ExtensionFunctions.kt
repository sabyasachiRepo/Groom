package edu.student.groom

fun String.letters() = filter { it.isLetter() }
val Any.TAG: String
    get() {
        val tag = javaClass.simpleName
        return if (tag.length <= 23) tag else tag.substring(0, 23)
    }