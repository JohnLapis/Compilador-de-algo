package compilador;

import org.junit.Test;
import static org.junit.Assert.assertTrue;
import java.util.Arrays;

public class TestScanner {
    @Test
    public void number() {
        System.out.println(Arrays.toString(Scanner.main("44 + 55")));
        System.out.println(Arrays.toString(Scanner.main("44")));
        System.out.println(Arrays.toString(Scanner.main("44")));
    }
}
