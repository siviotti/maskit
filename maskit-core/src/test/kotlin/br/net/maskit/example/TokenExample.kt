package br.net.maskit.example

import br.net.maskit.numericIdOf
import br.net.maskit.randomPrefixMaskit

fun main() {
    val maskit = randomPrefixMaskit(25) // date/time (14) + Id (11) = 25
    val id = numericIdOf("12345678901") // Id size = 11
    val masked = maskit.mask(id) // masked size = 25!
    val unmasked = maskit.unmask(masked)
    println("Original ID: $id, masked: $masked, unmasked: $unmasked")
    println("Extracted ID       : ${maskit.extractId(masked)}")
    println("Extracted Date/Time: ${maskit.extractPrefixObj(masked)}")
}