//TODO: Link this to other GUI
//TODO: Link files made to fileprocessor

package application;

import java.io.File;

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
		textInput.setLayoutX(width/2.0);
		
		double textInputWidth = width/1.5;	
		double textInputHeight = height/1.7;
		double buttonWidth = width/16.0;
		textInput.setMaxHeight(textInputHeight);
		textInput.setMinHeight(textInputHeight);
		textInput.setMinWidth(textInputWidth);
		textInput.setMaxWidth(textInputWidth);
		double middle = (width/2.0) + (textInput.getWidth() / 2.0);
		//I want textInput to be in the center of the screen
		textInput.setTranslateX((width/2.0) - textInputWidth/2.0);
		textInput.setTranslateY((height/8.0));
		
		//Enter Button
		button = new Button("Enter");
		button.setOnAction( e -> enterText(textInput));
		Main.setRobinButtonStyle(button);
		button.setScaleX(2);
		button.setScaleY(1.6);
		button.setTranslateX(width/2.0 - buttonWidth/2.0);
		button.setTranslateY((height/8.0) + 20);

		//Back Button
		backButton = new Button("Back");
		Main.setRobinButtonStyle(backButton);
		backButton.setScaleX(2);
		backButton.setScaleY(1.6);	
		backButton.setTranslateX(width/2.0 - buttonWidth/2.0);
		backButton.setTranslateY((height/8.0) + 40);

		//Background
		Background myBackground = new Background(new BackgroundFill(Main.BEIGE, null, null));
		myBackground = new Background(new BackgroundImage(
				new Image("file:src/PasteCodeBackground.png"), 
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
				String name = write.writeFile(text);
	
				if(!name.equals("")) {
					File f = new File(name);
					ChooseFile.selectedFiles.add(f);
					ChooseFileScene.fileList.getItems().add(name);
					//We need to add file to list
				}
				textInput.clear();
			}
			else {
				System.out.println("Previously written text");
			}
		}

	}//end enterText

}//end StackOverflowScene
