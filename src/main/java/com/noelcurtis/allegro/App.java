package com.noelcurtis.allegro;

public class App
{
    public static void main(String[] args)
    {
        try
        {
            if (args == null || args.length == 0)
            {
                System.out.print("Usage `java -jar allegro.jar yourfile.txt`");
                System.exit(1);
            }
            String filePath = args[0];
            work(filePath);
            System.exit(0);
        }
        catch (Exception ex)
        {
            System.out.println("Error: " + ex.toString());
        }
    }

    public static void work(String filePath) throws Exception
    {
        System.out.println("Starting..");
        // Initialize the reader
        ConcurrentKReader kReader = new ConcurrentKReader(filePath);
        // Process the files
        kReader.processFile();
        while (!kReader.isComplete())
        {
            // Wait till all the threads complete
        }
        // print the output
        System.out.println("Done!");
        kReader.printOutput(false);
    }
}
