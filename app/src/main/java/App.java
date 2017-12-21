import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by asahi02 on 2017-12-20.
 */

// TODO: move HTTP connection to background thread
// TODO: sorting list in descending order doesn't work yet
public class App {
    private static final int TOP_N = 5;

    public static void main(String[] args) {
        String queries = getInputFromUser();

        Webcrawler webcrawler = new Webcrawler();
        Map orderedMap = null;
        try {
            orderedMap = webcrawler.getTopNLibraries(queries);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (orderedMap != null) {
            printTopNLibraries(TOP_N, orderedMap);
        }
    }

    private static void printTopNLibraries(int topN, Map<String, Integer> libraryCounts) {
        System.out.println("##### RESULT ####");
        System.out.println("Top " + topN + " most used libraries:" + libraryCounts);
    }

    private static String getInputFromUser() {
        String queries = null;
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(System.in));

            System.out.print("Enter your search keywords and hit enter: ");
            queries = bufferedReader.readLine();

            System.out.println(String.format("Search with queries=%s", queries));
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return queries;
    }
}
