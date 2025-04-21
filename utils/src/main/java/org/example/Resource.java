package org.example;

import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicBoolean;

public class Resource {
    Semaphore isBusy;
    AtomicBoolean readersInLibrary;
    Library library;

    public Resource(Semaphore isBusy, AtomicBoolean readersInLibrary, Library library) {
        this.isBusy = isBusy;
        this.readersInLibrary = readersInLibrary;
        this.library = library;
    }

    public synchronized boolean attempt() throws InterruptedException {
        if(library.isWriterInQueue())
            return false;

        if(!readersInLibrary.get()){
            isBusy.acquire();
            readersInLibrary.set(true);
        }
        return true;
    }

    public synchronized void finish() {
        if (library.isWriterInLibrary())
            return;

        if(library.getInLibrary().isEmpty() && library.isWriterInQueue()){
            readersInLibrary.set(false);
            isBusy.release();
            return;
        }

        if(library.isWriterInQueue()){
            readersInLibrary.set(false);
        }
    }
}
