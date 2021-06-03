package br.net.maskit

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

internal class DigitTableTest {

    @Test
    fun testCreate() {
        val table = DigitTable(DEFAULT_MASK_TABLE_STR) // 60 chars
        assertEquals(DEFAULT_ROW_COUNT, table.rowCount)
        assertEquals(DEFAULT_MASK_TABLE_STR, table.toPlainString())
    }

    @Test
    fun testCreateCustomSize() {
        val table = DigitTable("GoQd7ShKu2k9wONaC4Hr3mExBU0WgfFn5yP1pIvDLsM8iAtJe6", 5)
        assertEquals(5, table.rowCount)
        assertEquals("GoQd7ShKu2k9wONaC4Hr3mExBU0WgfFn5yP1pIvDLsM8iAtJe6", table.toPlainString())
    }

    @Test
    fun testCreateErrorTooLittle() { //scenario: 59 char
        val chars = "GoQd7ShKu2k9wONaC4Hr3mExBU0WgfFn5yP1pIvDLsM8iAtJe6RbVlXjYqT";
        Assertions.assertThrows(IllegalStateException::class.java) { DigitTable(chars, 6) }
    }

    @Test
    fun testCreateErrorTooBig() { // scenario: 61 char
        val chars = "GoQd7ShKu2k9wONaC4Hr3mExBU0WgfFn5yP1pIvDLsM8iAtJe6RbVlXjYqTAA";
        Assertions.assertThrows(IllegalStateException::class.java) { DigitTable(chars, 6) }
    }

    @Test
    fun testCreateErrorDuplicates() { // scenario:  aa duplicate
        val chars = "GoQd7ShKu2k9wONaa4Hr3mExBU0WgfFn5yP1pIvDLsM8iAtJe6RbVlXjYqTc";
        Assertions.assertThrows(IllegalStateException::class.java) { DigitTable(chars, 6) }
    }

    @Test
    fun testCreateErrorInvalidChar() { // scenario: % is invalid
        val chars = "GoQd7ShKu2k9wONa%4Hr3mExBU0WgfFn5yP1pIvDLsM8iAtJe6RbVlXjYqTc";
        Assertions.assertThrows(java.lang.IllegalStateException::class.java) { DigitTable(chars, 6) }
    }

    @Test
    fun testEncode() {
        val table = DigitTable("GoQd7ShKu2k9wONaC4Hr3mExBU0WgfFn5yP1pIvDLsM8iAtJe6RbVlXjYqTc")
        // rows, col = 0
        assertEquals('G', table.encode(0, 0))
        assertEquals('k', table.encode(1, 0))
        assertEquals('3', table.encode(2, 0))
        assertEquals('F', table.encode(3, 0))
        assertEquals('L', table.encode(4, 0))
        assertEquals('R', table.encode(5, 0))
        // cols, row = 0: GoQd7ShKu2
        assertEquals('G', table.encode(0, 0))
        assertEquals('o', table.encode(0, 1))
        assertEquals('Q', table.encode(0, 2))
        assertEquals('d', table.encode(0, 3))
        assertEquals('7', table.encode(0, 4))
        assertEquals('S', table.encode(0, 5))
        assertEquals('h', table.encode(0, 6))
        assertEquals('K', table.encode(0, 7))
        assertEquals('u', table.encode(0, 8))
        assertEquals('2', table.encode(0, 9))
        // cols, row = 5: RbVlXjYqTc
        assertEquals('R', table.encode(5, 0))
        assertEquals('b', table.encode(5, 1))
        assertEquals('V', table.encode(5, 2))
        assertEquals('l', table.encode(5, 3))
        assertEquals('X', table.encode(5, 4))
        assertEquals('j', table.encode(5, 5))
        assertEquals('Y', table.encode(5, 6))
        assertEquals('q', table.encode(5, 7))
        assertEquals('T', table.encode(5, 8))
        assertEquals('c', table.encode(5, 9))
    }

    @Test
    fun testDecodeMissingChar() {
        val table = DigitTable("GoQd7ShKu2k9wONaC4Hr3mExBU0WgfFn5yP1pIvDLsM8iAtJe6RbVlXjYqTc")
        Assertions.assertThrows(IllegalArgumentException::class.java) { table.decode('Z') }
        Assertions.assertThrows(IllegalArgumentException::class.java) { table.decode('z') }
        Assertions.assertThrows(IllegalArgumentException::class.java) { table.decode('$') }
    }

    @Test
    fun testDecode() {
        val table = DigitTable("GoQd7ShKu2k9wONaC4Hr3mExBU0WgfFn5yP1pIvDLsM8iAtJe6RbVlXjYqTc")
        // rows, col = 0
        assertEquals(0, table.decode('G'))
        assertEquals(0, table.decode('k'))
        assertEquals(0, table.decode('3'))
        assertEquals(0, table.decode('F'))
        assertEquals(0, table.decode('L'))
        assertEquals(0, table.decode('R'))
        // cols, row = 0: GoQd7ShKu2
        assertEquals(0, table.decode('G'))
        assertEquals(1, table.decode('o'))
        assertEquals(2, table.decode('Q'))
        assertEquals(3, table.decode('d'))
        assertEquals(4, table.decode('7'))
        assertEquals(5, table.decode('S'))
        assertEquals(6, table.decode('h'))
        assertEquals(7, table.decode('K'))
        assertEquals(8, table.decode('u'))
        assertEquals(9, table.decode('2'))
        // cols, row = 5: RbVlXjYqTc
        assertEquals(0, table.decode('R'))
        assertEquals(1, table.decode('b'))
        assertEquals(2, table.decode('V'))
        assertEquals(3, table.decode('l'))
        assertEquals(4, table.decode('X'))
        assertEquals(5, table.decode('j'))
        assertEquals(6, table.decode('Y'))
        assertEquals(7, table.decode('q'))
        assertEquals(8, table.decode('T'))
        assertEquals(9, table.decode('c'))
    }

    @Test
    fun testEquals() {
        val table1 = DigitTable("GoQd7ShKu2k9wONaC4Hr3mExBU0WgfFn5yP1pIvDLsM8iAtJe6RbVlXjYqTc")
        val table2 = DigitTable("GoQd7ShKu2k9wONaC4Hr3mExBU0WgfFn5yP1pIvDLsM8iAtJe6RbVlXjYqTc")
        val table3 = DigitTable("ZoQd7ShKu2k9wONaC4Hr3mExBU0WgfFn5yP1pIvDLsM8iAtJe6RbVlXjYqTc")
        assertTrue(table1.equals(table2))
        assertEquals(table1.hashCode(), table2.hashCode())
        assertEquals(table1.toString(), table2.toString())
        assertEquals(table1.toPlainString(), table2.toPlainString())
        assertTrue(table2.equals(table1))
        assertTrue(table1.equals(table1))
        assertFalse(table1.equals(null))
        assertFalse(table1.equals(table3))
    }
}