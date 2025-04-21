package org.example.people;

import org.example.Library;
import org.example.Resource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.*;

class ReaderTest {

    private Semaphore semaphore;
    private Resource resource;
    private Library library;
    private AtomicBoolean readersInLibrary;
    private Reader reader;

    @BeforeEach
    void setUp() {
        semaphore = new Semaphore(2); // Example: maximum of 2 readers
        library = new Library();
        resource = new Resource(semaphore, readersInLibrary, library);
        reader = new Reader(semaphore, resource, library, 1);
    }

    @Test
    void testConstructor() {
        assertEquals("Reader 1", reader.toString());
        assertEquals(2, semaphore.availablePermits());
    }

    @Test
    void testHowMany() {
        assertEquals(2, reader.howMany()); // Semaphore starts with 2 permits

        semaphore.acquireUninterruptibly();
        assertEquals(1, reader.howMany());

        semaphore.acquireUninterruptibly();
        assertEquals(0, reader.howMany());
    }

    @Test
    void testLibraryIntegration() {
        library.requestRead(reader);
        assertTrue(library.getQueue().contains(reader));

        library.startReading(reader);
        assertFalse(library.getQueue().contains(reader));
        assertTrue(library.getInLibrary().contains(reader));

        library.stopReading(reader);
        assertFalse(library.getInLibrary().contains(reader));
    }
}
