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
    public enum Keywords {};

    public static String tokenize(String code) throws  {
        Pattern pattern = Pattern.compile("\\d+");
        String matches[];
        Matcher matcher = pattern.matcher(code);
        while (matcher.find()) {
            matches.append(matcher.group());
        }
        if (matches.length == 0) {
            throw Exception;
        }
    }

    public static void main(String[] args) {
        // It will only accept one file.
        if (args.length != 1) {
            System.out.println("Wrong number of parameters provided.");
            return;
        }

        System.out.println(tokenize(args[0]));

        Path path = Paths.get(args[0]);
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
