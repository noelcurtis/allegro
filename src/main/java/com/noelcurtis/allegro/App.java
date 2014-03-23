package com.noelcurtis.allegro;

import java.io.File;

public class App
{
    public static void main(String[] args)
    {
        try
        {
            String filePath = new File(".").getCanonicalPath() + "/src/test/java/com/noelcurtis/allegro/Artist_lists_small.txt";
            if (args != null && args.length != 0)
            {
                filePath = args[0];
            }
            // Initialize the reader
            ConcurrentKReader kReader = new ConcurrentKReader(filePath);
            // Process the files
            kReader.processFile();
            while (!kReader.isComplete())
            {
                // Wait till all the threads complete
            }
            // print the output
            kReader.printOutput();
        }
        catch (Exception ex)
        {
            System.out.println("Error: " + ex.toString());
        }
    }
}
