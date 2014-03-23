package com.noelcurtis.allegro;

import java.net.URL;

public class App
{
    public static void main(String[] args)
    {
        try
        {
            System.out.println("Starting..");
            URL url = App.class.getClassLoader().getResource("Artist_lists_small.txt");
            String filePath = url.getPath();
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
            kReader.printOutput(false);
        }
        catch (Exception ex)
        {
            System.out.println("Error: " + ex.toString());
        }
    }
}
