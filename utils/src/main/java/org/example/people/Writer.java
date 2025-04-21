package org.example.people;

import org.example.Library;

import java.util.concurrent.Semaphore;

public class Writer extends Thread implements Man{
    Semaphore isBusy;
    Library library;
    int id;
    private final String name;

    public Writer(Semaphore semaphore, Library library, int id){
        this.isBusy = semaphore;
        this.library = library;
        this.id = id;
        this.name = "Writer " + id;
    }

    @Override
    public void waitingMessage(){
        System.out.println(name + " is waiting in queue");
    }
    @Override
    public void actionMessage(){
        System.out.println(name + " is writing");
    }
    @Override
    public void finishMessage(){
        System.out.println(name + " finished writing");
    }
    @Override
    public String toString(){
        return name;
    }

    @Override
    public void run(){
        try{
            while(true){
                library.requestWrite(this);
                isBusy.acquire();

                library.startWriting(this);
                sleep(1000);

                library.stopWriting(this);
                isBusy.release();
                sleep(2000);
            }}
        catch(InterruptedException e){ System.out.println( name + " interrupted");
                                        Thread.currentThread().interrupt();}
    }
}
