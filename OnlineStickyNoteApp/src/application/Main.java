package application;

import java.io.IOException;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
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
	static public String clientName;
	
	@Override
	public void start(Stage primaryStage) throws IOException{
		textBoxPopup();
		root = (VBox)FXMLLoader.load(getClass().getResource("Sample.fxml"));
		scene = new Scene(root,screenWidth,screenHeight);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.getIcons().add(new Image("file:noteIcon.png"));
		primaryStage.setTitle(clientName + "'s Notes"); //added clients name to title
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
	
	private void textBoxPopup() throws IOException {
		Stage popupStage=new Stage();
		popupStage.setTitle("Enter Username");
		TextField textBox= new TextField();
		textBox.setPromptText("Enter Username...");
		textBox.setOnAction(e -> textFromBox(popupStage, textBox));
		VBox layout= new VBox();
		layout.getChildren().addAll(textBox);
		Scene popupScene= new Scene(layout, 500, 50);
		textBox.setPrefSize(200, 200);
		popupStage.setScene(popupScene);
		popupStage.setResizable(false);
		popupStage.showAndWait();
	}
	
	private void textFromBox(Stage s, TextField t) {
		clientName = t.getText();
		s.close();
	}
}
