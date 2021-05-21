package br.net.maskit.java;

import br.net.maskit.Maskit;
import br.net.maskit.NumericId;
import org.junit.jupiter.api.Test;

import static br.net.maskit.ApiKt.*;

public class MaskitJava {

    @Test
    public void testMaskit(){

        Maskit<NumericId> maskit = numericMaskitOf("021", "abc");
    }
}
