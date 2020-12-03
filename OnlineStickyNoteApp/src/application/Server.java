package application;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Server {
	
    //map is currently unused
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
			//create username variable to make things easier
			username = clientMessage.split("/")[1];
			//creates new file in specified location with username
			File savedNotes = new File(username + "_savedNotes.txt");
			
			if (clientMessage.split("/")[0].equals("GET")) {
				System.out.println(clientMessage.split("/")[1] + " requested download...");
				
				//checks if file already exists
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
					
				}//if file doesn't exist, then it creates a new one associated with that user
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
			    //added username removal
				clientMessage = clientMessage.replace("SEND/" + username + "/", "");
				
				

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

