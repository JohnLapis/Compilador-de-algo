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
        /**
         * The matching is eager, so, if there's a character that could be
         * repeated (like '>' and '>>' or '*' and '**') as a token, a single
         * token of the repeated character will be matched instead of many
         * tokens of a single character.
         *
         * And they are in order of priority. Ex: matching '!=' has higher
         * precedence than '!'. Resulting in '!=' instead of '!', '='.
         */
        // tokenizer_start:1 ends here

        // [[file:Scanner.org::*_COMMENTS][_COMMENTS:1]]
        // Only multiline comments are matched with the DOTALL flag.
        String COMMENT = "(?s:/\\*.*?\\*/)|//.*";
        // _COMMENTS:1 ends here

        // [[file:Scanner.org::*PUNCTUATOR][PUNCTUATOR:1]]
        String PUNCTUATOR =
            String.join("|",
                        // Special handlings
                        "!==?|!|\\?(\\.(?=\\D)|\\?)?",
                        // Single punctuators
                        "\\(|\\)|\\[|\\]|\\{|\\}|,|\\.(\\.{2})?|;|:|~|\\",
                        // Punctuators with '='
                        "(\\+|-|\\*{1,2}|/|%|<{1,2}|>{1,3}|^|&|\\|)=|=>|={1,3}",
                        // Punctuators which may have repeatable character
                        "\\+{1,2}|\\-{1,2}|\\*{1,2}|%|/|>{1,3}|<{1,2}|&{1,2}|\\|{1,2}"
                        );
        // PUNCTUATOR:1 ends here

        // [[file:Scanner.org::*LINE_TERMINATOR][LINE_TERMINATOR:1]]
        /**
         * The LINE_TERMINATOR possible characters are:
         * U+000A	LINE FEED (LF)	<LF>
         * U+000D	CARRIAGE RETURN <CR>
         * U+2028	LINE SEPARATOR	<LS>
         * U+2029 PARAGRAPH SEPARATOR <PS>
         *
         * <CR> followed by <LF> is considered token for better reporting of
         * line numbers.
         */
        String LINE_TERMINATOR = "\\u000A+|\\000D+|\\u2028+|\\u2029+";
        // LINE_TERMINATOR:1 ends here

        // [[file:Scanner.org::*LITERAL][LITERAL:1]]
        String CHARATER_ESCAPE_SEQUENCE =
            String.join("|",
                        "[^" + LINE_TERMINATOR + "\\dxu]",
                        "0(?!\\d)",
                        "x[0-9a-fA-F]{2}",
                        "u([0-9a-fA-F]{4}|)"
                        );
        /**
         * - Numeric literals are case-insensitive.
         *
         * - All literals have a word boundary between them and any other token.
         *
         * - The sign before the number acts as a operator regardless if there's
         * just one number.
         *
         * - Many operators are lazy (with "?" at the end) because many of the
         * - characters possible inside a literal are also possible outside it.
         * - Therefore, unless it is matched in the regex with an escape ("\")
         * - it'll define the end of the literal.
         */
        String LITERAL =
            String.join("|",
                        // Decimal literal
                        /**
                         * If starts with [1-9] digit, is followed by 0 or
                         * more digits, an optional dot, and more digits after
                         * the dot. The first '[0-9]*' consumes all digits if
                         * there's no digit after the dot, and the matching of
                         * the number remains consistent without any
                         * lookbackward checing.
                         *
                         * If starts with a dot, is followed by 1 or more digits.
                         *
                         * In both cases, the exponential part is optional.
                         */
                        "(([1-9][0-9]*\\.?[0-9]*)|(\\.[0-9]+))([eE][+-]?[0-9]+)?",
                        // Big integer decimal literal
                        "(0|[1-9][0-9]*)n",
                        // Binary literal
                        "0[bB][01]+n?",
                        // Octal literal
                        "0[oO][0-7]+n?",
                        // Hexadecimal literal
                        "0[xX][0-9a-fA-F]+n?",
                        // LITERAL:1 ends here

                        // [[file:Scanner.org::*LITERAL][LITERAL:2]]
                        // String literal
                        /**
                         * Each regex composing a string literal matches only
                         * one character. The end result is that you have all
                         * those possible characters being matched by the lazy
                         * wildcard "*". And that's why it is wrapped in
                         * parentheses.
                         */
                        "['\"]("
                        // String literal: Prohibited characters
                        + "[^\\u005c\\u000d\\u000a]"
                        // String literal: Line continuation
                        + "|" + LINE_TERMINATOR + "(?<=\\)"
                        // String literal: Escape sequence
                        + "|" + "\\" + CHARATER_ESCAPE_SEQUENCE
                        + ")*?['\"]",
                        // Regex literal
                        /**
                         * The sequence "//" isn't an empty regex literal,
                         * it's comment.
                         *
                         * U+005C is a backslash, which doesn't conflict with
                         * the non-literal dot following it.
                         */
                        // LITERAL:2 ends here

                        // [[file:Scanner.org::*LITERAL][LITERAL:3]]
                        // Regex literal: first character
                        "/([^\\*\\[/\\]|(\\u005c).+|\\[.*?\\])"
                        // Regex litreal: following characters
                        + "/([^\\[/\\]|(\\u005c).+|\\[.*?\\])*?/",
                        // LITERAL:3 ends here

                        // [[file:Scanner.org::*LITERAL][LITERAL:4]]
                        // Template literal
                        "(?s:`([^`\\]|(\\u005c).+)*?`)"
                        );
        // LITERAL:4 ends here

        // [[file:Scanner.org::*IDENTIFIER_NAME][IDENTIFIER_NAME:1]]
        /**
         * IDENTIFIER_NAME allows UnicodeEscapeSequences that, when replaced by
         * a SourceCharacter is still a valid IDENTIFIER_NAME. Ex: '\0061' is
         * valid because it represents the character 'e' and '\0025' is invalid
         * because it represents the character '%'.
         *
         * The code points U+200C and U+200D are named, respectively, <ZWNJ>, <ZWJ>.
         */
        String IDENTIFIER_NAME =
            // Turn on Unicode_Character_Class flag
            "(?U)"
            // Valid starting character
            + "[\\p{L}\\p{Nl}]+"
            // Valid ending characters
            + "[\\p{L}\\p{Nl}\\u200c\\u200d]*";
        // IDENTIFIER_NAME:1 ends here

        // [[file:Scanner.org::*matching][matching:1]]
        Pattern TOKEN =
            Pattern.compile(String.join("|",
                                        COMMENT,
                                       // LITERAL
                                        IDENTIFIER_NAME,
                                        LINE_TERMINATOR,
                                        PUNCTUATOR
                                        ));
        Matcher matcher = TOKEN.matcher(code);
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
