package br.net.maskit

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import kotlin.random.Random

import kotlin.random.nextLong

private const val TEST_TABLE = "BCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxy1234567890"

internal class NumericMaskitTest {

    fun createMaskit() = NumericMaskit(
        Swapper(listOf(2, 10, 3, 8, 6, 9, 0, 7, 1, 5, 4)), MaskTable(
            DEFAULT_MASK_TABLE_STR
        )
    )

    @Test
    fun testMask() {
        val cpf = numericIdOf("07311631769")
        val seq = listOf(2, 10, 3, 8, 6, 9, 0, 7, 1, 5, 4)
        val testTable = "BCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxy1234567890"
        val maskit = NumericMaskit(Swapper(seq), MaskTable(testTable))
        val maskedId = maskit.mask(cpf)
        assertEquals(maskedIdOf("iKMwYvpgmHW"), maskedId)
    }

    @Test
    fun testUnmask() {
        val seq = listOf(2, 10, 3, 8, 6, 9, 0, 7, 1, 5, 4)
        val testTable = "BCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxy1234567890"
        val maskit = NumericMaskit(Swapper(seq), MaskTable(testTable))
        val maskedId = maskedIdOf("iKMwYvpgmHW")
        val unmaskedId = maskit.unmask(maskedId)
        assertEquals(numericIdOf("07311631769"), unmaskedId)
    }

    @Test
    fun testMaskAndUnmask() {
        val cpf = numericIdOf("07311631769")
        val seq = listOf(2, 10, 3, 8, 6, 9, 0, 7, 1, 5, 4)
        val testTable = "BCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxy1234567890"
        val maskit = NumericMaskit(Swapper(seq), MaskTable(testTable))
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
        var tmp: Int = 0
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
        val dupMap = map.filter { it.value > 1 }.toMap()
        dupMap.forEach { k, v -> println("randomMasked:$k occurs:$v") }
        println("Generated randomMasks: ${map.keys.size} / $max")
        println("Duplicates: ${max - map.keys.size}")
    }

    @Test
    fun testPseudo() {
        val cpf = numericIdOf("07311631769")
        val seq = Swapper(listOf(2, 10, 3, 8, 6, 9, 0, 7, 1, 5, 4))
        val table = MaskTable(TEST_TABLE)
        println("0123456789")
        println(table)
        val kit = NumericMaskit(seq, table)
        val pseudoCpf = kit.mask(cpf)
        visual(numericIdOf("11111111111"), kit)
        visual(numericIdOf("11111211111"), kit)
        visual(numericIdOf("11111111112"), kit)
        visual(numericIdOf("21111211111"), kit)
        visual(numericIdOf("11111311111"), kit)
        visual(numericIdOf("22222222222"), kit)
        visual(numericIdOf("33333333333"), kit)
        visual(numericIdOf("44444444444"), kit)
        visual(numericIdOf("55555555555"), kit)
        visual(numericIdOf("66666666666"), kit)
        visual(numericIdOf("77777777777"), kit)
        visual(numericIdOf("88888888888"), kit)
        visual(numericIdOf("88888588888"), kit)
        visual(numericIdOf("88888885888"), kit)
        visual(numericIdOf("88888688888"), kit)
        visual(numericIdOf("88888788888"), kit)
        visual(numericIdOf("88888988888"), kit)
        visual(numericIdOf("99999999999"), kit)
        visual(numericIdOf("12345678901"), kit)
        visual(numericIdOf("12345678902"), kit)
        visual(numericIdOf("12345678903"), kit)
        visual(numericIdOf("12345678904"), kit)
        visual(numericIdOf("12345678905"), kit)
        visual(numericIdOf("12345678906"), kit)
    }

    fun visual(id: NumericId, kit: NumericMaskit) {
        val pseudo = kit.mask(id)
        val seed = kit.swapper.blend(id)
        println(" id=$id pseudo=$pseudo seed=$seed shift=${seed % DEFAULT_ROW_COUNT}")
    }

    @Test
    fun testMass() {
        val seq = Swapper(listOf(2, 10, 3, 8, 6, 9, 0, 7, 1, 5, 4))
        val table = MaskTable(TEST_TABLE)
        val kit = NumericMaskit(seq, table)
        println(table)
        val max: Long = 1000000
        var num = 0L
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
        var count = 0
        masked.text.forEach {
            if (!map.containsKey(it)) {
                map.put(it, 0)
            }
            count = map[it]!! + 1
            map.put(it, count)
        }
    }

    private fun occur(id: NumericId, masked: Masked, map: MutableMap<Int, MutableMap<Char, Int>>) {
        var count = 0
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


