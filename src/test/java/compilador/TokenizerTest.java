package compilador;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class TokenizerTest {
    @Test
    public void number() {
        System.out.println(compilador.Arithmetic.main("44 + 55"));
        System.out.println(compilador.Arithmetic.main("44"));
    }
}
