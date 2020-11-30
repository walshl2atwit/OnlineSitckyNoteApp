package application;
	
import java.io.IOException;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class Main extends Application {
	
	static public int screenWidth = 650;
	static public int screenHeight = 635;
	static public Scene scene = null;
	static public ArrayList<Note> notes = new ArrayList<Note>();
	static public VBox root;
	static public NoteSaver saver;
	
	@Override
	public void start(Stage primaryStage) throws IOException{
		root = (VBox)FXMLLoader.load(getClass().getResource("Sample.fxml"));
		scene = new Scene(root,screenWidth,screenHeight);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.getIcons().add(new Image("file:noteIcon.png"));
		primaryStage.setTitle("Notes");
		primaryStage.show();
		primaryStage.setResizable(false);
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void stop() {
		System.out.println("--Exiting program--");
		
		try {
			saver.saveNotes(notes);
		} catch (Exception e) {
			System.out.println("--Failed to save notes--");
		}
		Platform.exit();
	}
}
