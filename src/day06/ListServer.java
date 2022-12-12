package day06;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.SecureRandom;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class ListServer {
    // 1) java day06.ListServer 8080
    // >> Listening on port 8080
    public static void main(String[] args) {
        Integer port = Integer.parseInt(args[0]);

        Random rnd = new SecureRandom();
        // create server socket with input port number
        try {
            ServerSocket server = new ServerSocket(port);
            System.out.println("Listening on port " + port);

            while (true) {
                // wait for connection
                Socket socket = server.accept();
                System.out.println(
                        "New connection on port " + socket.getLocalPort() + ", passed to port " + socket.getPort());

                // 3) open input stream first if client opens output first
                // read number and limit
                // generate list of random numbers
                // put numbers in comma separated string
                // close input stream
                String payload = IOUtils.read(socket);
                String[] values = payload.split(" ");
                Integer count = Integer.parseInt(values[0]);
                Integer range = Integer.parseInt(values[1]);

                List<Integer> randNums = new LinkedList<>();
                for (Integer i=0; i<count; i++) {
                    randNums.add(rnd.nextInt(range));
                }
                String response = randNums.stream()
                    .map(v -> v.toString())                        // convert list element to string
                    .collect(Collectors.joining(":"));  // join the values into one string

                // 4) open output stream
                // send the comma separated string
                // close output stream
                IOUtils.write(socket, response);

                socket.close();
            }

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
