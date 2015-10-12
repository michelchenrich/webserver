package hm.webserver;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class ServerTest {
    private Server server;
    private FakeConnectionHandler handler;
    private int expectedConnections;

    @Before
    public void setUp() throws Exception {
        handler = new FakeConnectionHandler();
        server = new Server(8042, handler);
    }

    @Test
    public void canRunServer() {
        server.start();
        assertTrue(server.isListening());
        server.stop();
        assertFalse(server.isListening());
    }

    @Test
    public void canConnectToServer() throws Exception {
        server.start();
        givenConnection();
        server.stop();
        assertNumberOfConnections();
    }

    @Test
    public void canConnectMultipleTimes() throws Exception {
        server.start();
        givenConnection();
        givenConnection();
        givenConnection();
        server.stop();
        assertNumberOfConnections();
    }

    private void givenConnection() throws IOException, InterruptedException {
        new Socket("localhost", 8042);
        synchronized (handler) {
            handler.wait(300);
        }
        expectedConnections++;
    }

    private void assertNumberOfConnections() {
        assertEquals(expectedConnections, handler.connections);
    }

    private static class FakeConnectionHandler implements ConnectionHandler {
        public int connections;

        public synchronized void run(InputStream inputStream, OutputStream outputStream) {
            connections++;
            notify();
        }
    }
}