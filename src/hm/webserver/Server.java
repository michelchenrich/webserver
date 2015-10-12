package hm.webserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class Server implements ConnectionListener {
    private ConnectionHandler connectionHandler;
    private ExecutorService executor;
    private boolean listening;
    private ServerSocket serverSocket;

    public Server(int port, ConnectionHandler connectionHandler) {
        this.connectionHandler = connectionHandler;
        executor = Executors.newFixedThreadPool(4);
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    public synchronized void acceptConnection(Socket socket) {
        executor.execute(new ConnectionTask(socket, connectionHandler));
    }

    public synchronized boolean isListening() {
        return listening;
    }

    public synchronized void start() {
        listening = true;
        executor.execute(new ConnectionAcceptorTask(serverSocket, this));
    }

    public synchronized void stop() {
        try {
            listening = false;
            serverSocket.close();
            executor.shutdownNow();
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }
}