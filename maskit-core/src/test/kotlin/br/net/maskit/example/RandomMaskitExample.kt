package br.net.maskit.example

import br.net.maskit.numericIdOf
import br.net.maskit.randomMaskit

fun main() {
    val maskit = randomMaskit(11) // Id size = 11
    val id = numericIdOf("12345678901") // size = 11
    val masked = maskit.mask(id)
    val unmasked = maskit.unmask(masked)
    println("Original ID: $id, masked: $masked, unmasked: $unmasked")
}