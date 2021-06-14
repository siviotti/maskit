package br.net.maskit.lgpd

import br.net.maskit.Masked
import java.time.LocalDateTime

data class Agente(val id: String, val nome: String)

data class Tratamento @JvmOverloads constructor (val id: String, val metadados: List<String> = listOf())

data class Titular @JvmOverloads constructor (val id: String, val dados: Map<String,String> = mapOf())

data class Registro(val momento: LocalDateTime, val tratamento: String, val titular: Masked)