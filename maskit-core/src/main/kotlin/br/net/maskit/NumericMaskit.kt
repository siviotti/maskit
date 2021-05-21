package br.net.maskit

import kotlin.random.Random

/**
 * "Mask Kit" or "Mask It".
 * Façade class to use Pseudonimization of Numerid Ids.
 *
 * The Kit contains a Seq (sequence) and a MaskTable.
 *
 * @author Douglas Siviotti
 * @since 1.0
 * @see Swapper
 * @see MaskTable
 */
class NumericMaskit(val swapper: Swapper, val table: MaskTable) : Maskit<NumericId> {

    override
    fun mask(numericId: NumericId): Masked {
        val shift = swapper.blend(numericId) % table.rowCount
        val shuffled = swapper.swap(numericId)
        val list = shuffled.digits.mapIndexed { index, digit ->
            val row = (swapper.indexes[index] + numericId.digits[index] + shift) % table.rowCount
            table.encode(row, digit)
        }
        return Masked(listToString(list))
    }

    override
    fun unmask(masked: Masked): NumericId {
        return swapper.restore(NumericId(masked.text.map { table.decode(it) }))
    }

    override
    fun randomMask(numericId: NumericId): Masked{
        val swapped = swapper.swap(numericId)
        val list = swapped.digits.map { digit ->
            val row = Random.nextInt(0 , table.rowCount)
            table.encode(row, digit)
        }
        return Masked(listToString(list))
    }
}