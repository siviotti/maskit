package br.net.maskit.lgpd

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class ModelTest {

    @Test
    fun testTratamento(){
        val tratamento = Tratamento("001")
        assertEquals("001", tratamento.id)
    }
}