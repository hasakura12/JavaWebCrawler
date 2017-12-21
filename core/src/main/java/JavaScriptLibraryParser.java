import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by asahi02 on 2017-12-20.
 */
public class JavaScriptLibraryParser {
    private static final String SRC_NAME = "src";

    public static ConcurrentHashMap<String, Integer> parseFromUrl(String url) throws IOException {
        return parseHelper(Jsoup.connect(url).get());
    }

    public static ConcurrentHashMap<String, Integer> parseFromFile(String fileName) throws IOException {
        return parseHelper(Jsoup.parse(new File(fileName), "ISO-8859-1"));
    }


    // guaranteed to have unique items
    static ConcurrentHashMap<String, Integer> parseHelper(Document htmlFile) {
        ConcurrentHashMap<String, Integer> jsLibraries = new ConcurrentHashMap<>();
        System.out.println("JS library file name parsed:");

        // find script tag with type attribute equal to text/javascript
        for (Element element : htmlFile.select("script[type*=text/javascript]")) {
            String jsLibraryUrl = element.attr(SRC_NAME);

            if (jsLibraryUrl != null && jsLibraryUrl.equals("") ) {
                continue;
            }

            // increment library occurrence
            jsLibraries.put(jsLibraryUrl, jsLibraries.get(jsLibraryUrl) == null ? 1 :  jsLibraries.get(jsLibraryUrl) + 1);
            System.out.println("    -" + jsLibraryUrl);
        }

        return jsLibraries;
    }
}
