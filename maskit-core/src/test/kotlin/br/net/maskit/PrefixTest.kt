package br.net.maskit

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

val TEST_DATE_TIME = LocalDateTime.of(2001, 5, 30, 13, 46, 53)

internal class PrefixTest {

    fun createDateTimeMaskit() = FixedDateTimePrefixMaskit(
        Swapper(listOf(17, 2, 10, 22, 12, 3, 19, 8, 15, 6, 23, 9, 11, 16, 0, 24, 7, 14, 21, 1, 18, 5, 20, 4, 13)),
        DigitTable(DEFAULT_MASK_TABLE_STR)
    )

    @Test
    fun mask() {
        val maskit = createDateTimeMaskit()
        val cpf = numericIdOf("07311631769")
        val masked = maskit.mask(cpf)
        assertEquals(25, masked.text.length)
    }

    @Test
    fun unmask() {
    }

    @Test
    fun maskAndUnmaskRoundTrip() {
        val maskit = createDateTimeMaskit() // uses TEST_DATE_TIME, not Now()
        val cpf = numericIdOf("07311631769")
        val masked = maskit.mask(cpf)
        val unmasked = maskit.unmask(masked)
        val dateTimeStr = NumericId(dateTimeToDigits(TEST_DATE_TIME)).toString()

        assertEquals(25, unmasked.digits.size)
        assertTrue(unmasked.toString().contains("07311631769"))
        assertTrue(unmasked.toString().contains(dateTimeStr))

        val originalId = maskit.extractId(masked)
        val prefixId = maskit.extractPrefix(masked)
        val dt = maskit.prefixType.fromDigits(prefixId.digits)

        assertEquals(numericIdOf("07311631769"), originalId)
        assertEquals(TEST_DATE_TIME, dt)
        assertEquals(LocalDateTime::class.java, dt.javaClass)
    }

    @Test
    fun testRandom() {
        val maskit = createDateTimeMaskit() // uses TEST_DATE_TIME, not Now()
        val cpf = numericIdOf("07311631769")
        var randomMasked: Masked
        for (i in 1..1000) {
            randomMasked = maskit.randomMask(cpf)
            assertEquals(cpf, maskit.extractId(randomMasked))
            assertEquals(TEST_DATE_TIME, maskit.extractPrefixObj(randomMasked))

        }
    }

    @Test
    fun testDatefixMaskit(){
        val seq = "17,2,10,12,3,8,15,6,9,11,16,0,7,14,1,18,5,4,13" // size = 19
        val maskit = datedMaskitOf(seq, DEFAULT_MASK_TABLE_STR)
        val today = LocalDate.now()
        val masked = maskit.mask(numericIdOf("07311631769"))
        assertEquals(19, maskit.maskit.swapper.size)
        assertEquals(today, maskit.extractPrefixObj(masked))
        val maskit2=datedMaskitOf(maskit.maskit.swapper, maskit.maskit.table)
        assertEquals(masked, maskit2.mask(numericIdOf("07311631769")))
    }

    @Test
    fun testTimePrefixMaskit(){
        val before = LocalTime.now()
        Thread.sleep(1001)
        val seq = "2,10,12,3,8,15,6,9,11,16,0,7,14,1,5,4,13" // size = 17
        val maskit = timedMaskitOf(seq, DEFAULT_MASK_TABLE_STR)
        val masked = maskit.mask(numericIdOf("07311631769"))
        Thread.sleep(1001)
        val after = LocalTime.now()
        assertEquals(17, maskit.maskit.swapper.size)
        val time = maskit.extractPrefixObj(masked) as LocalTime
        assertTrue(before.isBefore(time) )
        assertTrue(after.isAfter(time) )
        val maskit2=timedMaskitOf(maskit.maskit.swapper, maskit.maskit.table)
        assertEquals(17, maskit2.maskit.swapper.size)
    }
}

class FixedDateTimePrefixMaskit(swapper: Swapper, table: DigitTable) :
    PrefixMaskit(swapper, table, PrefixType.DATE_TIME) {

    override fun now() = TEST_DATE_TIME

}