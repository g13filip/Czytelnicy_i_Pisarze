package org.example;

import org.example.people.Man;
import org.example.people.Reader;
import org.example.people.Writer;

import java.util.ArrayList;
import java.util.List;

public class Library {
    List<Man> queue = new ArrayList<>();
    List<Man> inLibrary = new ArrayList<>();
    private static final String STOP = "------------------------";

    int countOfReaders = 0;
    int countOfWriters = 0;

    public synchronized boolean isWriterInQueue() {
        if (!queue.isEmpty()) {
            return queue.get(0).toString().contains("Writer");}
        else {return false;}
    }
    public boolean isWriterInLibrary() {
        if (!inLibrary.isEmpty()) {
            return inLibrary.get(0).toString().contains("Writer");}
        else {return false;}
    }

    public List<Man> getQueue() {
        return queue;
    }

    public List<Man> getInLibrary() {
        return inLibrary;
    }

    public synchronized void message(){
        System.out.println(countOfReaders + " readers waiting and " + countOfWriters + " writers waiting");
        System.out.println("People in queue: " + queue);
        System.out.println("People in library: " + inLibrary);
        System.out.println(STOP);
    }

    public synchronized void requestRead(Reader reader) {
        countOfReaders++;
        reader.waitingMessage();
        queue.add(reader);
    }

    public synchronized void startReading(Reader reader) {
        countOfReaders--;
        queue.remove(reader);
        inLibrary.add(reader);
        System.out.println(STOP);
        reader.actionMessage();
        message();
    }

    public synchronized void stopReading(Reader reader) {
        System.out.println(STOP);
        reader.finishMessage();
        inLibrary.remove(reader);
        message();
    }

    public synchronized void requestWrite(Writer writer) {
        countOfWriters++;
        writer.waitingMessage();
        queue.add(writer);
    }

    public synchronized void startWriting(Writer writer) {
        countOfWriters--;
        queue.remove(writer);
        inLibrary.add(writer);
        writer.actionMessage();
        message();
    }

    public synchronized void stopWriting(Writer writer) {
        writer.finishMessage();
        inLibrary.remove(writer);
        message();
    }
}
