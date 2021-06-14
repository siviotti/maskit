package br.net.maskit.lgpd

/**
 * Interface que especifica um objeto capaz de realizar a operação de escrita ou gravação do registro
 * de atividade de tratamento. É o momento em que o tratamento é efetivamente registrado.
 * <P>
 * Este interface deve ser implementada pela aplicação cliente que deve escolher a melhor forma
 * técnica de efetivamente executar o Log (Log4J, Api de Log, banco de dados etc).
 *
 * @since 1.0
 * @author Douglas Siviotti
 */
interface Registrador {

    fun registra(conteudo: String)
}