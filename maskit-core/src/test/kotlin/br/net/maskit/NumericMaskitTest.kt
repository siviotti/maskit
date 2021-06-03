package br.net.maskit

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import kotlin.random.Random

import kotlin.random.nextLong

private const val TEST_TABLE = "BCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxy1234567890"

internal class NumericMaskitTest {

    fun createMaskit() = NumericMaskit(
        Swapper(listOf(2, 10, 3, 8, 6, 9, 0, 7, 1, 5, 4)), DigitTable(
            DEFAULT_MASK_TABLE_STR
        )
    )

    @Test
    fun testMask() {
        val cpf = numericIdOf("07311631769")
        val seq = listOf(2, 10, 3, 8, 6, 9, 0, 7, 1, 5, 4)
        val testTable = "BCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxy1234567890"
        val maskit = NumericMaskit(Swapper(seq), DigitTable(testTable))
        val maskedId = maskit.mask(cpf)
        assertEquals(maskedOf("iKMwYvpgmHW"), maskedId)
    }

    @Test
    fun testUnmask() {
        val seq = listOf(2, 10, 3, 8, 6, 9, 0, 7, 1, 5, 4)
        val testTable = "BCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxy1234567890"
        val maskit = NumericMaskit(Swapper(seq), DigitTable(testTable))
        val maskedId = maskedOf("iKMwYvpgmHW")
        val unmaskedId = maskit.unmask(maskedId)
        assertEquals(numericIdOf("07311631769"), unmaskedId)
    }

    @Test
    fun testMaskAndUnmask() {
        val cpf = numericIdOf("07311631769")
        val seq = listOf(2, 10, 3, 8, 6, 9, 0, 7, 1, 5, 4)
        val testTable = "BCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxy1234567890"
        val maskit = NumericMaskit(Swapper(seq), DigitTable(testTable))
        val maskedId = maskit.mask(cpf)
        val unmaskedId = maskit.unmask(maskedId)
        assertEquals(cpf, unmaskedId)
    }

    @Test
    fun testRandomMask() {
        val cpf = numericIdOf("07311631769")
        val maskit = createMaskit()
        val maskedId = maskit.randomMask(cpf)
        val unmaskedId = maskit.unmask(maskedId)
        assertEquals(cpf, unmaskedId)
    }

    @Test
    fun testRandomDuplicate() {
        val cpf = numericIdOf("07311631769")
        val maskit = createMaskit()
        var masked: Masked
        val map = mutableMapOf<Masked, Int>()
        var tmp: Int
        val max = 1000000
        for (i in 1..max) {
            masked = maskit.randomMask(cpf)
            assertEquals(cpf, maskit.unmask(masked))
            tmp = 0
            if (map.containsKey(masked)) {
                tmp = map[masked]!!
            }
            map[masked] = tmp + 1
        }
        //val dupMap = map.filter { it.value > 1 }.toMap()
        //dupMap.forEach { k, v -> println("randomMasked:$k occurs:$v") }
        //println("Generated randomMasks: ${map.keys.size} / $max")
        //println("Duplicates: ${max - map.keys.size}")
    }

    @Test
    fun testMass() {
        val seq = Swapper(listOf(2, 10, 3, 8, 6, 9, 0, 7, 1, 5, 4))
        val table = DigitTable(TEST_TABLE)
        val kit = NumericMaskit(seq, table)
        val max: Long = 1000000
        var num :Long
        val random = Random
        var id: NumericId
        var masked: Masked
        var decodedId: NumericId
        val mapDist = mutableMapOf<Char, Int>()
        val mapOccur = mutableMapOf<Int, MutableMap<Char, Int>>()
        val mapUnique = mutableMapOf<Long, String>()
        for (i in 1L..max) {
            num = random.nextLong(10000000001L..99999999999L)
            id = numericIdOf(num)
            masked = kit.mask(id)
            if (mapUnique.containsKey(num)) {
                //println("Duplicate num = $num. Old PseudiId = ${mapUnique[num]} new PseudoId = $maskedId")
                assertEquals(mapUnique[num], masked.text)
            }
            mapUnique[num] = masked.text
            decodedId = kit.unmask(masked)
            dist(masked, mapDist)
            //println(" id=$id encoded=$pseudoId seed=${kit.seq.seed(id)}")
            assertEquals(id, decodedId)
            occur(id, masked, mapOccur)
        }
        //mapDist.toSortedMap().forEach { k, v -> println("$k = $v") }
        //mapOccur.toSortedMap().forEach { k, v -> println("digit $k occur: ${v.toSortedMap()}") }
    }

    private fun dist(masked: Masked, map: MutableMap<Char, Int>) {
        var count : Int
        masked.text.forEach {
            if (!map.containsKey(it)) {
                map.put(it, 0)
            }
            count = map[it]!! + 1
            map.put(it, count)
        }
    }

    private fun occur(id: NumericId, masked: Masked, map: MutableMap<Int, MutableMap<Char, Int>>) {
        var count : Int
        var pseudoDigit: Char
        var subMap: MutableMap<Char, Int>
        id.digits.forEachIndexed { index, digit ->
            pseudoDigit = masked.text[index]
            if (!map.containsKey(digit)) {
                map[digit] = mutableMapOf<Char, Int>()
            }
            subMap = map[digit]!!

            if (!subMap.containsKey(pseudoDigit)) {
                subMap[pseudoDigit] = 0
            }
            count = subMap[pseudoDigit]!!
            subMap[pseudoDigit] = count + 1
        }
    }
}


