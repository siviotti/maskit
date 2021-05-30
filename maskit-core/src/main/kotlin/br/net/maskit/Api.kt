@file:JvmName("Api")

package br.net.maskit

fun numericMaskitOf(swapper: Swapper, maskTable: MaskTable) = NumericMaskit(swapper, maskTable)
fun numericMaskitOf(commaSeparatedIndexes: String, charTable: String) =
    numericMaskitOf(
        Swapper(commaSeparatedIndexes.split(",").map { it.toInt() }),
        MaskTable(charTable, DEFAULT_ROW_COUNT)
    )

@JvmOverloads
fun prefixMaskitOf(swapper: Swapper, maskTable: MaskTable, prefixType: PrefixType = PrefixType.DATE_TIME) =
    PrefixMaskit(swapper, maskTable, prefixType)

@JvmOverloads
fun prefixMaskitOf(commaSeparatedIndexes: String, charTable: String, prefixType: PrefixType = PrefixType.DATE_TIME) =
    prefixMaskitOf(
        Swapper(commaSeparatedIndexes.split(",").map { it.toInt() }),
        MaskTable(charTable, DEFAULT_ROW_COUNT),
        prefixType
    )

fun datedMaskitOf(swapper: Swapper, maskTable: MaskTable) = PrefixMaskit(swapper, maskTable, PrefixType.DATE)
fun datedMaskitOf(commaSeparatedIndexes: String, charTable: String) =
    prefixMaskitOf(commaSeparatedIndexes, charTable, PrefixType.DATE)

fun timedMaskitOf(swapper: Swapper, maskTable: MaskTable) = PrefixMaskit(swapper, maskTable, PrefixType.TIME)
fun timedMaskitOf(commaSeparatedIndexes: String, charTable: String) =
    prefixMaskitOf(commaSeparatedIndexes, charTable, PrefixType.TIME)

fun numericIdOf(value: Long) = NumericId(toDigits(value.toString()))
fun numericIdOf(text: String) = NumericId(toDigits(text))
fun maskedOf(text: String) = Masked(text)

/**
 * WARNING: USE FOR TEST ONLY!
 * This function returns a default Maskit instance based on default parameters:
 * <BR> numeric sequence of swapping: DEFAULT_SWAPPER_SEQ
 * <BR> char table for encode/decode: DEFAULT_MASK_TABLE_STR
 * This function should be used on tests and implementation checks.
 * For real scenarios you must use your own numeric sequence (Swapper) and a char table (MaskTable).
 *
 * @return The default instance os Maskit based on default test/check parameters
 * @see DEFAULT_SWAPPER_SEQ
 * @see DEFAULT_MASK_TABLE_STR
 */
@Deprecated("Use only for test", ReplaceWith("numericMaskitOf(seq, table)"))
fun defaultMaskit() = numericMaskitOf(DEFAULT_SWAPPER_SEQ, DEFAULT_MASK_TABLE_STR)