package br.net.maskit

fun numericIdOf(value: Long) = NumericId(toDigits(value.toString()))
fun numericIdOf(text: String) = NumericId(toDigits(text))
fun maskedIdOf(text: String) = Masked(text)
fun toDigits(text: String) = text.map { c -> Character.getNumericValue(c) }
fun limitDigit(numericId: NumericId, swapper: Swapper, maxDigit: Int): List<Int> {
    require(numericId.digits.size == swapper.size)
    return numericId.digits.mapIndexed { index, digit -> (digit * swapper.indexes[index]) % maxDigit + 1 }
}

fun numericMaskitOf(swapper: Swapper, maskTable: MaskTable) = NumericMaskit(swapper, maskTable)
fun numericMaskitOf(sequence: String, chars: String) =
    NumericMaskit(Swapper(toDigits(sequence)), MaskTable(chars, DEFAULT_ROW_COUNT))