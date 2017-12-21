import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by asahi02 on 2017-12-20.
 */
public class Webcrawler {
    public Map<String, Integer> getTopNLibraries(String queries) throws IOException {
        HashSet<String> mainResultLinks = Http.getMainResultFromGoogle(queries);
        ConcurrentHashMap<String, Integer> resultingUniqueJSLibraryCountMap = new ConcurrentHashMap<>();

        for (String url : mainResultLinks) {
            ConcurrentHashMap<String, Integer> jsLibraries = JavaScriptLibraryParser.parseFromUrl(url); // keep adding unique links
            jsLibraries.forEach((k, v) -> resultingUniqueJSLibraryCountMap.merge(k, v, Integer::sum));
        }

        // TODO: this sorting method has a BUG, doesn't return entire list
        //return sortByValuesInDescendingOrder(resultingUniqueJSLibraryCountMap);

        return resultingUniqueJSLibraryCountMap;
    }

    static Map<String, Integer> sortByValuesInDescendingOrder(ConcurrentHashMap<String, Integer> unorderedMap) {
        DescendingComparator descendingComparator = new DescendingComparator(unorderedMap);
        Map<String, Integer> orderedMap = new TreeMap(descendingComparator);
        orderedMap.putAll(unorderedMap);
        return orderedMap;
    }

    static class DescendingComparator implements Comparator {
        Map map;

        public DescendingComparator(Map map) {
            this.map = map;
        }

        public int compare(Object o1, Object o2) {
            return ((Integer) map.get(o2)).compareTo((Integer) map.get(o1));
        }
    }
}
