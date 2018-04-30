package main;

import java.io.*;
import java.net.*;

public class Server {
	
	
	public static void main(String[] args) {
		try {
			ServerSocket svrSock = new ServerSocket(7777); // First argument for port number
			svrSock.setSoTimeout(100000); // Set the duration to check if the client is still connected in milliseconds
			System.out.println("Listening on Port:" + svrSock.getLocalPort());
			
			System.out.println("Listening for clients...");
			Socket server = svrSock.accept(); // Ready to listen for clients
			System.out.println("Successfully Connected to: " + server.getRemoteSocketAddress());
			
			DataInputStream in = new DataInputStream(server.getInputStream());
			DataOutputStream out = new DataOutputStream(server.getOutputStream());
			
			// While the client has not disconnected, continue to read incoming client data
			while(server.isConnected()) {
				String data = in.readUTF();
				int upperCase_count = 0;
				int lowerCase_count = 0;
				for(int i=0;i<data.length();i++) {
					if(Character.isUpperCase(data.charAt(i)))
						upperCase_count++;
					if(Character.isLowerCase(data.charAt(i)))
						lowerCase_count++;				
				}
				
				out.writeUTF("Server:Your string contained " + upperCase_count + " uppercase letters and " + lowerCase_count + " lowercase letters");
			}
			
			// Close before ending
			svrSock.close();
			server.close();
		} catch (SocketTimeoutException e) {
			System.out.println("The socket has timed out");
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Failed to setup ServerSocket on port");
		}		
	}
}
