import org.junit.Test;

import java.util.HashSet;
import java.util.List;

/**
 * Created by asahi02 on 2017-12-20.
 */
public class HttpTest {
    @Test
    public void getWithOneQuery() throws Exception {
        HashSet<String> mainResultLinks = Http.getMainResultFromGoogle("stackoverflow");
        assert(mainResultLinks.contains("https://stackoverflow.com/?"));
        assert(mainResultLinks.contains("https://en.wikipedia.org/wiki/Stack_overflow"));
        assert(mainResultLinks.contains("https://www.stackoverflowbusiness.com/"));
        assert(mainResultLinks.size() > 0);
    }

    @Test
    public void getWithOneQuery2() throws Exception {
        HashSet<String> mainResultLinks = Http.getMainResultFromGoogle("how to walk");
        assert(mainResultLinks.contains("https://www.wikihow.com/Walk"));
        assert(mainResultLinks.contains("https://www.wikihow.com/Walk-Properly"));
        assert(mainResultLinks.size() > 0);
    }
}