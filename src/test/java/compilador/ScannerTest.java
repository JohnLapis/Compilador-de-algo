// [[file:ScannerTest.org::*imports][imports:1]]
package compilador;

import java.util.List;
import java.util.Collection;
import java.nio.file.Path;
import java.nio.file.Paths;

import junitparams.*;
import org.junit.Test;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;

import static junitparams.JUnitParamsRunner.*;
import static org.junit.Assert.assertEquals;
// imports:1 ends here

// [[file:ScannerTest.org::*ScannerTest][ScannerTest:1]]
@RunWith(JUnitParamsRunner.class)
public class ScannerTest {
    private Path fixtureDirectory = Paths.get("fixtures");

    // @BeforeClass
    // private void setUp() {
    //     for file in directory x
    //                  load file into property


    //                  and then make _____Values() use the attribute
    //     for (Path filename : directory.listFiles()) {
    //         this.filename = loadFile(filename);
    //     }
    // }

    @Test
    @FileParameters(value = "src/test/java/compilador/fixtures/expression.xml", mapper = XMLMapper.class)
    public void testTokenzine(String input, List<String> expected) {
        System.out.println(input);
        System.out.println(expected);

        try {
            assertEquals(Scanner.tokenize(input), expected);
        } catch (Throwable e) {
            System.out.println("Error " + e.getMessage());
            e.printStackTrace();
        }
    }
}
// ScannerTest:1 ends here
