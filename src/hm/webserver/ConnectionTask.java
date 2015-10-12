package hm.webserver;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

class ConnectionTask implements Runnable {
    private Socket socket;
    private ConnectionHandler handler;
    private InputStream inputStream;
    private OutputStream outputStream;

    public ConnectionTask(Socket socket, ConnectionHandler handler) {
        this.socket = socket;
        this.handler = handler;
    }

    public void run() {
        try {
            inputStream = socket.getInputStream();
            outputStream = socket.getOutputStream();
            handler.run(inputStream, outputStream);
        } catch (Exception exception) {
            exception.printStackTrace();
        } finally {
            finish();
        }
    }

    private void finish() {
        try {
            inputStream.close();
            outputStream.flush();
            outputStream.close();
            socket.close();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}