package com.noelcurtis.allegro;

import java.util.Map;

public class LineProcessor
{
    Reader _reader;

    public LineProcessor(String filePath)
    {
        _reader = new Reader(filePath);
    }

    public void startProcessing()
    {
        _reader.run();
        String line = _reader.getLine();
        while (line != null)
        {
            WordPairer wordPairer = new WordPairer(line);
            // kick it off
            Mediator.getExecutorService().execute(wordPairer);
            line = _reader.getLine();
        }
        printOutput();
    }

    public void printOutput()
    {
        for (Map.Entry<WordPair, Integer> entry : Mediator.WordPairsCount.entrySet())
        {
            if (entry.getValue() >= Mediator.RecurranceThreshold)
            {
                System.out.println(entry.getKey().format() + "\n");
            }
        }
    }
}
