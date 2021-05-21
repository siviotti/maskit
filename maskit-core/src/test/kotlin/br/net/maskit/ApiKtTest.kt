package br.net.maskit

import br.net.maskit.toDigits
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class ApiKtTest{

    @Test
    fun testToDigits(){
        val digits = listOf(1,2,3,4,5)
        assertEquals(digits, toDigits("12345"))
        assertEquals(listOf<Int>(), toDigits(""))
    }
}