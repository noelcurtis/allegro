## Usage
*   You can run the program using `java -jar Allegro-1.0.jar Artist_lists_small.txt`
*   The jar can be found in the build folder `/build/Allegro-1.0.jar`

## Build
*   The project is built with Maven using `mvn clean install`

## Method
* The solution uses a divide and conquer approach to count artist pairs in linear time.
* A HashTable is used to keep track of Artist pairs.
* The whole list of lines is split into sections which are counted concurrently.
* The results from counting each section are then aggregated to get the final result.
* For example
    *   Consider a file with a 1000 lines
    *   The file is read into sections of n lines per section
    *   Once a section is ready it is handed of to get counted for Artist pairs on a separate thread
    *   After the section is counted its results are aggregated with the other sections
    *   This is done till there are no more lines
* For the given file the program takes `~100 milliseconds` to complete on a 2.3GZ Intel Core i7 with 6GB of memory for the JVM

## Analysis
*   Pairing
    *   For each section of `k` lines and `n` words per line pairing takes `O(k*(n^2))`
*   Counting
    *   For each section if there were `p` pairs created then counting similar pairs only takes `O(p)` in worst case if all the pairs occur only once as
    we are using a HashTable for lookup which has lookup of `O(1)`
    *   To aggregate the count it takes another `O(p)` in worst case

## Serialized Version
*   There is also a serialized version of the counter `KReader`, you can see how this compares by running the test cases.

