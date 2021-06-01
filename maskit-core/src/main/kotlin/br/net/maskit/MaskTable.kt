package br.net.maskit

import java.lang.StringBuilder

/**
 * Character repository for masking numeric digits.
 * @author Douglas Siviotti
 * @since 1.0
 */
class MaskTable @JvmOverloads constructor(text: String, val rowCount: Int = DEFAULT_ROW_COUNT) {
    private val rows: List<List<Char>>
    private val map: Map<Char, Int>

    init {
        val size = ROW_SIZE * rowCount
        check(text.length == size) { "Table must have size=$size and rows=$rowCount. Content:$text" }
        check(text.toSet().size == text.length) { "Duplicated element" }
        var temp = mutableListOf<Char>()
        var rowsTemp = mutableListOf<List<Char>>()
        val mapTemp = mutableMapOf<Char, Int>()
        text.forEachIndexed { index, char ->
            requireValidChar(char)
            temp.add(char)
            mapTemp[char] = index % 10
            if ((index + 1) % ROW_SIZE == 0) { //10, 20, 30, 40, 50, 60
                rowsTemp.add(temp.toList())
                temp = mutableListOf()
            }
        }
        rows = rowsTemp.toList()
        map = mapTemp.toMap()
    }

    fun encode(row: Int, digit: Int) = rows[row][digit]

    fun decode(char: Char): Int {
        require(map.contains(char)) { "Char '$char' is missing. See table:\n ${toString()}" }
        return map[char]!!
    }

    override fun equals(other: Any?): Boolean {
        if (other == null) return false
        if (other == this) return true
        if (other::class.java != this::class.java) return false
        return rows.equals((other as MaskTable).rows)
    }

    override fun hashCode(): Int = rows.hashCode()

    override fun toString(): String {
        val sb = StringBuilder()
        rows.forEach {
            it.forEach { char -> sb.append(char) }
            sb.append("\n")
        }
        return sb.toString()
    }
}