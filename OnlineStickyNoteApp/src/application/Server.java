package application;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Scanner;

class Server{
	static class ClientRequest implements Runnable{
		Socket connectionSocket;
	
		public ClientRequest(Socket socket)
		{
			this.connectionSocket = socket;
		}

		@Override
		public void run() {
			try {
				processRequest();
			} catch (Exception e) {
				System.out.println(e);
			}
			
		}

	private void processRequest() throws Exception{
		BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
		DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
		
		ArrayList<String> list = new ArrayList<>();
		while(true)
		{
			String str = inFromClient.readLine();
			System.out.println("Received: " + str);
			list.add(str);
			
		}
	}

}
	public static void main(String[] args) throws Exception{
		ServerSocket serverSocket = new ServerSocket(12345);
		
		System.out.println("This server is ready to receive");
		
		while(true)
		{
			Socket connectionSocket = serverSocket.accept();
			ClientRequest request = new ClientRequest(connectionSocket);
			Thread thread = new Thread(request);
			thread.start();
		}
		
	}
}
