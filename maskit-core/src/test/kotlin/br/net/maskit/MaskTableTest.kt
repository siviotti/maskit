package br.net.maskit

import br.net.maskit.MaskTable
import org.junit.jupiter.api.Test

internal class MaskTableTest{

    @Test
    fun testCreate(){
        val pseudoTable = MaskTable("BCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxy1234567890")
        println(pseudoTable)
    }
}