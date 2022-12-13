package irc;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;

public class MyClient {
    Socket socket = null;

    public void ClientStart(String host, int port) throws UnknownHostException, IOException, ConnectException {
		// connect as client
		Socket socket = new Socket(host, port);
		System.out.println("Connected to " + host + ":" + socket.getPort());
		this.socket = socket;
	}

    public void write(String payload) throws IOException {
        OutputStream os = this.socket.getOutputStream();
        BufferedOutputStream bos = new BufferedOutputStream(os);
        DataOutputStream dos = new DataOutputStream(bos);
        // SEND
        dos.writeUTF(payload);
        System.out.println("SENT> " + payload);
        dos.flush();
    }
}
