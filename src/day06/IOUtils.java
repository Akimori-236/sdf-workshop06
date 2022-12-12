package day06;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class IOUtils {
    public static void write(Socket socket, String payload) throws IOException {
        OutputStream os = socket.getOutputStream();
        BufferedOutputStream bos = new BufferedOutputStream(os); 
        // bos knows the max transfer unit (MTU) to send efficiently
        DataOutputStream dos = new DataOutputStream(bos);
        // SEND
        dos.writeUTF(payload);
        System.out.println(">SEND>" + payload);
        dos.flush();
    }

    public static String read(Socket socket) throws IOException {
        InputStream is = socket.getInputStream();
        BufferedInputStream bis = new BufferedInputStream(is);
        DataInputStream dis = new DataInputStream(bis);
        String payload = dis.readUTF();
        System.out.println("<READ<" + payload);
        return payload;
    }
}
