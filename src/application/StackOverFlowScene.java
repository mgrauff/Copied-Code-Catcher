//TODO: Link this to other GUI
//TODO: Link files made to fileprocessor

package application;

import base.TxtWriter;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.VBox;

public class StackOverFlowScene extends Scene {
	
	//Stage window;
	String fileName = "pastedCode";
	Scene scene;
	Button button;
	Button backButton;
	String lastText = "";
	int count = 1;

	public StackOverFlowScene(double width, double height) {
		this(new VBox(), width, height);
	}//public constructor

	private StackOverFlowScene(VBox root, double width, double height) {
		super(root, width, height);
		
		//Form
		TextArea textInput = new TextArea();
		textInput.setMaxHeight(600.0);
		textInput.setMinHeight(500.0);
		textInput.setMinWidth(500.0);
		textInput.setMaxWidth(700.0);
		
		//Enter Button
		button = new Button("Enter");
		button.setOnAction( e -> enterText(textInput));
		Main.setRobinButtonStyle(button);
		button.setScaleX(1.6);
		button.setScaleY(1.6);

		//Back Button
		backButton = new Button("Back");
		Main.setRobinButtonStyle(backButton);
		backButton.setScaleX(1.6);
		backButton.setScaleY(1.6);	

		//Background
		Background myBackground = new Background(new BackgroundFill(Main.BEIGE, null, null));
		myBackground = new Background(new BackgroundImage(
				new Image("file:src/HoodRobinRobin.png"), 
				BackgroundRepeat.NO_REPEAT, 
				BackgroundRepeat.NO_REPEAT, 
				BackgroundPosition.DEFAULT, 
				new BackgroundSize(1.0,1.0, true, true, false, false)));
		root.setBackground(myBackground);

		//Scene settings
		root.setPadding(new Insets(20,20,20,20));
		root.setSpacing(25.0);
		root.getChildren().addAll(textInput, button, backButton);	

	}//end private constructor

	void enterText(TextArea textInput) {
		String text = textInput.getText();
		if(text.length() <= 0) {
			System.out.println("Empty Field");
		}
		else {
			if(!text.equals(lastText)) {
				TxtWriter write = new TxtWriter(fileName, count);
				count ++;
				lastText = text;
				write.writeFile(text);
				textInput.clear();
			}
			else {
				System.out.println("Previously written text");
			}
		}

	}//end enterText

}//end StackOverflowScene
