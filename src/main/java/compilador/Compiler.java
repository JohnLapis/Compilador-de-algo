// [[file:Compiler.org::*imports][imports:1]]
package compilador;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;
import java.nio.file.Files;
import java.text.ParseException;
import java.util.List;
import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.PrintWriter;
// imports:1 ends here

// [[file:Compiler.org::*Compiler][Compiler:1]]
public class Compiler {
    public static void main(String[] args) throws IOException, ParseException, Exception {
        // It will only accept one file.
        if (args.length != 1) {
            System.out.println("Wrong number of parameters provided.");
            throw new Exception();
        }

        String inputFile = args[0];
        Path path = Paths.get(inputFile);
        String code = String.join("\n", Files.readAllLines(path));
        List<String> tokenizedCode = Scanner.tokenize(code);
        String outputFile = "o.js";
        try (PrintWriter outputStream = new PrintWriter(outputFile)) {
            outputStream.println(String.join(" ", tokenizedCode));
        }
    }
}
// Compiler:1 ends here
