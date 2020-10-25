package compilador;

import org.junit.Test;
import static org.junit.Assert.assertTrue;
import java.util.Arrays;

public class TestScanner {
    @Test
    public void number() {
        String params[] = {"44 + 856", "60", ""};
        assert(tokenize("44 + 845") == {"44", "845"})
    }
}
