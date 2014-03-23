package com.noelcurtis.allegro;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ConcurrentKReader
{
    private final String _filePath;
    private HashMap<WordPair, Integer> _pairsCount;
    private List<LineProcessor> _lineProcessors;

    public ConcurrentKReader(String filePath)
    {
        _filePath = filePath;
    }

    public void processFile() throws Exception
    {
        _lineProcessors = new LinkedList<LineProcessor>();
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
        int count = 0;
        String[] lines = new String[Mediator.SplittingFactor];
        for (String line; (line = br.readLine()) != null; )
        {
            if (count == Mediator.SplittingFactor)
            {
                LineProcessor lineProcessor = new LineProcessor(lines, _pairsCount);
                Mediator.getExecutorService().submit(lineProcessor);
                _lineProcessors.add(lineProcessor);
                count = 0; // reset the count
                lines = new String[Mediator.SplittingFactor]; // initialize the array
            }
            lines[count] = line;
            count++;
        }
        // check if there are some lines still remaining
        if (lines.length > 0)
        {
            LineProcessor lineProcessor = new LineProcessor(lines, _pairsCount);
            Mediator.getExecutorService().submit(lineProcessor);
        }
        br.close();
        System.out.println("Time Taken: " + (System.currentTimeMillis() - currentTime) + "ms");
    }

    /**
     * Use to print the output
     */
    public void printOutput()
    {
        for (Map.Entry<WordPair, Integer> entry : _pairsCount.entrySet())
        {
            if (entry.getValue() >= Mediator.RecurrenceThreshold)
            {
                System.out.println(entry.getKey().format() + "-> " + entry.getValue());
            }
        }
    }

    public boolean isComplete()
    {
        boolean isComplete = true;
        for(LineProcessor lp : _lineProcessors)
        {
            if (!lp.isComplete())
            {
                 isComplete = false;
            }
        }
        return isComplete;
    }
}
