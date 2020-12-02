package application;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Server{
	static File savedNotes = new File("savedNotes.txt");
	
	private static class ClientHandler implements Runnable {
		Socket socket;
		public ClientHandler(Socket socket) {
			this.socket = socket;
		}
		@Override
		public void run() {
			try {
				process();
			} catch (Exception e) {
				
			}
		}
		
		private void process() throws Exception{
			// Get a reference to the socket's input and output streams.
			InputStream is = socket.getInputStream();
			DataOutputStream os = new DataOutputStream(socket.getOutputStream());

			// Set up input stream filters.
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			String clientMessage = br.readLine();
			if (clientMessage.split("/")[0].equals("GET")) {
				System.out.println(clientMessage.split("/")[1] + " requested download...");
				String response = "";
				
				Scanner s = new Scanner(savedNotes);
				if (s.hasNext()) {
					response += s.nextLine() + "\r\n\r\n";
				} else {
					response += "\r\n\r\n";
				}
				os.writeBytes(response);
				
			} else if (clientMessage.split("/")[0].equals("SEND")) {
				clientMessage = clientMessage.replace("SEND/", "");
				PrintWriter out = new PrintWriter(new FileWriter(savedNotes));
				out.print(clientMessage);
				out.close();
			}
		}
	}	
	public static void main(String[] args) throws Exception{
		
		ServerSocket serverSocket = new ServerSocket(1234);
		
		System.out.println("This server is ready to receive");
		
		while(true)
		{
			Socket connectionSocket = serverSocket.accept();			
			ClientHandler client = new ClientHandler(connectionSocket);			
			Thread thread = new Thread(client);
			thread.start();
		}
		
	}
}

