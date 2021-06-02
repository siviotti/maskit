package br.net.maskit.maskservice

import br.net.maskit.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class MaskController(@Autowired val maskService: MaskService) {

    @GetMapping("/mask/{id}")
    fun mask(@PathVariable id: String): String {
        return maskService.mask(numericIdOf(id)).toString()
    }

    @GetMapping("/amask/{id}")
    fun amask(@PathVariable id: String): String {
        return maskService.randomMask(numericIdOf(id)).toString()
    }

    @GetMapping("/unmask/{masked}")
    fun unmask(@PathVariable masked: String): String {
        return maskService.unmask(maskedOf(masked)).toString()
    }

    @GetMapping("/dmask/{id}")
    fun dateMask(@PathVariable id: String): String {
        return maskService.dateMask(numericIdOf(id)).toString()
    }

    @GetMapping("/damask/{id}")
    fun dateRandomMask(@PathVariable id: String): String {
        return maskService.dateRandomMask(numericIdOf(id)).toString()
    }

    @GetMapping("/dunmask/{masked}")
    fun dateUnmask(@PathVariable masked: String): String {
        return maskService.dateUnmask(maskedOf(masked)).toString()
    }

}