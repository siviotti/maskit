package br.net.maskit

fun numericIdOf(value: Long) = NumericId(toDigits(value.toString()))
fun numericIdOf(text: String) = NumericId(toDigits(text))
fun maskedIdOf(text: String) = Masked(text)
fun toDigits(text: String) = text.map { c -> Character.getNumericValue(c) }
fun numericMaskitOf(swapper: Swapper, maskTable: MaskTable) = NumericMaskit(swapper, maskTable)
fun numericMaskitOf(commaSeparatedChars: String, chars: String) =
    numericMaskitOf(
        Swapper(commaSeparatedChars.split(",").map { it.toInt() }),
        MaskTable(chars, DEFAULT_ROW_COUNT)
    )

/**
 * WARNING: USE FOR TEST ONLY!
 * This function returns a default Maskit instance based on default parameters:
 * <BR> numeric sequence of swapping: DEFAULT_SWAPPER_SEQ
 * <BR> char table for encode/decode: DEFAULT_MASK_TABLE_STR
 * This function should be used on tests and implementatiob checks.
 * For real scenarios you must use your own numeric sequence (Swapper) and a char table (MaskTable).
 *
 * @return The default instance os Maskit based on default test/check parameters
 * @see DEFAULT_SWAPPER_SEQ
 * @see DEFAULT_MASK_TABLE_STR
 */
@Deprecated("Use only for test", ReplaceWith("numericMaskitOf(seq, table)"))
fun defaultMaskit() = numericMaskitOf(DEFAULT_SWAPPER_SEQ, DEFAULT_MASK_TABLE_STR)