package br.net.maskit.java;

import br.net.maskit.*;

import static br.net.maskit.Api.numericIdOf;
import static br.net.maskit.Api.numericMaskitOf;

/**
 * Exemplo de uso da Maskit através de funções "factory method". Este exemplo e mais recomendado,
 * pois é bem provável que a sequência de índices e a tabela de caracteres sejam obtidas na forma
 * de uma String a partir de uma variável de ambiente ou parâmetro do programa.
 *
 * @author Douglas Siviotti
 * @since 1.0
 */
public class JavaExemploSimples {

    public static void main(String[] args) {
        // Defina uma sequência de Swap com indices embaralhados
        String seq = "7,9,1,5,0,10,6,2,3,8,4";
        // Define 60 caracteres da tabela     012345678901234567890123456789012345678901234567890123456789
        String table = "GoQd7ShKu2k9wONaC4Hr3mExBU0WgfFn5yP1pIvDLsM8iAtJe6RbVlXjYqTc";
        // Cria uma instância de Maskit para Ids Numéricos (exemplo CPF)
        Maskit<NumericId> maskit = numericMaskitOf(seq, table);
        // Cria uma versão mascarada do CPF (11 dígitos)
        Masked masked = maskit.mask(numericIdOf("12345678901"));
        System.out.println("CPF mascarado   :" + masked);
        NumericId unmasked = maskit.unmask(masked);
        System.out.println("CPF desmascarado:" + unmasked);
    }
}
