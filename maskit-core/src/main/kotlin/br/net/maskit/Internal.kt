package br.net.maskit

// Internal Constants

const val VALID_CHAR = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890"
const val ROW_SIZE = 10
const val DEFAULT_ROW_COUNT = 6

/** ONLY TEST USE!!! Example of Swapper numeric sequence. */
const val DEFAULT_SWAPPER_SEQ = "7,9,1,5,0,10,6,2,3,8,4"

/** ONLY TEST USE!!! Example of MaskTable content. */
const val DEFAULT_MASK_TABLE_STR =
    "GoQd7ShKu2" +
            "k9wONaC4Hr" +
            "3mExBU0Wgf" +
            "Fn5yP1pIvD" +
            "LsM8iAtJe6" +
            "RbVlXjYqTc"

// Internal Functions

fun toDigits(text: String) = text.map { c -> Character.getNumericValue(c) }
fun requireValidChar(c: Char) = require(VALID_CHAR.contains(c)) { "Invalid char: $c" }

fun listToString(list: List<*>): String {
    val sb = StringBuilder()
    list.forEach { c -> sb.append(c) }
    return sb.toString()
}
