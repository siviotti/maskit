package br.net.maskit

// Internal elements: functions, constants etc

const val VALID_CHAR = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890"
const val ROW_SIZE = 10
const val DEFAULT_ROW_COUNT = 6
fun requireValidChar(c: Char) = require(VALID_CHAR.contains(c)) { "Invalid char: $c" }

fun listToString(list: List<*>): String {
    val sb = StringBuilder()
    list.forEach { c -> sb.append(c) }
    return sb.toString()
}
