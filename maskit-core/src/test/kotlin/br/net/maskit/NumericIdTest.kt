package br.net.maskit

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class NumericIdTest {

    @Test
    fun testCreate(){
        val numericId = NumericId(listOf(2,5,8,2,3,4))
        assertEquals(listOf(2,5,8,2,3,4), numericId.digits)

    }

    @Test
    fun testSub(){
        val numericId = NumericId(listOf(2,5,8,2,3,4))
        // sub (fromIndex [included], toIndex [excluded])
        // default = 0 until size
        assertEquals(listOf(2,5,8,2,3,4), numericId.sub().digits) // 0 until 6 (excluded)
        assertEquals(listOf(2,5,8,2,3,4), numericId.sub(0).digits) // 0 until 6 (excluded)
        assertEquals(listOf(2,5,8,2,3,4), numericId.sub(0,6).digits) // 6 = size
        assertEquals(listOf(  5,8,2,3,4), numericId.sub(1).digits)
        assertEquals(listOf(  5,8,2,3  ), numericId.sub(1,5).digits)
        assertEquals(listOf(    8,2    ), numericId.sub(2,4).digits) // indexes 2 and 3
    }

    @Test
    fun testConcat(){
        val nid1 = NumericId(listOf(2,5,8,2,3,4))
        val nid2 = NumericId(listOf(9,1,7))
        val nid3 = nid1.concat(nid2)
        assertEquals(listOf(2,5,8,2,3,4,9,1,7), nid3.digits)
        assertEquals(listOf(2,5,8,2,3,4,9,1,7,2,5,8,2,3,4,9,1,7), nid3.concat(nid3).digits)
    }

}