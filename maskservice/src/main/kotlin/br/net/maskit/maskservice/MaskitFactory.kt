package br.net.maskit.maskservice

import br.net.maskit.Maskit
import br.net.maskit.NumericId
import br.net.maskit.NumericMaskit
import br.net.maskit.defaultMaskit
import org.springframework.beans.factory.config.ConfigurableBeanFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Scope

class MaskitFactory {

    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
    fun createMaskit(): NumericMaskit{
        return defaultMaskit()
    }
}