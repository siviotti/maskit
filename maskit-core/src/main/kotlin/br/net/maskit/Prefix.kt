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
open class PrefixMaskit(swapper: Swapper, table: DigitTable, val prefixType: PrefixType) : Maskit<NumericId> {

    val maskit = NumericMaskit(swapper, table)

    override fun mask(id: NumericId) = maskit.mask(createPrefixedId(id))

    override fun unmask(masked: Masked) = maskit.unmask(masked)

    fun extractId(masked: Masked) = maskit.unmask(masked).sub(prefixType.size)

    fun extractPrefix(masked: Masked) = maskit.unmask(masked).sub(0, prefixType.size)

    fun extractPrefixObj(masked: Masked) = prefixType.fromDigits(extractPrefix(masked).digits)

    override fun randomMask(id: NumericId) = maskit.randomMask(createPrefixedId(id))

    private fun createPrefixedId(id: NumericId): NumericId {
        val idSize = maskit.swapper.size - prefixType.size
        require(id.digits.size == idSize)
        {
            "The ID must have size = $idSize because tha mask perform over 'dateTime + Id'. " +
                    "Swapper size=${maskit.swapper.size}. Prefix size = $prefixType.size"
        }
        return NumericId(prefixType.toDigits(now())).concat(id)
    }

    protected open fun now(): LocalDateTime = LocalDateTime.now()
}

enum class PrefixType (
    val size: Int, val toDigits: (dt: LocalDateTime) -> List<Int>,
    val fromDigits: (digits: List<Int>) -> Any
) {
    DATE(8, { dateToDigits(it) }, { digitsToDate(it) }),
    TIME(6, { timeToDigits(it) }, { digitsToTime(it) }),
    DATE_TIME(14, { dateTimeToDigits(it) }, { digitsToDateTime(it) });
}