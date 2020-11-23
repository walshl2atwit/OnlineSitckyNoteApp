package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

public class SampleController extends Main {

    @FXML
    private TextField noteMessage;

    @FXML
    private Button newNoteBtn;
    
    @FXML
    public GridPane gridPane;
    
    public void initialize() throws Exception {
    	Main.saver = new NoteSaver("tempName");
    	notes = Main.saver.downloadNotes();
    	if (notes.isEmpty() == false) {
	    	for (int i = 0; i < notes.size(); i++) {
				gridPane.add(Note.createTextArea(notes.get(i)), i % 3, i / 3);
			}
    	}
    	System.out.println("--Notes loaded--");
    }

    //creates a note and adds it to the array of notes
    @FXML
    void makeNote(ActionEvent event) {
    	if (!noteMessage.getText().isBlank()) {
    		if (notes.size() % 3 == 0) {
    			gridPane.resize(screenWidth, gridPane.getHeight() + 50);
    			gridPane.addRow(gridPane.getRowCount());
    		}
    		Note note = new Note(20, Color.LIGHTPINK, noteMessage.getText());
    		Main.notes.add(note);
    		System.out.println(noteMessage.getText());
    		gridPane.add(Note.createTextArea(note), (notes.size() - 1) % 3, (notes.size() - 1) / 3);
    	}
    	noteMessage.clear();
	}
    
    //deletes a note when it's selected by the user and the user hits delete
    //the notes will then reorganize themselves to get rid of the gap
    @FXML
    void deleteNote(KeyEvent event) {
    	if (event.getCode() == KeyCode.DELETE) {
    		System.out.println("--Note Deleted--");
    		Object target = event.getTarget();
    		notes.remove(gridPane.getChildren().indexOf(target));
    		if (target instanceof Node) {
    			gridPane.getChildren().remove(target);
    			gridPane.getChildren().clear();
    			
    			for (int i = 0; i < notes.size(); i++) {
    				gridPane.add(Note.createTextArea(notes.get(i)), i % 3, i / 3);
    			}
    		}
    	}
    }

}
