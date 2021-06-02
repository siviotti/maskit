package br.net.maskit.java;

import br.net.maskit.*;

import java.time.LocalDateTime;

import static br.net.maskit.Api.*;

/**
 * Exemplo de uso de um Maskit e PrefixMaskit aleatórios. Neste caso a tabela de caracteres e a
 * sequência de Swap são geradas randomicamente. A única informação fornecida é o tamanho do ID.
 * No caso do CPF será 11 dígitos. A criação é feita através das funções:
 * <code>randomMaskit</code> e <code>randomPrefixMaskit</code>
 *
 * @author Douglas Siviotti
 * @since 1.0
 */
public class JavaExemploRandom {

    public static void main(String[] args) {
        // Cria uma instância de Maskit aleatória. Define apenas o tamanho do ID
        NumericMaskit maskit = randomMaskit(11);
        // Cria uma versão mascarada do CPF (11 dígitos)
        Masked masked = maskit.mask(numericIdOf("12345678901"));
        System.out.println("CPF mascarado   :" + masked);
        NumericId unmasked = maskit.unmask(masked);
        System.out.println("CPF desmascarado:" + unmasked);

        // Cria uma instância de PrefixMaskit aleatória. Define apenas o tamanho do ID
        PrefixMaskit maskit2 = randomPrefixMaskit(25); // DATE_TIME default
        // Cria uma versão mascarada do CPF (25 dígitos: 14 da Data/Hora + 11 CPF)
        Masked masked2 = maskit2.mask(numericIdOf("12345678901"));
        System.out.println("data/hora + CPF mascarados   :" + masked2 + " (25 dígitos)");
        // Número desmascarado com 25 dígitos onde os 14 primeiros s]ao data/hore
        NumericId unmasked2 = maskit2.unmask(masked2);
        System.out.println("Data/Hora + CPF desmascarados:" + unmasked2 + " (25 dígitos)");
        // Obtendo as duas partes do número (Data/hora e CPF)
        LocalDateTime ldt = (LocalDateTime) maskit2.extractPrefixObj(masked2);
        System.out.println("Data/Hora extraída :" + ldt);
        String cpfOriginal = maskit2.extractId(masked2).toString();
        System.out.println("CPF extraído       :" + cpfOriginal);

    }

}
