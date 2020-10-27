// [[file:Scanner.org::*imports][imports:1]]
package compilador;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
// imports:1 ends here

// [[file:Scanner.org::*tokenizer_start][tokenizer_start:1]]
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
        // tokenizer_start:1 ends here

        // [[file:Scanner.org::*comment][comment:1]]
        // Only multiline comments are matched with the DOTALL flag.
        String COMMENT = "(?s:/\\*.*?\\*/)|//.*";
        // comment:1 ends here

        // [[file:Scanner.org::*special_token][special_token:1]]
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
        // special_token:1 ends here

        // [[file:Scanner.org::*literal][literal:1]]
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
        // literal:1 ends here

        // [[file:Scanner.org::*line_terminator][line_terminator:1]]
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
        // line_terminator:1 ends here

        // [[file:Scanner.org::*identifier_name][identifier_name:1]]
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
            "[\p{L}\p{Nl}]"
            // Valid ending characters
            + "[\p{L}\p{Nl}\\u200c\\200d]+";
        // identifier_name:1 ends here

        // [[file:Scanner.org::*matching][matching:1]]
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
        // matching:1 ends here

        // [[file:Scanner.org::*tokenizer_end][tokenizer_end:1]]
        return matches;
    }
}
// tokenizer_end:1 ends here
