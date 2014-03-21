package com.noelcurtis.allegro;

import com.google.common.eventbus.EventBus;
import com.google.gson.Gson;
import org.junit.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Unit test for simple App.
 */
public class AppTest
{

    @Test
    public void wordPairsWork()
    {
        ConcurrentHashMap<WordPair, Integer> values = new ConcurrentHashMap<WordPair, Integer>();

        WordPair aPair = new WordPair("The Glenlivet", "Ben Nevis");
        WordPair bPair = new WordPair("Ben Nevis", "The Glenlivet");
        WordPair cPair = new WordPair("Ardmore", "The Glenlivet");

        values.put(aPair, 1);
        values.put(bPair, 2);
        values.put(cPair, 3);

        assert (values.containsKey(aPair));
        assert (aPair.equals(bPair));
        assert (aPair.hashCode() == bPair.hashCode());
        assert (!aPair.equals(cPair));
        assert (aPair.hashCode() != cPair.hashCode());
        assert (values.get(aPair) == 2);
        assert (values.get(cPair) == 3);
    }

    @Test
    public void wordPairsTest()
    {
        String testString = "Radiohead,Pulp,Morrissey,Delays,Stereophonics,Blur,Suede,Sleeper,The La's,Super Furry Animals";
        ConcurrentHashMap<WordPair, Integer> _wordPairsCount = new ConcurrentHashMap<WordPair, Integer>();
        WordPairer p = new WordPairer(testString);
        p.run();
        assert (_wordPairsCount.size() == 45);
    }

    @Test
    public void anotherWordPairsTest()
    {
        String testString = "1,2,3,4,5";
        final ConcurrentHashMap<WordPair, Integer> _wordPairsCount = new ConcurrentHashMap<WordPair, Integer>();
        WordPairer p = new WordPairer(testString);
        p.run();

        assert (_wordPairsCount.size() == 10);
    }

    @Test
    public void processFile() throws Exception
    {
        String filePath = new File(".").getCanonicalPath() + "/src/test/java/com/noelcurtis/allegro/Artist_lists_small.txt";
        LineProcessor lp = new LineProcessor(filePath);
        lp.startProcessing();
    }
}
