package chat;

import java.io.Console;
import java.io.IOException;
import java.net.ConnectException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.rmi.server.ServerCloneException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Chat {
	/*
	 * use threading to facilitate sending and receiving at the same time
	 * main thread to send
	 * 2nd thread to wait for messages (read)
	 * implement runnable
	 * pass inputstream to constructor
	 * public void run() {while (true) {String resp = readUTF()}}
	 */
	// blocking IO.. very wasteful of threads
	// non-blocking IO -> keep changing between checkmsgs and sendmsgs
	public static void main(String[] args) {
		if (args.length != 2) {
			System.err.println("USE> java -cp classes chat.Chat <host> <port>");
			return;
		}
		// Parameters
		String host = args[0];
		Integer port = Integer.parseInt(args[1]);

		// Setup
		MyServer server = new MyServer();
		MyClient client = new MyClient();
		Console cons;
		ExecutorService threadpool = Executors.newFixedThreadPool(2);

		// Try to connect as client, if fail start server
		try {
			client.ClientStart(host, port);
		} catch (ConnectException e) {
			System.err.println("ERROR> No server available at " + host + ":" + port);
		} catch (UnknownHostException e) {
			e.printStackTrace();
			System.err.println("ERROR> Unable to resolve host");
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (client.socket == null) {
			// cant start client, start server instead
			try {
				System.out.println("Starting server...");
				server.ServerStart(port);

				// wait for connection from client
				server.ServerListen();
				System.out.println("New connection!");
				
				// Server reading
				InputStreamReader serverThread = new InputStreamReader(server.socket);
				threadpool.submit(serverThread);
				
				// Server writing
				cons = System.console();
				while (true) {
					String inputText = cons.readLine("");
					try {
						server.write(inputText);
					} catch (IOException e) {
						System.err.println("ERROR> Unable to write to server");
					}
				}

			} catch (IOException e) {
				e.printStackTrace();
				System.err.println("ERROR> Unable to start server");
			}
		} else {
			// CLIENT STARTED

			// Client reading
			InputStreamReader clientThread = new InputStreamReader(client.socket);
			threadpool.submit(clientThread);

			// Client writing
			cons = System.console();
			while (true) {
				String inputText = cons.readLine("");
				try {
					client.write(inputText);
				} catch (IOException e) {
					System.err.println("ERROR> Unable to write to server");
				}
			}
		}
	}

	public static void ServerListen(ServerSocket server) {

		while (true) {

		}
	}
	/*
	 * CHAT APP (WHATSAPP)
	 * first guy start server 2nd connect
	 * if cant connect.. start server
	 * ANOTHER TYPE (DISCORD)
	 * one multithreaded server
	 * one guy send, everybody receive
	 */
}
