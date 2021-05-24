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
        println("id:$id")
        return maskService.randomMask(numericIdOf(id)).toString()
    }

    @GetMapping("/unmask/{masked}")
    fun unmask(@PathVariable masked: String): String {
        println("masked:$masked")
        return maskService.unmask(maskedOf(masked)).toString()
    }

}