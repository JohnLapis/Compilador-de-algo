// [[file:App.org::*imports][imports:1]]
package compilador;

import java.nio.file.Path;
import java.util.regex.Pattern;
import java.nio.file.Paths;
import java.io.IOException;
import java.nio.file.Files;
import java.util.regex.Matcher;
// imports:1 ends here

// [[file:App.org::*number][number:1]]
class Arithmetic {
    public enum Keywords = {};

    public void tokenzine(String code) {
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(code);
    }

    public static void main(String[] args) {
        if (args.length > 1) {
            Path path = Paths.get(args[1]);
        } else {
            System.out.println("No filename provided.");
        }
        String text;
        try {
            text = String.join("\n", Files.readAllLines(path));
        } catch (IOException e) {
            System.out.println("Error " + e.getMessage());
            e.printStackTrace();
            return;
        }

    }
}
// number:1 ends here

// [[file:App.org::*number][number:2]]
public class App {
    public static void main( String[] args ) {
        System.out.println("Hello World!");
    }
}
// number:2 ends here
