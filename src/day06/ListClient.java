package day06;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class ListClient {
    // 2) java day06.ListClient 100 localhost 8080
    // >> Connected to localhost:8080
    public static void main(String[] args) {
        // Get list size
        Integer listSize = Integer.parseInt(args[0]);
        Integer range = Integer.parseInt(args[1]);
        // Get host
        String host = args[2];
        // Get port number
        Integer port = Integer.parseInt(args[3]);

        // Create socket and connect
        try {
            Socket socket = new Socket(host, port);
            System.out.println("Connected to " + host + ":" + port + " on port " + socket.getPort());

            // 3) open output first
            // write 10,100 (10 numbers from 0 to 100)
            // close output stream
            IOUtils.write(socket, "%d %d".formatted(listSize, range));
            

            // 4) open input stream
            // reads string
            // close input stream
            // InputStream is = socket.getInputStream();
            // BufferedInputStream bis = new BufferedInputStream(is);
            // DataInputStream dis = new DataInputStream(bis);
            String response = IOUtils.read(socket);
            // calc avg TODO


            socket.close();

        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}
