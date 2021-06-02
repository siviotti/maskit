package br.net.maskit

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

// Internal Constants

const val VALID_CHAR = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890"
const val ROW_SIZE = 10
const val DEFAULT_ROW_COUNT = 6

/** ONLY TEST USE!!! Example of Swapper numeric sequence (11 indexes). */
const val DEFAULT_SWAPPER_SEQ = "7,9,1,5,0,10,6,2,3,8,4"

/** ONLY TEST USE!!! Example of Swapper numeric sequence (25 indexes). */
const val DEFAULT_PREFIX_SWAPPER_SEQ =
    "17,2,10,22,12,3,19,8,15,6,23,9,11,16,0,24,7,14,21,1,18,5,20,4,13"

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
fun requireValidChar(text: String) = text.forEach { require(VALID_CHAR.contains(it)) { "Invalid char: $it" } }

fun listToString(list: List<*>): String {
    val sb = StringBuilder()
    list.forEach { c -> sb.append(c) }
    return sb.toString()
}

fun leftZero(value: Int) = if (value in 0..9) "0$value" else value.toString()

fun dateTimeToDigits(dateTime: LocalDateTime): List<Int> {
    val sb = StringBuilder()
    sb.append(dateTime.year)
    sb.append(leftZero(dateTime.monthValue))
    sb.append(leftZero(dateTime.dayOfMonth))
    sb.append(leftZero(dateTime.hour))
    sb.append(leftZero(dateTime.minute))
    sb.append(leftZero(dateTime.second))
    return toDigits(sb.toString())
}

fun dateToDigits(dateTime: LocalDateTime): List<Int> {
    val sb = StringBuilder()
    sb.append(dateTime.year)
    sb.append(leftZero(dateTime.monthValue))
    sb.append(leftZero(dateTime.dayOfMonth))
    return toDigits(sb.toString())
}

fun timeToDigits(dateTime: LocalDateTime): List<Int> {
    val sb = StringBuilder()
    sb.append(leftZero(dateTime.hour))
    sb.append(leftZero(dateTime.minute))
    sb.append(leftZero(dateTime.second))
    return toDigits(sb.toString())
}

fun digitsToDateTime(digits: List<Int>): LocalDateTime = LocalDateTime.of(
    Integer.valueOf("${digits[0]}${digits[1]}${digits[2]}${digits[3]}"),
    Integer.valueOf("${digits[4]}${digits[5]}"),
    Integer.valueOf("${digits[6]}${digits[7]}"),
    Integer.valueOf("${digits[8]}${digits[9]}"),
    Integer.valueOf("${digits[10]}${digits[11]}"),
    Integer.valueOf("${digits[12]}${digits[13]}")
)

fun digitsToDate(digits: List<Int>): LocalDate = LocalDate.of(
    Integer.valueOf("${digits[0]}${digits[1]}${digits[2]}${digits[3]}"),
    Integer.valueOf("${digits[4]}${digits[5]}"),
    Integer.valueOf("${digits[6]}${digits[7]}")
)

fun digitsToTime(digits: List<Int>): LocalTime = LocalTime.of(
    Integer.valueOf("${digits[0]}${digits[1]}"),
    Integer.valueOf("${digits[2]}${digits[3]}"),
    Integer.valueOf("${digits[4]}${digits[5]}")
)


