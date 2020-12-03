package application;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Server {
	
	static Map<String, File> users = new HashMap<String, File>();
	static String username;
	
	
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
			username = clientMessage.split("/")[1];
			File savedNotes = new File("C:\\Users\\Jonathan Bernardi\\git\\OnlineSitckyNoteApp\\OnlineStickyNoteApp\\" + username + "_savedNotes.txt");
			
			if (clientMessage.split("/")[0].equals("GET")) {
				System.out.println(clientMessage.split("/")[1] + " requested download...");
				
				if(savedNotes.exists())
				{
					String response = "";
					Scanner s = new Scanner(savedNotes);
					if (s.hasNext()) 
					{
						response += s.nextLine() + "\r\n\r\n";
					} else 
					{
						response += "\r\n\r\n";
					}
					os.writeBytes(response);
					
				}
				else 
				{
					try {
						savedNotes.createNewFile();
						users.put(username, savedNotes);
						if(savedNotes.exists())
						{
							String response = "";
							Scanner s = new Scanner(savedNotes);
							if (s.hasNext()) 
							{
								response += s.nextLine() + "\r\n\r\n";
							} else 
							{
								response += "\r\n\r\n";
							}
							os.writeBytes(response);
						}
					} catch(IOException e) {
						e.printStackTrace();
					}
					
				}
				
				
			} else if (clientMessage.split("/")[0].equals("SEND")) {
				clientMessage = clientMessage.replace("SEND/", "");
				

				try {
				PrintWriter out = new PrintWriter(new FileWriter(savedNotes));
				out.print(clientMessage);
				out.close();
				} catch(IOException e) {
					e.printStackTrace();
				}
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

