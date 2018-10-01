import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import static java.lang.System.out;

/*README
program from the algoritm book
Takes the first 100 words and finds how much of each word is used. Uses linear probing
 */

public class Lab3Question7Point2
{
    public class LinearProbingHashST<Key, Value>
    {
        private int N; // number of key-value pairs in the table
        private int M; // size of linear-probing table
        private Key[] keys; // the keys
        private Value[] vals; // the values

        public LinearProbingHashST(int i)
        {
            M = i;
            keys = (Key[]) new Object[i];
            vals = (Value[]) new Object[i];
        }

        private int hash(Key key)
        { return (key.hashCode() & 0x7fffffff) % M; }

        private void resize(int cap)  // See page 474.
        {
            //out.println("sugar");
            LinearProbingHashST<Key, Value> t;
            t = new LinearProbingHashST<Key, Value>(cap);
            for (int i = 0; i < M; i++)
                if (keys[i] != null)
                    t.put(keys[i], vals[i]);
            keys = t.keys;
            vals = t.vals;
            M = t.M;
        }

        public void put(Key key, Value val)
        {
            if (N >= M/2) resize(2*M); // double M (see text)
            int i;
            for (i = hash(key); keys[i] != null; i = (i + 1) % M)
                if (keys[i].equals(key)) { vals[i] = val; return; }
            keys[i] = key;
            vals[i] = val;
            N++;
            //out.println("coconut");
        }

        public Value get(Key key)
        {
            for (int i = hash(key); keys[i] != null; i = (i + 1) % M)
                if (keys[i].equals(key))
                    return vals[i];
            return null;
        }

        public boolean contains(Key key)
        {
            Value ans = get(key);
            if (ans == null)
            {
                return (false);
            }
            else
            {
                return (true);
            }
        }
    }

    public static void main(String[] args) throws IOException
    {
        long startTime = System.nanoTime(); //start program timer
        Scanner in = new Scanner(new File("TextModified.txt"));
        Lab3Question7Point2 lab = new Lab3Question7Point2();
        Lab3Question7Point2.LinearProbingHashST<String, Integer> LBHash =
                lab.new LinearProbingHashST<String, Integer>(100);  //starting up linear probing has table
        String[] filteredWords = new String[100];
        int counter = 0;
        long startAlgoTime = System.nanoTime();  //start algo timer

        while (counter < 100)  //getting the first 100 unique words
        {
            String line = in.nextLine();  //get the next sentence
            String[] lineArr = line.split("\\W+");  //spit into words

            for (int i = 0; i < lineArr.length  && counter < 100; i++)  //go through words
            {
                if (!LBHash.contains(lineArr[i]))  //if new add it in with val 1
                {
                    LBHash.put(lineArr[i], 1);
                    filteredWords[counter] = lineArr[i];
                    counter++;
                    //out.println("success");
                    //out.println(counter);
                }
                else
                {
                    int val = LBHash.get(lineArr[i]);  //if old just add 1 more to the value
                    LBHash.put(lineArr[i], val + 1);
                    //out.println("not success");
                    //out.println(counter);
                }
            }
            //out.println(counter);
        }

        //out.println(counter);
        //out.println("onion");
        long outputTime = System.nanoTime();
        long outputTimeMill = System.currentTimeMillis();
        String max = "";  //add this as a benchmark
        LBHash.put(max, 0);

        for (int x = 0; x < counter; x++)  //for all the input check the highest amount of entry
        {
            //System.out.println(filteredWords[x] + ' ' + st.get(filteredWords[x]));
            if (LBHash.get(filteredWords[x]) > LBHash.get(max))  //if bigger than
            {
                max = filteredWords[x];
            }
        }

        System.out.println(max + " " + LBHash.get(max));
        long endTime = System.nanoTime();
        long endTimeMill = System.currentTimeMillis();
        long time = endTime - startTime;
        long time1 = endTime - startAlgoTime;
        long time2 = endTime - outputTime;
        long time3 = endTimeMill - outputTimeMill;
        System.out.printf("Program time: %d ns\n", time);
        System.out.printf("Program algorithm time: %d ns\n", time1);
        System.out.printf("Program algorithm output time: %d ns\n", time2);
        System.out.printf("Program algorithm output time: %d ms", time3);
    }
}
