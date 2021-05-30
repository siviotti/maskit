package br.net.maskit

import java.time.LocalDateTime

/**
 * Specialized Maskit that use a Prefix before the Id.
 *
 * @author Douglas Siviotti
 * @since 1.0
 * @see Maskit
 * @see NumericMaskit
 */
class PrefixMaskit(val swapper: Swapper, val table: MaskTable, val prefix: Prefix) : Maskit<NumericId> {

    private val maskit = NumericMaskit(swapper, table)

    override fun mask(id: NumericId): Masked {
        return maskit.mask(createDatedId(id))
    }

    override fun unmask(masked: Masked): NumericId {
        return maskit.unmask(masked)
    }

    fun extractId(masked: Masked): NumericId {
        return maskit.unmask(masked).sub(prefix.size)
    }

    override fun randomMask(id: NumericId): Masked {
        return maskit.randomMask(createDatedId(id))
    }

    private fun createDatedId(id: NumericId): NumericId {
        val idSize = swapper.indexes.size - prefix.size
        require(id.digits.size == idSize)
        {
            "The ID must have size = $idSize because tha mask perform over 'dateTime + Id'. " +
                    "Swapper size=${swapper.size}. DateTimeSize = $prefix.size"
        }
        val dt = LocalDateTime.now()
        return NumericId(prefix.toDigits(dt));
    }
}

enum class Prefix(val size: Int, val toDigits: (dt: LocalDateTime) -> List<Int>) {
    DATE(8, { dateToDigits(it) }),
    TIME(6, { timeToDigits(it) }),
    DATE_TIME(14, { dateTimeToDigits(it) });
}