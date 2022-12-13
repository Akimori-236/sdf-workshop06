package irc;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class IRCServer {
    // server needs to take in multiple connections
    // clients need their own threaded input stream
    // server input -> output to all clients
    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            System.err.println("USAGE> java -cp classes irc.IRCServer <port>");
            return;
        }
        // SETUP
        Integer port = Integer.parseInt(args[0]);
        ExecutorService threadpool = Executors.newFixedThreadPool(3);

        ServerSocket server = new ServerSocket(port);
        while (true) {
            // Connection
            Socket socket = server.accept();
            ClientHandler client = new ClientHandler(socket);
            System.out.println("New connection!");
            
        }
    }
}
