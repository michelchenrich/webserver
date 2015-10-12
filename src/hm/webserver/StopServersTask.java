package hm.webserver;

import java.util.List;

class StopServersTask extends Thread {
    private final List<Server> servers;

    public StopServersTask(List<Server> servers) {
        this.servers = servers;
    }

    public void run() {
        for (Server server : servers)
            server.stop();
        System.out.println("Servers stopped.");
    }
}