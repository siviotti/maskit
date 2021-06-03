package br.net.maskit.example

import br.net.maskit.numericIdOf
import br.net.maskit.numericMaskitOf

fun main() {
    val sequence = "7,9,1,5,0,10,6,2,3,8,4";
    val table = "GoQd7ShKu2k9wONaC4Hr3mExBU0WgfFn5yP1pIvDLsM8iAtJe6RbVlXjYqTc"
    val maskit = numericMaskitOf(sequence, table)
    val id = numericIdOf("12345678901")
    val masked = maskit.mask(id)
    val unmasked = maskit.unmask(masked)
    println("Original ID: $id, masked: $masked, unmasked: $unmasked")
    //Original ID: 12345678901, masked: T3whm9JOPcU, unmasked: 12345678901
}