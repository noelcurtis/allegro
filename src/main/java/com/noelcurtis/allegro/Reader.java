package com.noelcurtis.allegro;

import java.io.*;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Reads a file and builds a Queue of lines
 */
public class Reader implements Runnable
{
    public static final String DONE_MESSAGE = "EOF";
    private final String _filePath;
    final ConcurrentLinkedQueue<String> _linesToProcess;

    public Reader(String filePath)
    {
        _filePath = filePath;
        _linesToProcess = new ConcurrentLinkedQueue<String>();
    }

    @Override
    public void run()
    {
        try
        {
            processFile();
        }
        catch (Exception ex)
        {
            System.out.println("Error processing file: " + ex.getMessage());
        }
    }

    public String getLine()
    {
        return _linesToProcess.poll();
    }

    /**
     * Reads lines from a file into a Queue, and optionally add them to an EventBus
     * This is done on a single thread, multiple threads are not used to read from disk!
     * @throws Exception
     */
    public void processFile() throws Exception
    {
        // Get the file URL
        File file = new File(_filePath);
        if (!file.exists())
        {
            throw new Exception("File not found at path:" + _filePath);
        }
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF8"));
        // Read lines into a queue with a buffered reader
        for (String line; (line = br.readLine()) != null; )
        {
            _linesToProcess.add(line);
        }
        br.close();
    }
}
