package irc;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.Console;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ClientHandler implements Runnable {
    Socket socket;
    DataInputStream dis;
    DataOutputStream dos;
    String filename;
    Console cons;
    ExecutorService clientThreadpool = Executors.newFixedThreadPool(2);

    public ClientHandler(Socket s) {
        this.socket = s;
        try {
            OutputStream os = this.socket.getOutputStream();
            BufferedOutputStream bos = new BufferedOutputStream(os);
            this.dos = new DataOutputStream(bos);
            InputStream is = this.socket.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(is);
            this.dis = new DataInputStream(bis);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("ERROR> Unable to get client output stream");
        }

    }

    @Override
    public void run() {
        // socket communication
        // SERVER READ CLIENT & WRITE TO ALL CLIENTS



        InputStreamReader serverReader = new InputStreamReader(socket);
        clientThreadpool.submit(serverReader);
        // WRITE
        this.cons = System.console();
        while (true) {
            String inputText = this.cons.readLine("");
            try {
                this.write(inputText);
            } catch (IOException e) {
                System.err.println("ERROR> Unable to write to server");
            }
        }
    }

    // SERVER WRITE TO CLIENT
    public void write(String payload) throws IOException {
        this.dos.writeUTF(payload);
        System.out.println("SENT> " + payload);
        this.dos.flush();
    }
}
