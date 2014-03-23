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

    /**
     * Use to process the file for cardinality calculation
     * @throws Exception
     */
    public void processFile() throws Exception
    {
        _lineProcessors = new LinkedList<LineProcessor>(); // using a linked list for mostly writes
        long currentTime = System.currentTimeMillis();
        _pairsCount = new HashMap<WordPair, Integer>();
        // Get the file URL
        File file = new File(_filePath);
        if (!file.exists())
        {
            throw new Exception("File not found at path:" + _filePath);
        }
        // create a reader to read the file
        BufferedReader br  = null;
        try
        {
            // Read lines and process and process them by the SplittingFactor
            br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF8"));
            int count = 0;
            String[] lines = new String[Mediator.SplittingFactor];
            for (String line; (line = br.readLine()) != null; )
            {
                // Once the SplittingFactor is reached, kick of a job to count word pairs
                if (count == Mediator.SplittingFactor)
                {
                    LineProcessor lineProcessor = new LineProcessor(lines, _pairsCount);
                    // start off and async line processor
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
                // start off and async line processor
                Mediator.getExecutorService().submit(lineProcessor);
            }
            br.close(); // close the buffer
            System.out.println("Time Taken: " + (System.currentTimeMillis() - currentTime) + "ms");
        }
        catch (Exception ex)
        {
            throw ex;
        }
        finally
        {
            if (br != null)
            {
                br.close(); // release the file handle on exception
            }
        }
    }

    /**
     * Use to print the output
     */
    public void printOutput(boolean debug)
    {
        System.out.println("\nOutput:");
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

    /**
     * Use to check if all the line processors have completed
     * @return
     */
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
