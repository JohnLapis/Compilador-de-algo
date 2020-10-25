package compilador;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.Collection;

import junitparams.*;
import org.junit.Test;
import org.junit.runner.RunWith;

import junitparams.naming.TestCaseName;

import static junitparams.JUnitParamsRunner.*;
import static org.junit.Assert.assertEquals;

@RunWith(JUnitParamsRunner.class)
public class TestScanner {

    @Test
    @Parameters(method = "arithmeticValues")
    public void testArithmetic(String input, List<String> expected) {
        try {
            assertEquals(Scanner.tokenize(input), expected);
        } catch (Throwable e) {
            System.out.println("Error " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static Collection<Object[]> arithmeticValues() {
        return Arrays.asList(new Object[][] {
                { "44 + 845", Arrays.asList("44", "845") },
                { "44 + 845", Arrays.asList("44", "845") },
                { "44 + 845 * (885 - 33 / 5) ** 9", Arrays.asList("44", "+", "845", "*", "(", "885", "-", "33", "/", "5", ")", "9") },
                { "44 + a * (b) ** 9", Arrays.asList("44", "+", "a", "*", "(", "b", "-", "33", "/", "5", ")", "9") }
            });
    }

}
