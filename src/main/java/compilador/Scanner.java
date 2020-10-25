// [[file:Scanner.org::*imports][imports:1]]
package compilador;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// imports:1 ends here

// [[file:Scanner.org::*number][number:1]]
class Scanner {
  public enum Keywords {};

  public static List<String> tokenize(String code) throws ParseException {
    Pattern pattern = Pattern.compile("\\d+");
    Matcher matcher = pattern.matcher(code);
    List<String> matches = new ArrayList<String>();
    while (matcher.find()) {
      matches.add(matcher.group());
    }
    if (matches.length == 0) {
        return ParseException;
    }
    return matches;
  }

  public static void main(String[] args) {
    // It will only accept one file.
    if (args.length != 1) {
      System.out.println("Wrong number of parameters provided.");
      return;
    }

    Path path = Paths.get(args[0]);
    String code;
    try {
        code = String.join("\n", Files.readAllLines(path));
    } catch (IOException e) {
        System.out.println("Error " + e.getMessage());
        e.printStackTrace();
        return;
    }
    System.out.println("[ " + String.join(", ", tokenize(code)) + " ]");

  }
}
// number:1 ends here
