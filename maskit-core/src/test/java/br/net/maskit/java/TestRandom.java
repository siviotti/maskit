package br.net.maskit.java;

import br.net.maskit.Maskit;
import br.net.maskit.NumericId;

import java.util.HashSet;
import java.util.Set;

import static br.net.maskit.Api.*;

/**
 * Teste de duplicidade em mascaras randômicas (maskit.randomMask()).
 *
 * Um CPF, por exemplo, pode gerar cerca de 60.000.000 de possibilidades randômicas.
 *
 * Dois CPFs diferentes nunca geram mascarados iguais. As 60 milhões são exclusivas do CPF.
 *
 * @author Douglas Siviotti
 */
public class TestRandom {

    public static void main(String[] args) {
        Set<String> set1 = new HashSet<String>();
        Set<String> set2 = new HashSet<String>();
        Maskit<NumericId> maskit = defaultMaskit();
        NumericId cpf1 = numericIdOf("07311631769");
        NumericId cpf2 = numericIdOf("11204786771");
        long max = 100000;
        for (int i = 0; i < max; i++) {
            set1.add(maskit.randomMask(cpf1).toString());
            set2.add(maskit.randomMask(cpf2).toString());
        }
        System.out.println("set1:" + set1.size() + " Itens repetidos:" + (max - set1.size()));
        System.out.println("set2:" + set2.size() + " Itens repetidos:" + (max - set2.size()));
        set1.retainAll(set2); // intersessão entre os conjuntos (repetidos)
        System.out.println("Itens iguais entre conjuntos:" + set1);
    }
}

/** Resultados médios de execução (10 execuções para cada quantidade):
 * 1000: repetidos: 0, repetição = 0%
 * 10.000 repetidos: 0, repetição 0% (algumas vezes deu 1)
 * 100.000 repetidos: 12, repetiçao = 0,012%
 * 1.000.000 repetidos: 1400, repetição = 0,14%
 * 10.000.000 repetidos: 136.600, repetição = 1,36%
 */

