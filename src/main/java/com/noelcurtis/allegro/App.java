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
            // initialize a reader
            KReader kReader = new KReader(filePath);
            // process file
            kReader.processFile();
            // print output
            kReader.printOutput();
        }
        catch (Exception ex)
        {
            System.out.println("Error: " + ex.toString());
        }
    }
}
