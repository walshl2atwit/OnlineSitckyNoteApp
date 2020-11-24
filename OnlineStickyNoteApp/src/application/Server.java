package application;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Server{
	
	static ArrayList<Thread> threads = new ArrayList<Thread>();
	static ArrayList<ClientHandler> clients = new ArrayList<ClientHandler>();
	static File savedNotes = new File("savedNotes.txt");
	
		private static class ClientHandler implements Runnable{
		
			Socket socket;
			public ClientHandler(Socket socket)
			{
				this.socket = socket;
			}
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					process();
				} catch (Exception e) {
					System.out.println(e);
				}
			}
		


		

	private void process() throws Exception{
		InputStream is = socket.getInputStream();
		DataOutputStream os = new DataOutputStream(socket.getOutputStream());
		
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		
		ArrayList<String> list = new ArrayList<>();
		while(true)
		{
			String clientMessage = br.readLine();
			System.out.println("Received: " + clientMessage);
			list.add(clientMessage);
			sendToClients(clientMessage);
		}
	}

}
		
	private static void log(String note) throws IOException {
		Scanner s = new Scanner(savedNotes);
		BufferedWriter writer = new BufferedWriter(new FileWriter(savedNotes, true));
		writer.write(note + "\n ;;; \n");
		writer.close();
	}
	
	private static void sendToClients(String message) throws IOException{
		for(int i =0; i < clients.size(); i++)
		{
			DataOutputStream os = new DataOutputStream(clients.get(i).socket.getOutputStream());
			os.writeBytes(message);
		}
	}
		
	public static void main(String[] args) throws Exception{
		
		ServerSocket serverSocket = new ServerSocket(1234);
		
		System.out.println("This server is ready to receive");
		
		while(true)
		{
			Socket connectionSocket = serverSocket.accept();			
			ClientHandler client = new ClientHandler(connectionSocket);			
			clients.add(client);
			Thread thread = new Thread(client);
			threads.add(thread);
			thread.start();
		}
		
	}
}
