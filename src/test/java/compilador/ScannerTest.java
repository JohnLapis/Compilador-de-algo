// [[file:ScannerTest.org::*imports][imports:1]]
package compilador;

import java.util.List;
import java.util.Arrays;
import java.util.Collection;
import java.util.ArrayList;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Element;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

import java.io.IOException;
import org.xml.sax.SAXException;
import javax.xml.parsers.ParserConfigurationException;

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
    private static String fixtureDirectory = "src/test/java/compilador/fixtures";

    @Test
    @Parameters(method = "data")
    public void testTokenzine(String input, String expected) {
        System.out.println(input);
        System.out.println(expected);

        try {
            System.out.println("foi");

            assertEquals(String.join(" ", Scanner.tokenize(input)), expected);
        } catch (Throwable e) {
            System.out.println("Error " + e.getMessage());
            e.printStackTrace();
        }
    }
    private Collection<Object[]> data() throws IOException, ParserConfigurationException, SAXException {
        List parameters = new ArrayList();
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = dbFactory.newDocumentBuilder();
        File path = new File(ScannerTest.fixtureDirectory);
        for (String file : path.list()) {
            file = path + "/" + file;
            Document doc = docBuilder.parse(file);
            NodeList nodes = doc.getElementsByTagName("codeSample");
            int nodeLength = nodes.getLength();
            for (int i = 0; i < nodeLength; i++) {
                Element currentNode = (Element) nodes.item(i);
                String input = currentNode.getElementsByTagName("code").item(0)
                    .getTextContent();
                String expected = currentNode.getElementsByTagName("tokens").item(0)
                    .getTextContent();
                parameters.add(Arrays.asList(input, expected));
                System.out.println(parameters.get(i).toString());
            }
        }
        return Arrays.asList(parameters);
        // return parameters;
    }
}
// ScannerTest:1 ends here
