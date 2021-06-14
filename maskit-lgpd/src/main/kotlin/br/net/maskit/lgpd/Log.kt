package br.net.maskit.lgpd

import java.time.LocalDateTime

/**
 * Classe que efetua um LOG de registro de tratamento (Artigo 37).
 * Há três opções de log conforme 3 níveis de informação que é logada:
 *
 * @author Douglas Siviotti
 * @since 1.0
 */
class Log(val tratamento: Tratamento) {


    /**
     * Cria o conteúdo de um log de nível 1 (momento, tratamento, titular).
     *
     * @param titular O titular dos dados tratados na ocorrência.
     */
    fun nivel1(titular: Titular) {

    }

    /**
     * Cria o conteúdo de um log de nível 2 (momento, tratamento, titular + metadados tratados).
     *
     * @param titular O titular dos dados tratados na ocorrência.
     * @param metadados O conjunto de metadados tratados numa determinada ocorrência. Pode não ser igual
     * aos declarados previamente  no tratamento, mas idealmente, devem ser um subconjunto deste.
     *
     */
    @JvmOverloads
    fun nivel2(titular: Titular, metadados: Set<String> = titular.dados.keys) {

    }

    /**
     * Cria o conteúdo de um log de nível 3 (momento, tratamento, titular + metadados e DADOS tratados).
     *
     * @param titular O titular dos dados tratados na ocorrência.
     * @param dados Um mapa contendo os metadados e seus respectivos valores relacionados ao titular.
     * Só é usado se os dados contidos no parâmetro "titular" não forem adequados.
     */
    @JvmOverloads
    fun nivel3(titular: Titular, dados: Map<String, String> = titular.dados) {

    }


}