import java.nio.file.Path;
import java.util.regex.Pattern;
import java.nio.file.Paths;
import java.io.IOException;
import java.nio.file.Files;
import java.util.regex.Matcher;


// [[file:numbers.org][No heading:1]]
class Arithmetic {
    public enum keyword = {String, Integer, ing, Float Double};

    public void tokenzine(String code) {
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(code);
        System.out.println(matcher.group());
    }

    public static void main(String[] args) {
        String text;
        try {
            Path path = Paths.get(args[1]);
            text = String.join("\n", Files.readAllLines(path));
        } catch (IOException e) {
            System.out.println("Error " + e.getMessage());
            e.printStackTrace();
            return;
        }

    }

    private void test() {

     }
}
// No heading:1 ends here
