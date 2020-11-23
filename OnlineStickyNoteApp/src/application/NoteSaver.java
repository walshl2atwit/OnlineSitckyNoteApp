package application;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class NoteSaver {
	
	public String clientName;
	
	public NoteSaver(String name) {
		clientName = name;
	}
	
	//saves notes into a text file with ;;; in between each note
	public void saveNotes(ArrayList<Note> a) throws Exception{
		// Connection to server
		Socket socket = new Socket("localhost", 1234);
		DataOutputStream os = new DataOutputStream(socket.getOutputStream());
		BufferedReader is = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		
		String notesSendMessage = "SEND/";
		
	}
	
	//finds all the saved notes and separates them into an array list of new notes
	public ArrayList<Note> downloadNotes() throws Exception {
		// Connection to server
		Socket socket = new Socket("localhost", 1234);
		DataOutputStream os = new DataOutputStream(socket.getOutputStream());
		BufferedReader is = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		
		// Request from server
		String noteRequestMessage = "GET/" + "\r\n\r\n";
		
		// Note contents from server
		String[] noteStrings = is.readLine().split(";;;");	// Contents of all the notes split into array
		ArrayList<Note> a = new ArrayList<Note>();		// All the notes in one collection
		for (int i = 0; i < noteStrings.length; i++) {
			a.add(new Note(noteStrings[i]));
		}
		
		return a;
	}
}