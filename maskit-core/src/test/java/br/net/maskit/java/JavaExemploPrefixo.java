package br.net.maskit.java;

import br.net.maskit.Masked;
import br.net.maskit.NumericId;
import br.net.maskit.PrefixMaskit;

import java.time.LocalDateTime;

import static br.net.maskit.Api.numericIdOf;
import static br.net.maskit.Api.prefixMaskitOf;

/**
 * Exemplo de uso de um Maskit "prefixado" com data e hora. O número mascarado funciona como um token
 * que tem internamente, além do número de ID original, uma data/hora embutida.
 * O Maskit "prefixado" adiciona a data/hora, data ou hora na frente do número e mascara
 * o conjunto inteiro. Para isso é necessário definir uma sequência de Swap com um número de índices
 * igual a soma dos dígitos do prefixo (data/hora, data ou hora) com as do ID.
 *
 * @author Douglas Siviotti
 * @since 1.0
 */
public class JavaExemploPrefixo {

    public static void main(String[] args) {
        // Defina uma sequência de Swap com indices embaralhados
        // São 25 índices por causa do prefixo Data/Hora que tem 14 caracteres
        // 14 (data/hora) + 11 (cpf) = 25 índices
        String seq = "17,2,10,22,12,3,19,8,15,6,23,9,11,16,0,24,7,14,21,1,18,5,20,4,13";
        // Define 60 caracteres da tabela     012345678901234567890123456789012345678901234567890123456789
        String table = "GoQd7ShKu2k9wONaC4Hr3mExBU0WgfFn5yP1pIvDLsM8iAtJe6RbVlXjYqTc";
        // Cria uma instância de Maskit que utiliza prefixos
        // PrefixMaskit maskit = prefixMaskitOf(seq, table, PrefixType.DATE_TIME); // opcional
        PrefixMaskit maskit = prefixMaskitOf(seq, table); // DATE_TIME é o default
        // Cria uma versão mascarada do CPF (25 dígitos: 14 da Data/Hora + 11 CPF)
        Masked masked = maskit.mask(numericIdOf("12345678901"));
        System.out.println("data/hora + CPF mascarados   :" + masked + " (25 dígitos)");
        // Número desmascarado com 25 dígitos onde os 14 primeiros s]ao data/hore
        NumericId unmasked = maskit.unmask(masked);
        System.out.println("Data/Hora + CPF desmascarados:" + unmasked +" (25 dígitos)");
        // Obtendo as duas partes do número (Data/hora e CPF)
        LocalDateTime ldt = (LocalDateTime) maskit.extractPrefixObj(masked);
        System.out.println("Data/Hora extraída :" + ldt);
        String cpfOriginal = maskit.extractId(masked).toString();
        System.out.println("CPF extraído       :" + cpfOriginal);
    }
}
