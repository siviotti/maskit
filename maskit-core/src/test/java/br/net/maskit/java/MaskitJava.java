package br.net.maskit.java;

import br.net.maskit.Masked;
import br.net.maskit.Maskit;
import br.net.maskit.NumericId;
import br.net.maskit.NumericMaskit;

import static br.net.maskit.Api.*;

public class MaskitJava {

    public static void main(String[] args) {
        String cpf = "07311631769";
        NumericMaskit maskit = defaultMaskit();
        Masked masked = maskit.mask(numericIdOf(cpf));
        Masked randomMasked = maskit.randomMask(numericIdOf(cpf));
        System.out.println("CPF Original    :"+ cpf);
        System.out.println("CPF Mascarado   :" + masked);
        System.out.println("Random Masked   :" + randomMasked);
        System.out.println("CPF Desmascarado:" + maskit.unmask(masked));
        System.out.println("Random Desmask  :" + maskit.unmask(masked));

    }
}
