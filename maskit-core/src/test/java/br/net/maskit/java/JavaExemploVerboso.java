package br.net.maskit.java;

import static br.net.maskit.Api.*;

import br.net.maskit.*;

import java.util.Arrays;

/**
 * Exemplo de uso da Maskit através da criaçao simples de objetos necessários para seu funcionamento.
 *
 * @author Douglas Siviotti
 * @since 1.0
 */
public class JavaExemploVerboso {

    public static void main(String[] args) {
        // Defina uma sequência de Swap com indices embaralhados
        Swapper swapper = new Swapper(Arrays.asList(7, 9, 1, 5, 0, 10, 6, 2, 3, 8, 4));
        // Define 60 caracteres da tabela 012345678901234567890123456789012345678901234567890123456789
        DigitTable table = new DigitTable("GoQd7ShKu2k9wONaC4Hr3mExBU0WgfFn5yP1pIvDLsM8iAtJe6RbVlXjYqTc");
        // Cria uma instância de Maskit para Ids Numéricos (exemplo CPF)
        Maskit<NumericId> maskit = new NumericMaskit(swapper, table);
        // Cria uma versão mascarada do CPF (11 dígitos)
        Masked masked = maskit.mask(numericIdOf("12345678901"));
        System.out.println("CPF mascarado   :" + masked);
        NumericId unmasked = maskit.unmask(masked);
        System.out.println("CPF desmascarado:" + unmasked);
    }
}
