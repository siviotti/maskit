package br.net.maskit.maskservice

import br.net.maskit.Maskit
import br.net.maskit.NumericId
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan

@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan("br.net.maskit.maskservice")
@EntityScan("br.net.maskit.maskservice")
class MaskserviceApplication

fun main(args: Array<String>) {
	runApplication<MaskserviceApplication>(*args)
}
