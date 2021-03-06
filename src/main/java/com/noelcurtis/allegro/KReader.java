package com.noelcurtis.allegro;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class KReader
{
    private final String _filePath;
    private HashMap<WordPair, Integer> _pairsCount;

    public KReader(String filePath)
    {
        _filePath = filePath;
    }

    public void processFile() throws Exception
    {
        long currentTime = System.currentTimeMillis();
        _pairsCount = new HashMap<WordPair, Integer>();
        // Get the file URL
        File file = new File(_filePath);
        if (!file.exists())
        {
            throw new Exception("File not found at path:" + _filePath);
        }
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF8"));
        // Read lines and process then one at a time
        for (String line; (line = br.readLine()) != null; )
        {
            processLine(line);
        }
        br.close();
        System.out.println("Time Taken: " + (System.currentTimeMillis() - currentTime) + "ms");
    }

    private void processLine(String line)
    {
        WordPairer pairer = new WordPairer(line);
        pairer.run();
        Set<WordPair> wordPairs = pairer.getWordPairs();
        for (WordPair p : wordPairs)
        {
            if (!_pairsCount.containsKey(p))
            {
                _pairsCount.put(p, 1);
            }
            else
            {
                _pairsCount.put(p, _pairsCount.get(p) + 1);
            }
        }
    }

    /**
     * Use to print the output
     */
    public void printOutput(boolean debug)
    {
        for (Map.Entry<WordPair, Integer> entry : _pairsCount.entrySet())
        {
            if (entry.getValue() >= Mediator.RecurrenceThreshold)
            {
                if (debug)
                {
                    System.out.println(entry.getKey().format() + "-> " + entry.getValue());
                }
                else
                {
                    System.out.println(entry.getKey().format());
                }

            }
        }
    }
}
