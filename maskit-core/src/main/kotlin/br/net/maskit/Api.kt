@file:JvmName("Api")

package br.net.maskit

import java.lang.StringBuilder
import kotlin.random.Random

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

@JvmOverloads
fun randomMaskit(idSize: Int, chars: String = DEFAULT_MASK_TABLE_STR, rowCount: Int = 6) =
    numericMaskitOf(randomSwapper(idSize), randomMaskTable(chars, rowCount));

@JvmOverloads
fun randomPrefixMaskit(
    idSize: Int,
    chars: String = DEFAULT_MASK_TABLE_STR,
    rowCount: Int = 6,
    prefixType: PrefixType = PrefixType.DATE_TIME
) = prefixMaskitOf(randomSwapper(idSize), randomMaskTable(chars, rowCount), prefixType);

fun randomSwapper(size: Int) = Swapper(randomIndexes(size))

@JvmOverloads
fun randomMaskTable(chars: String = DEFAULT_MASK_TABLE_STR, rowCount: Int = DEFAULT_ROW_COUNT) =
    MaskTable(chars, rowCount)

fun randomIndexes(size: Int): List<Int> {
    val list = (0 until size).map { it }.toMutableList()
    val temp = mutableSetOf<Int>()
    var index: Int
    for (i in 0 until size) {
        index = Random.nextInt(list.size)
        temp.add(list[index])
        list.removeAt(index)
    }
    return temp.toList()
}

fun indexesToString(indexes: List<Int>) : String {
    val sb = StringBuilder()
    indexes.forEach { sb.append(it).append(',') }
    sb.deleteAt(sb.lastIndex)
    return sb.toString()
}

@JvmOverloads
fun randomChars(chars: String = VALID_CHAR, rowCount: Int = DEFAULT_ROW_COUNT): String {
    val size = rowCount * 10
    val list: MutableList<Char> = chars.map { it }.toMutableList()
    val temp = mutableSetOf<Char>()
    var index: Int
    for (i in 0 until size) {
        index = Random.nextInt(list.size)
        temp.add(list[index])
        list.removeAt(index)
    }
    return listToString(temp.toList())
}

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