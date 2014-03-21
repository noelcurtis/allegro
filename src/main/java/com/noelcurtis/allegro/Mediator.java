package com.noelcurtis.allegro;

import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Mediator
{
    // This is an ExecutorService used to mediate on a
    // thread pool and execute tasks from a Queue of lines to process
    private static ExecutorService executorService;
    // This is the number to threads that will be made available in the pool
    // The threads will be used to execute WordPairer on lines
    private static int threadCount = Runtime.getRuntime().availableProcessors();

    public static HashMap<WordPair, Integer> WordPairsCount = new HashMap<WordPair, Integer>();

    /**
     * Helper to get the Executor Service to submit jobs to
     * @return
     */
    public static ExecutorService getExecutorService()
    {
        if (executorService == null)
        {
            executorService = Executors.newFixedThreadPool(threadCount);
        }
        return executorService;
    }
}
