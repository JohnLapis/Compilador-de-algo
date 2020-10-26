// [[file:ScannerTest.org::*Mapper][Mapper:1]]
package compilador;

import junitparams.mappers.DataMapper;
import java.io.Reader;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.xml.sax.InputSource;
import java.io.InputStream;
import java.io.ByteArrayInputStream;

import java.io.StringReader;
import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;


public class XMLMapper implements DataMapper {
    public Object[] map(Reader reader) {
        try {
            // Arbittray size. Should change in the future.
            char charArray[] = new char[5000];
            reader.read(charArray);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(charArray);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = dbFactory.newDocumentBuilder();
            // InputStream uri = new InputStream(stringBuilder.toString());
            // InputStream uri = new InputStream(reader);

            ByteArrayInputStream uri = new ByteArrayInputStream(stringBuilder.toString().getBytes("UTF-8"));
            Document doc = docBuilder.parse(uri);
            NodeList nodes = doc.getElementsByTagName("code");
            String elements[] = new String[nodes.getLength()];
            for (int i = 0; i < nodes.getLength(); i++) {
                elements[i] = nodes.item(i).getTextContent();
            }
            return elements;
        } catch (IOException e) {
            System.out.println("Error " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException();
        } catch (SAXException e) {
            System.out.println("Error " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException();
        } catch (ParserConfigurationException e) {
            System.out.println("Error " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException();
        }
    }
}
// Mapper:1 ends here
