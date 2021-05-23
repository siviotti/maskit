package br.net.maskit

import br.net.maskit.MaskTable
import org.junit.jupiter.api.Test

internal class MaskTableTest{

    @Test
    fun testCreate(){
        val pseudoTable = MaskTable(DEFAULT_MASK_TABLE_STR)
        println(pseudoTable)
    }
}