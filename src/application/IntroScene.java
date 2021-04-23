package application;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class IntroScene extends Scene {

	private VBox root;
	private Button advance;
	
	public IntroScene(double width, double height) {
		this(new VBox(), width, height);
	}
	private IntroScene(VBox root, double width, double height) {
		super(root, width, height);
		
		this.root = root;
		root.setAlignment(Pos.CENTER);
		
		Text welcomeText = new Text("Welcome to Team Robin's Copied Code Catcher");
		root.getChildren().add(welcomeText);
		
		advance = new Button("Show example results");
		root.getChildren().add(advance);
	}
	
	public void addAdvanceEvent(EventHandler<ActionEvent> eh) {
		advance.setOnAction(eh);
	}
}
