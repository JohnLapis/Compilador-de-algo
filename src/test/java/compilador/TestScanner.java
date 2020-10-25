package compilador;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import java.util.Arrays;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;
import java.util.List;
import java.util.ArrayList;
import org.junit.runners.Suite;
import java.util.Collection;


@RunWith(Suite.class)
@Suite.SuiteClasses({
        TestArithmetic.class,
            })
public class TestScanner {

}

@RunWith(Parameterized.class)
class TestArithmetic {
    @Parameters
    public static List data() {
        return Arrays.asList(
                          Arrays.asList("44 + 845", Arrays.asList("44", "845")),
                          Arrays.asList("44 + 845 * (885 - 33 / 5) ** 9", Arrays.asList("44", "+", "845", "*", "(", "885", "-", "33", "/", "5", ")", "9")),
                          Arrays.asList("44 + a * (b) ** 9", Arrays.asList("44", "+", "a", "*", "(", "b", "-", "33", "/", "5", ")", "9"))
                          );
    }

    @Parameter
    public String input;

    @Parameter(1)
    public List<String> expected = new ArrayList<String>();

    public TestArithmetic(String input, List<String> expected) {
        this.input = input;
        this.expected = expected;
    }

    @Test
    public void testArithmetic() {
        System.out.println(input);
        System.out.println("[ " + String.join(", ", expected) + " ]");
        // try {
        //     assertEquals(Scanner.tokenize(input), expected);
        // } catch (Throwable e) {
        //     System.out.println("Error " + e.getMessage());
        //     e.printStackTrace();
        // }
    }
}
