package wallet.idhub.com.etherscan;

import org.junit.Test;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        LocalDateTime localDateTime = LocalDateTime.ofEpochSecond(Long.valueOf("1567577550"), 0, ZoneOffset.UTC);

        System.out.println(localDateTime.toString() );
    }
}