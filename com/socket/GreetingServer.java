package com.socket;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class GreetingServer extends Thread {
	private ServerSocket serverSocket;

	public GreetingServer(int port) throws IOException {
		serverSocket = new ServerSocket(port);
		serverSocket.setSoTimeout(300000);
	}

	/*public void run() {
		while (true) {
			try {
				System.out.println("Waiting for client on port " + serverSocket.getLocalPort() + "...");
				Socket server = serverSocket.accept();
				System.out.println("Just connected to " + server.getRemoteSocketAddress());
				DataInputStream in = new DataInputStream(server.getInputStream());
				System.out.println(in.readUTF());
				DataOutputStream out = new DataOutputStream(server.getOutputStream());
				out.writeUTF("Thank you for connecting to " + server.getLocalSocketAddress() + "\nGoodbye!");
				server.close();
			} catch (SocketTimeoutException s) {
				System.out.println("Socket timed out!");
				break;
			} catch (IOException e) {
				e.printStackTrace();
				break;
			}
		}
	}*/
	public void run() {
		System.out.println("Server start");
		boolean b=true;
		while (b) {
			try {
				Socket server = serverSocket.accept();
				DataInputStream in = new DataInputStream(server.getInputStream());
				DataOutputStream out = new DataOutputStream(server.getOutputStream());
				String key=in.readUTF();
				System.out.println("Client says :" + key );
				switch (key) {
				case "1":out.writeUTF("****one****");break;
				case "2":out.writeUTF("****two****");break;
				default:out.writeUTF("****bye****");b=false;break;
				}
				server.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		test();
	}

	public static void test() {
		int port = Integer.parseInt("1232");
		try {
			Thread t = new GreetingServer(port);
			t.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
