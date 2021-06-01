package br.net.maskit

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.lang.IllegalArgumentException

internal class SwapperTest {

    @Test
    fun testCreateValidSeq() {
        val seq = Swapper(listOf(1, 3, 2, 0, 4)) // Valid Seq
        assertTrue(seq.size == 5)
        assertEquals(1, seq.indexes[0])
        assertEquals(3, seq.indexes[1])
        assertEquals(2, seq.indexes[2])
        assertEquals(0, seq.indexes[3])
        assertEquals(4, seq.indexes[4])
    }

    @Test
    fun testCreateInvalidSeqElementOutOfBound() {
        //  5 is invalid, size = 5
        assertThrows(IllegalStateException::class.java) { Swapper(listOf(1, 3, 2, 5, 4)) }
        //  -2 is invalid, size = 5
        assertThrows(IllegalStateException::class.java) { Swapper(listOf(1, 3, -2, 0, 4)) }
    }

    @Test
    fun testCreateInvalidSeqElementDuplicated() {
        //  3 and 4 are duplicated
        assertThrows(IllegalStateException::class.java) { Swapper(listOf(1, 3, 2, 3, 4)) }
    }

    @Test
    fun testCreateInvalidSeqElementFixedSize() {
        val size = 4
        //  valid elements, but invalid size of elements
        assertThrows(IllegalStateException::class.java) { Swapper(listOf(1, 3, 2, 0, 4), size) }
        val validSeq4Elements = Swapper(listOf(1, 3, 2, 0), size)
        assertEquals(4, validSeq4Elements.size)
    }

    @Test
    fun testSwap(){
        val original = numericIdOf("07311631769")
        val seq = Swapper (listOf(2,10,3,8,6,9,0,7,1,5,4))
        // 39173601761
        val swapped = seq.swap(original)
        assertEquals(numericIdOf("39173601761"), swapped)
    }

    @Test
    fun testRestore(){
        val original = numericIdOf("07311631769")
        val seq = Swapper (listOf(2,10,3,8,6,9,0,7,1,5,4))
        val shuffled = numericIdOf("39173601761")
        val restored = seq.restore(shuffled)
        assertEquals(original, restored)
    }

    @Test
    fun testBlend(){
        val seq = Swapper(listOf(2,0,1,4,3))
        val id = numericIdOf("12345")
        // numericId.digits.forEachIndexed { index, digit -> count += (digits[index] + 1) * digit }
        // (1 x (2+1)) + (2 * (0+1)) + (3 x (1+1)) + (4 x (4+1)) + (5 x (3+1))
        //     3       +     2       +     6       +     20      +     20 = 51
        assertEquals(51, seq.blend(id))
        val id2 = numericIdOf("52731")
        // (5 x (2+1)) + (2 * (0+1)) + (7 x (1+1)) + (3 x (4+1)) + (1 x (3+1))
        //     15      +     2       +     14      +     15      +     4 = 50
        assertEquals(50, seq.blend(id2))
    }

    @Test
    fun tesRequireSize(){
        val seq = Swapper (listOf(2,10,3,8,6,9,0,7,1,5,4))
        val little = numericIdOf("123")
        val big = numericIdOf("1234567890123456789")
        assertThrows(IllegalArgumentException::class.java) { seq.swap(little) }
        assertThrows(IllegalArgumentException::class.java) { seq.swap(big) }
        assertThrows(IllegalArgumentException::class.java) { seq.restore(little) }
        assertThrows(IllegalArgumentException::class.java) { seq.restore(big) }
        assertThrows(IllegalArgumentException::class.java) { seq.blend(little) }
        assertThrows(IllegalArgumentException::class.java) { seq.blend(big) }
    }

    @Test
    fun testShift(){
        val seq = Swapper(listOf(2,0,1,4,3))
        val id = numericIdOf("12345")
        // numericId.digits.forEachIndexed { index, digit -> count += (digits[index] + 1) * digit }
        // (1 x (2+1)) + (2 * (0+1)) + (3 x (1+1)) + (4 x (4+1)) + (5 x (3+1))
        //     3       +     2       +     6       +     20      +     20 = 51
        // 51 % 6 = 3
        assertEquals(3, seq.shift(id))
        val id2 = numericIdOf("52731")
        // (5 x (2+1)) + (2 * (0+1)) + (7 x (1+1)) + (3 x (4+1)) + (1 x (3+1))
        //     15      +     2       +     14      +     15      +     4 = 50
        // 50 % 6 = 2
        assertEquals(2, seq.shift(id2))
    }

    @Test
    fun testShiftDistribution(){
        val seq = Swapper(listOf(0,1,2,3,4,5,6,7,8,9,10)) // CPF
        val map = mutableMapOf<Int, Int>()
        var shift = 0
        var count: Int = 0
        val begin = 10000000001L
        val end = 10001000000L
        for (i in begin .. end){
            shift = seq.shift(numericIdOf(i))
            //println ("i=$i jump = $jump")
            count = if (map.containsKey(shift)) map[shift]!! else 0
            map.put(shift, count+1)
        }
        //map.toSortedMap().forEach { t, u -> println("shift=$t, count=$u") }
        val avg = (end-begin) / DEFAULT_ROW_COUNT
        //println("avg long ($long) = $avg")
        assertTrue(map[0]!!.toLong() > avg-100 )
        assertTrue(map[0]!!.toLong() < avg+100 )
    }

}