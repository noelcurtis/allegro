package com.noelcurtis.allegro;

import org.junit.Test;
import java.io.File;
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
    public void wordPairs()
    {
        String testString = "Radiohead,Pulp,Morrissey,Delays,Stereophonics,Blur,Suede,Sleeper,The La's,Super Furry Animals";
        WordPairer p = new WordPairer(testString);
        p.run();
        assert (p.getWordPairs().size() == 45);
    }

    @Test
    public void anotherWordPairs()
    {
        String testString = "1,2,3,4,5";
        WordPairer p = new WordPairer(testString);
        p.run();
        assert (p.getWordPairs().size() == 10);
    }

    @Test
    public void kProcessFile() throws Exception
    {
        String filePath = new File(".").getCanonicalPath() + "/src/test/java/com/noelcurtis/allegro/Artist_lists_small.txt";
        KReader kReader = new KReader(filePath);
        kReader.processFile();
        kReader.printOutput();
    }

    @Test
    public void kConcurrentProcessFile() throws Exception
    {
        String filePath = new File(".").getCanonicalPath() + "/src/test/java/com/noelcurtis/allegro/Artist_lists_small.txt";
        ConcurrentKReader kReader = new ConcurrentKReader(filePath);
        kReader.processFile();
        while (!kReader.isComplete())
        {
            // Wait till all the threads complete
        }
        kReader.printOutput();
    }
}
