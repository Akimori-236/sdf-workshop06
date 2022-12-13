package irc;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class InputStreamReader implements Runnable {
    Socket socket;

    public InputStreamReader(Socket s) {
        this.socket = s;
    }

    @Override
    public void run() {
        try (InputStream is = this.socket.getInputStream()) {
            BufferedInputStream bis = new BufferedInputStream(is);
            DataInputStream dis = new DataInputStream(bis);
            while (true) {
                String payload = dis.readUTF();
                System.out.println("RECEIVED> " + payload);
            }
        } catch (IOException e) {
            System.err.println("ERROR> Unable to read input stream");
        }
    }
}
