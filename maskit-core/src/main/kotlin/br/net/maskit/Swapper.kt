package br.net.maskit

/**
 * Class to perform a Swap operation over fixed size numeric Ids.
 *
 * @author Douglas Siviotti
 * @since 1.0
 */
data class Swapper(val indexes: List<Int>, val size: Int = indexes.size) {
    init {
        indexes.forEach { c ->
            check(c in 0 until size) { "Invalid index: $c. Must be between 0 and ${size - 1}" }
        }
        check(indexes.size == size)
        { "Expected $size indexes. Found ${indexes.size}. Indexes: $indexes" }
        check(indexes.toSet().size == size)
        { "Repeated indexes: ${indexes.groupingBy { it }.eachCount().filter { it.value > 1 }}" }
    }

    fun swap(numericId: NumericId): NumericId {
        requireSize(numericId)
        return NumericId(indexes.map { numericId.digits[it] })
    }

    fun restore(swapped: NumericId): NumericId {
        requireSize(swapped)
        val temp = indexes.toMutableList()
        swapped.digits.forEachIndexed { index, digit -> temp[indexes[index]] = digit }
        return NumericId(temp)
    }

    fun blend(numericId: NumericId): Int {
        requireSize(numericId)
        var count = 0
        numericId.digits.forEachIndexed { index, digit -> count += (indexes[index] + 1) * digit }
        return count
    }

    fun shift(numericId: NumericId, rowCount: Int = DEFAULT_ROW_COUNT) = blend(numericId) % rowCount

    private fun requireSize(id: NumericId) {
        require(id.digits.size == size) { "NumericId must have size $size. Value=$id" }
    }

}