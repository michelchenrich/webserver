package hm.webserver;

import java.io.IOException;
import java.net.ServerSocket;

class ConnectionAcceptorTask implements Runnable {
    private final ServerSocket serverSocket;
    private final ConnectionListener listener;

    public ConnectionAcceptorTask(ServerSocket serverSocket, ConnectionListener listener) {
        this.serverSocket = serverSocket;
        this.listener = listener;
    }

    public void run() {
        while (canRun()) acceptConnection();
    }

    private boolean canRun() {
        return listener.isListening() && !Thread.interrupted();
    }

    private void acceptConnection() {
        try {
            listener.acceptConnection(serverSocket.accept());
        } catch (IOException exception) {
            if (listener.isListening()) exception.printStackTrace();
        }
    }
}