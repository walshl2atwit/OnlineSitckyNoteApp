package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class SavingNotes {
	
	protected static File file = new File("savedNotes.txt");
	
	public static File getFile() {
		return file;
	}
	
	public static void setFile(File f) {
		file = f;
	}
	
	//saves notes into a text file with ;;; in between each note
	public static void saveNotes(ArrayList<Note> a) {
		try (PrintWriter out = new PrintWriter(file);) {
			for (int i = 0; i < a.size(); i++) {
				out.printf("%s;;;", a.get(i).getMessage());
			}
			System.out.println("--Notes saved--");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	//finds all the saved notes and separates them into an array list of new notes
	public static ArrayList<Note> getNotesFromFile() {
		ArrayList<Note> a = new ArrayList<Note>();
		try (Scanner in = new Scanner(file)) {
			if (in.hasNext() == true) {
				String[] arr = in.nextLine().split(";;;");
				for (int i = 0; i < arr.length; i++) {
					a.add(new Note(arr[i]));
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return a;
	}
}
