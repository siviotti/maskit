package br.net.maskit.maskservice

import br.net.maskit.*
import org.springframework.stereotype.Controller

@Controller
class MaskService {

    private val maskit: NumericMaskit = defaultMaskit()

    init{
        println("Constructor MaskService")
    }

    fun mask (id: NumericId) = maskit.mask(id)

    fun randomMask (id: NumericId) = maskit.randomMask(id)

    fun unmask(masked: Masked) = maskit.unmask(masked)
}