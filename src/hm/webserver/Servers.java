package hm.webserver;

import java.util.ArrayList;
import java.util.List;

public class Servers {
    private List<Server> servers = new ArrayList<>();

    public void add(int port, ConnectionHandler connectionHandler) {
        servers.add(new Server(port, connectionHandler));
    }

    public void execute() {
        for (Server server : servers)
            server.start();
        Runtime.getRuntime().addShutdownHook(new StopServersTask(servers));
        System.out.println("Servers started.");
    }
}