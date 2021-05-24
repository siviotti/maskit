package br.net.maskit.maskservice

import br.net.maskit.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.context.annotation.ComponentScan
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController


@RestController
@ComponentScan("br.net.maskit")
@EntityScan("br.net.maskit")
class MaskController(@Autowired val maskit: NumericMaskit) {

    @GetMapping("/mask/{id}")
    fun mask(@PathVariable id: String): String {
        println("id:$id")
        return maskit.mask(numericIdOf(id)).toString()
    }
}