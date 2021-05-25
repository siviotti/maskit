package br.net.maskit

import java.time.LocalDateTime

data class NumericId(val digits: List<Int>) {
    init {
        digits.forEach { c -> check(c in 0..9) { "Invalid value: $c" } }
    }
    override fun toString() = listToString(digits)
}

data class Masked(val text: String) {
    init {
        text.forEach { requireValidChar(it) }
    }
    override fun toString(): String = text
}

data class MomentNumericId(val moment: LocalDateTime, val numericId: NumericId){

}

