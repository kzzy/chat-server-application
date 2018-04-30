import java.io.*;
import java.net.Socket;
import java.util.Scanner;


public class Client {
	public static void sendServer(DataOutputStream stream, String msg) {
		try {
			stream.writeUTF(msg);
			stream.flush();
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Failed to send the last message to server: " + msg);
		}
	}
	
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		
		System.out.print("Please enter the hostname: ");
		String host = input.nextLine();
		System.out.println();
		System.out.print("Please enter the Port: ");
		int port = Integer.parseInt(input.nextLine());
		System.out.println();
		
		try {				
			Socket client = new Socket(host, port);
			System.out.println("Client has successfully connected to: " + client.getRemoteSocketAddress());
			
			// setup client-output to server-input
			OutputStream clientToServer = client.getOutputStream();
			DataOutputStream clientData = new DataOutputStream(clientToServer); // setup data output
			System.out.println("Client OutputStream to Server Ready");
			
			// setup server-input to client-output
			InputStream serverToClient = client.getInputStream();
			DataInputStream serverData = new DataInputStream(serverToClient);
			System.out.println("Client InputStream From Server Ready");
			
			while(client.isConnected()) {
				sendServer(clientData, input.nextLine());
				// Server Response to our client input
				System.out.println(serverData.readUTF());
			}
			
			// Close before ending
			client.close();
			input.close();
			
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Failed to connect to ServerSocket using HOST:" + args[0] + " and PORT:" + args[1]);
			
		}
	}

}
