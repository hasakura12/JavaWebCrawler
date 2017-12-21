import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.*;
import java.util.*;

/**
 * Created by asahi02 on 2017-12-20.
 */
public class Http {
    private static final String URL_GOOGLE_SEARCH = "http://www.google.com/search?q=";
    private static final String CHARSET = "UTF-8";
    private static final String USER_AGENT = "hisashi";

    public static HashSet<String> getMainResultFromGoogle(String query) throws IOException {
        HashSet<String> mainResultLinks = new HashSet<>();

        Elements links = Jsoup.connect(URL_GOOGLE_SEARCH +
                URLEncoder.encode(query, CHARSET)).userAgent(USER_AGENT).get().select(".g>.r>a");

        for (Element link : links) {
            String title = link.text();
            String url = link.absUrl("href"); // Google returns URLs in format "http://www.google.com/url?q=<url>&sa=U&ei=<someKey>".
            url = URLDecoder.decode(url.substring(url.indexOf('=') + 1, url.indexOf('&')), CHARSET);

            if (!url.startsWith("http")) {
                continue; // Ads/news/etc.
            }

            System.out.println("Title: " + title + "(" + url + ")");
            mainResultLinks.add(url);
        }

        return mainResultLinks;
    }
}
