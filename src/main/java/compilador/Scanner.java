// [[file:Scanner.org::*imports][imports:1]]
package compilador;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
// imports:1 ends here

// [[file:Scanner.org::*scanner][scanner:1]]
class Scanner {
    public enum Keywords {};

    public static List<String> tokenize(String code) throws ParseException {
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(code);
        List<String> matches = new ArrayList<String>();
        while (matcher.find()) {
            matches.add(matcher.group());
        }
        return matches;
    }
}
// scanner:1 ends here
