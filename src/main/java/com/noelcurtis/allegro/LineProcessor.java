package com.noelcurtis.allegro;

import java.util.*;

public class LineProcessor
{
    final Reader _reader;
    List<WordPairer> _wordPairers;
    private HashMap<WordPair, Integer> _wordPairsCount;

    public LineProcessor(String filePath)
    {
        _reader = new Reader(filePath);
    }

    /**
     * Use to process a file and collect word pairs
     * @throws Exception
     */
    public void startProcessing() throws Exception
    {
        long start = System.currentTimeMillis();
        // initialize
        _wordPairers = new LinkedList<WordPairer>();
        // start reading the file
        _reader.run();
        String line = _reader.getLine();
        while (line != null)
        {
            // Create a word Pairer and kick it off
            final WordPairer wordPairer = new WordPairer(line);
            Mediator.getExecutorService().execute(wordPairer);
            //wordPairer.run();
            _wordPairers.add(wordPairer);
            line = _reader.getLine();
        }
        System.out.println("Pairing: " + (System.currentTimeMillis() - start) + "ms");
        guardCompletion();
        System.out.println("Compiling Results: " + (System.currentTimeMillis() - start) + "ms");
        printOutput();
    }

    /**
     * Use to guard completion till all Pairers are complete
     */
    private synchronized void guardCompletion()
    {
        while (!isComplete())
        {}
        complieResults();
    }

    /**
     * Use to collect results
     */
    private void complieResults()
    {
        _wordPairsCount = new HashMap<WordPair, Integer>();
        for(WordPairer p : _wordPairers)
        {
            final Set<WordPair> pairs = p.getWordPairs();
            for (WordPair pair : pairs)
            {
                if (_wordPairsCount.containsKey(pair))
                {
                    _wordPairsCount.put(pair, _wordPairsCount.get(pair) + 1);
                }
                else
                {
                    _wordPairsCount.put(pair, 1);
                }
            }
        }
    }

    /**
     * Use to check if processing is complete
     * @return
     */
    private boolean isComplete()
    {
        boolean complete = true;
        if (_wordPairers == null || _wordPairers.size() == 0) return complete;
        for (WordPairer p : _wordPairers)
        {
            if(!p.isComplete())
            {
                complete = false;
            }
        }
        return complete;
    }

    /**
     * Use to print the output
     */
    private void printOutput()
    {
        for (Map.Entry<WordPair, Integer> entry : _wordPairsCount.entrySet())
        {
            if (entry.getValue() >= Mediator.RecurranceThreshold)
            {
                System.out.println(entry.getKey().format() + "-> " + entry.getValue());
            }
        }
    }
}
