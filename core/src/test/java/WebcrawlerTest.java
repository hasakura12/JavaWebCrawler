import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by asahi02 on 2017-12-20.
 */
public class WebcrawlerTest {
    private static final String SEARCH_QUERIES = "test";

    @Mock
    private Webcrawler mockedWebcrawler = mock(Webcrawler.class);

    @Test
    public void getTopNLibrariesWithMockedClawler() throws Exception {
        when(mockedWebcrawler.getTopNLibraries(anyString())).thenReturn(mockedResultMap());
        assert(mockedWebcrawler != null);

        ConcurrentHashMap<String, Integer> topLibrariesMap =
                (ConcurrentHashMap<String, Integer>) mockedWebcrawler.getTopNLibraries(SEARCH_QUERIES);
        verify(mockedWebcrawler).getTopNLibraries(anyString());
        assert(topLibrariesMap.equals(mockedResultMap()));
    }

    @Test
    public void getTopNLibraries() throws Exception {
        Webcrawler webcrawler = new Webcrawler();
        Map<String, Integer> orderedMap = webcrawler.getTopNLibraries("how to walk");
        assert(orderedMap.size() > 0); // TODO: BUG HERE, not returning entire list in order
    }

    @Test
    public void sortByValuesInDescendingOrder() throws Exception {
        ConcurrentHashMap<String, Integer> unorderedMap = mockedUnorderedMap();
        Map<String, Integer> orderedMap = Webcrawler.sortByValuesInDescendingOrder(unorderedMap);
        assert(orderedMap.size() == 5);

    }

    private ConcurrentHashMap<String, Integer> mockedResultMap() {
        ConcurrentHashMap<String, Integer> libraryCounts = new ConcurrentHashMap<>();
        libraryCounts.put("library1", 5);
        libraryCounts.put("library2", 4);
        libraryCounts.put("library3", 3);
        libraryCounts.put("library4", 2);
        libraryCounts.put("library5", 1);
        return libraryCounts;
    }

    private ConcurrentHashMap<String, Integer> mockedUnorderedMap() {
        ConcurrentHashMap<String, Integer> libraryCounts = new ConcurrentHashMap<>();
        libraryCounts.put("library1", 1);
        libraryCounts.put("library2", 4);
        libraryCounts.put("library3", 2);
        libraryCounts.put("library4", 8);
        libraryCounts.put("library5", 0);
        return libraryCounts;
    }
}