package org.example;

import org.example.people.Reader;
import org.example.people.Writer;

import java.util.Arrays;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicBoolean;

public class Main {
    public static void main(String[] args) {

        int amountOfReaders = 10, amountOfWriters = 3;
        if (args.length != 0) {
            try {
                amountOfReaders = Integer.parseInt(args[0]);
                amountOfWriters = Integer.parseInt(args[1]);
            }
            catch (NumberFormatException e) {
                System.err.println("Argument must be an integer");
                System.exit(0);
            }
        }

        Reader[] readers = new Reader[amountOfReaders];
        Writer[] writers = new Writer[amountOfWriters];
        Semaphore semaphore = new Semaphore(5);
        Semaphore semaphore2 = new Semaphore(1);
        AtomicBoolean readersInLibrary = new AtomicBoolean(false);
        Library library = new Library();
        Resource resource = new Resource(semaphore2, readersInLibrary, library);


        for (int i = 0; i < amountOfReaders; i++) {
            readers[i] = new Reader(semaphore,resource, library, i);
        }
        for (int i = 0; i < amountOfWriters; i++) {
            writers[i] = new Writer(semaphore2, library, i);
        }


        Arrays.stream(writers)
                .forEach(Thread::start);
        Arrays.stream(readers)
                .forEach(Thread::start);
    }
}