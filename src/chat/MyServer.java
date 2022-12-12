package chat;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class MyServer {

    ServerSocket server;
    Socket socket;

    public void ServerStart(int port) throws IOException {
		ServerSocket server = new ServerSocket(port);
		System.out.println("Listening on port " + port);
		this.server = server;
	}

    public void ServerListen() {
        try {
            this.socket = this.server.accept();

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("ERROR> Unable to accept connection");
        }
    }

    public void write(String payload) throws IOException {
        OutputStream os = this.socket.getOutputStream();
        BufferedOutputStream bos = new BufferedOutputStream(os); 
        // bos knows the max transfer unit (MTU) to send efficiently
        DataOutputStream dos = new DataOutputStream(bos);
        // SEND
        dos.writeUTF(payload);
        System.out.println("SENT> " + payload);
        dos.flush();
    }
}
