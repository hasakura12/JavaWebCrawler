import org.jsoup.Jsoup;
import org.junit.Test;

import java.io.File;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import static org.junit.Assert.*;

/**
 * Created by asahi02 on 2017-12-20.
 */
public class JavaScriptLibraryParserTest {
    private static final String URL_YAHOO = "https://ca.yahoo.com";
    private static final String YAHOO_FILE = "./src/test/resources/yahoo.html";
    private static final String LOGIN_FILE = "./src/test/resources/login.html";

    @Test
    public void parseFromUrl() throws Exception {
        ConcurrentHashMap<String, Integer> jsLibraries = JavaScriptLibraryParser.parseFromUrl(URL_YAHOO);
        assert(jsLibraries.containsKey("https://mbp.yimg.com/sy/rq/darla/3-0-7/js/g-r-min.js"));
        assert(jsLibraries.containsKey("https://mbp.yimg.com/sy/zz/combo?yui:/3.18.0/yui/yui-min.js"));
        assert(jsLibraries.size() == 11);
    }

    @Test
    public void parseFromFile() throws Exception {
        ConcurrentHashMap<String, Integer> jsLibraries = JavaScriptLibraryParser.parseFromFile(LOGIN_FILE);
        assert(jsLibraries.containsKey("http://localhost/libraries/javascript/jquery_1.6.4.min.js"));
        assert(jsLibraries.containsKey("http://localhost/libraries/javascript/test.js"));
        assert(jsLibraries.size() == 3);
    }

    @Test
    public void parseHelper() throws Exception {
        ConcurrentHashMap<String, Integer> jsLibraries = JavaScriptLibraryParser.parseHelper(Jsoup.parse(new File(LOGIN_FILE), "ISO-8859-1"));
        assert(jsLibraries.containsKey("http://localhost/libraries/javascript/jquery_1.6.4.min.js"));
        assert(jsLibraries.containsKey("http://localhost/libraries/javascript/test.js"));
        assert(jsLibraries.size() == 3);
    }

    @Test
    public void parseFromUrls() throws Exception {
        ConcurrentHashMap<String, Integer> resultingUniqueJSLibraryCountMap = JavaScriptLibraryParser.parseFromUrl(URL_YAHOO);
        assert(resultingUniqueJSLibraryCountMap.containsKey("https://mbp.yimg.com/sy/rq/darla/3-0-7/js/g-r-min.js"));
        assert(resultingUniqueJSLibraryCountMap.containsKey("https://mbp.yimg.com/sy/zz/combo?yui:/3.18.0/yui/yui-min.js"));

        ConcurrentHashMap<String, Integer> jsLibraries2 = JavaScriptLibraryParser.parseFromFile(LOGIN_FILE);
        jsLibraries2.forEach((k, v) -> resultingUniqueJSLibraryCountMap.merge(k, v, Integer::sum));

        assert(resultingUniqueJSLibraryCountMap.containsKey("https://mbp.yimg.com/sy/rq/darla/3-0-7/js/g-r-min.js"));
        assert(resultingUniqueJSLibraryCountMap.containsKey("https://mbp.yimg.com/sy/zz/combo?yui:/3.18.0/yui/yui-min.js"));
        assert(resultingUniqueJSLibraryCountMap.containsKey("http://localhost/libraries/javascript/jquery_1.6.4.min.js"));
        assert(resultingUniqueJSLibraryCountMap.containsKey("http://localhost/libraries/javascript/test.js"));

        // check if there was no duplicates added
        ConcurrentHashMap<String, Integer> jsLibraries3 = JavaScriptLibraryParser.parseFromFile(LOGIN_FILE);
        jsLibraries3.forEach((k, v) -> resultingUniqueJSLibraryCountMap.merge(k, v, Integer::sum));
        assert(resultingUniqueJSLibraryCountMap.size() == 13);


    }
}