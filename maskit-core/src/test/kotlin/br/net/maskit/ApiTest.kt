package br.net.maskit

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class ApiTest {

    @Test
    fun testRandomIndexes() {
        val randomIndexes = randomIndexes(11);
        assertEquals(11, randomIndexes.size)
        assertEquals(11, randomIndexes.toSet().size) // no duplicate
    }

    @Test
    fun testRandomIndexesMass() {
        val mass = mutableSetOf<List<Int>>()
        val max = 10000
        for (i in 0 until max) {
            mass.add(randomIndexes(11))
            assertEquals(11, mass.last().toSet().size) // no duplicate
        }
        val duplicates = max - mass.size
        println("Generated lists from $max: ${mass.size}, duplicates: $duplicates")
        assertTrue(duplicates < 10)
    }

    @Test
    fun testRandomChars() {
        val chars = randomChars()
        println(chars)
        assertEquals(60, chars.length)
        assertEquals(60, chars.map { it }.toSet().size) // no duplicate
    }

    @Test
    fun testIndexesToString(){
        val strIndexes = indexesToString(listOf(1,15,8,33,4,2,9))
        assertEquals("1,15,8,33,4,2,9", strIndexes)
    }
}