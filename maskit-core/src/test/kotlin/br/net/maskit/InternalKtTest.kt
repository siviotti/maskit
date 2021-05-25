package br.net.maskit

import br.net.maskit.toDigits
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

internal class InternalKtTest{

    @Test
    fun testToDigits(){
        val digits = listOf(1,2,3,4,5)
        assertEquals(digits, toDigits("12345"))
        assertEquals(listOf<Int>(), toDigits(""))
    }

    @Test
    fun requireValidChar() {
    }

    @Test
    fun leftZero() {
        assertEquals("01", leftZero(1))
        assertEquals("05", leftZero(5))
        assertEquals("09", leftZero(9))
        assertEquals("-9", leftZero(-9))
        assertEquals("10", leftZero(10))
        assertEquals("51", leftZero(51))
        assertEquals("132", leftZero(132))
    }

    @Test
    fun testDateTimeToDigits() {
        val dt = LocalDateTime.of(2021, 5, 25, 16, 56, 3 )
        val digits = toDigits("20210525165603")
        assertEquals(digits, dateTimeToDigits(dt))
    }
}