// [[file:Scanner.org::*imports][imports:1]]
package compilador;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
// imports:1 ends here

// [[file:Scanner.org::*code][code:2]]
class Scanner {
    public enum Keywords {};

    public static List<String> tokenize(String code) throws ParseException {
        /* The matching is eager, so, if there's a character that could be
         * repeated (like '>' and '>>' or '*' and '**') as a token, a single
         * token of the repeated character will be matched instead of many
         * tokens of a single character.
         *
         * And they are in order of priority. Ex: matching '!=' has higher
         * precedence than '!'. Resulting in '!=' instead of '!', '='.
         */
        // code:2 ends here

        // [[file:Scanner.org::*code][code:3]]
        // Only multiline comments are matched with the DOTALL flag.
        String COMMENT = "(?s:/\\*.*?\\*/)|//.*";
        // code:3 ends here

        // [[file:Scanner.org::*code][code:4]]
        String SPECIAL_TOKEN =
            String.join("|",
                        // Special handlings
                        "!==?|!|\\?(\\.(?=\D)|\\?)?",
                        // Single punctuators
                        "\\(|\\)|\\[|\\]|\\{|\\}|,|\\.(\\.{2})?|;|:|~|\\",
                        // Punctuators with '='
                        "(\\+|-|\\*{1,2}|/|%|<{1,2}|>{1,3}|^|&|\\|)=|=>|={1,3}",
                        // Punctuators which may have repeatable character
                        "\\+{1,2}|-{1,2}|\\*{1,2}|%|/|>{1,3}|<{1,2}|&{1,2}|\\|{1,2}"
                        );
        String LITERAL =
            String.join("|",
                        // Decimal literal
                        "",
                        // Octal literal
                        "",
                        // Hexadecimal literal
                        "",
                        // String literal
                        "\".*?\"",
                        // Regex literal
                        "/.*/",
                        // Template literal
                        "(?s:`.*?`)"
                        );
        // code:4 ends here

        // [[file:Scanner.org::*code][code:5]]
        /* The LINE_TERMINATOR possible characters are:
         * U+000A	LINE FEED (LF)	<LF>
         * U+000D	CARRIAGE RETURN <CR>
         * U+2028	LINE SEPARATOR	<LS>
         * U+2029 PARAGRAPH SEPARATOR <PS>
         *
         * <CR> followed by <LF> is considered token for better reporting of
         * line numbers.
         */
        String LINE_TERMINATOR = "\\u000A+|\\000D+|\\u2028+|\\u2029+";
        // code:5 ends here

        // [[file:Scanner.org::*code][code:6]]
        /*
         * IDENTIFIER_NAME allows UnicodeEscapeSequences that, when replaced by
         * a SourceCharacter is still a valid IDENTIFIER_NAME. Ex: '\0061' is
         * valid because it represents the character 'e' and '\0025' is invalid
         * because it represents the character '%'.
         *
         * The code points U+200C and U+200D are named, respectively, <ZWNJ>, <ZWJ>.
         */
        String IDENTIFIER_NAME =
            // Valid starting character
            "[a-zA-Z$_]"
            // Valid ending characters
            + "[a-zA-Z$_\\u200c\\200d]+"
            // code:6 ends here

            // [[file:Scanner.org::*code][code:7]]
            Pattern pattern =
            Pattern.compile(String.join("|",
                                        COMMENT,
                                        // LITERAL,
                                        IDENTIFIER_NAME,
                                        LINE_TERMINATOR,
                                        SPECIAL_TOKEN
                                        ));
        Matcher matcher = pattern.matcher(code);
        List<String> matches = new ArrayList<String>();
        while (matcher.find()) {
            String match = matcher.group();
            if (match.contains("/*") || match.contains("//")) {
                System.out.println("ignored");
                continue;
            }
            // System.out.println(matcher.start() + " " + matcher.end());

            matches.add(match);
        }
        return matches;
    }
}
// code:7 ends here
