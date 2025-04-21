package org.example.people;

import org.example.Library;
import org.example.Resource;

import java.util.concurrent.Semaphore;

public class Reader extends Thread implements Man{
    Semaphore semaphore;
    Resource resource;
    Library library;
    int id;
    private final String name;

    public Reader(Semaphore semaphore,Resource resource, Library library, int id) {
        this.semaphore = semaphore;
        this.resource = resource;
        this.library = library;
        this.id = id;
        this.name = "Reader " + id;
    }

    @Override
    public void waitingMessage(){ System.out.println(name + " is waiting in queue");}
    @Override
    public void actionMessage() { System.out.println(name + " is reading");}
    @Override
    public void finishMessage() { System.out.println(name + " finished reading");}
    @Override
    public String toString(){ return name;}

    public int howMany(){
        return semaphore.availablePermits();
    }

    @Override
    public void run(){

        while(true){
            try{
            library.requestRead(this);
            semaphore.acquire();

            while(!resource.attempt()){
                sleep(1);
            }

            library.startReading(this);
            sleep(1000);

            library.stopReading(this);
            semaphore.release();

            resource.finish();
            sleep(3000);}
            catch(InterruptedException e){ System.out.println( name + " interrupted");
                                            Thread.currentThread().interrupt();}
        }
        }
}
