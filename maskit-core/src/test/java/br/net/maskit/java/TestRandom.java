package br.net.maskit.java;

import br.net.maskit.Maskit;
import br.net.maskit.NumericId;

import java.util.HashSet;
import java.util.Set;

import static br.net.maskit.ApiKt.*;

public class TestRandom {

    public static void main(String[] args) {
        Set<String> set1 = new HashSet<String>();
        Set<String> set2 = new HashSet<String>();
        Maskit<NumericId> maskit = defaultMaskit();
        NumericId cpf1 = numericIdOf("07311631769");
        NumericId cpf2 = numericIdOf("11204786771");

        for (int i = 0; i < 10000000; i++) {
            set1.add(maskit.randomMask(cpf1).toString());
            set2.add(maskit.randomMask(cpf2).toString());
        }
        System.out.println("set1:" + set1.size());
        System.out.println("set2:" + set2.size());
        set1.retainAll(set2);
        System.out.println("Itens repetidos:" + set1);
    }
}

