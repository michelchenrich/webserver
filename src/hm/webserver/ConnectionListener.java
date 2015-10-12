package hm.webserver;

import java.net.Socket;

interface ConnectionListener {
    void acceptConnection(Socket socket);

    boolean isListening();
}