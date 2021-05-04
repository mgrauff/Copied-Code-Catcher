//TODO: Link this to other GUI
//TODO: Link files made to fileprocessor

package application;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;


public class StackOverFlowScene extends Scene {
	String fileName = "pastedCode";

	//Stage window;
	Scene scene;
	Button button;
	String lastText = "";
	int count = 1;

	public StackOverFlowScene(double width, double height) {
		this(new HBox(), width, height);
	}

	private StackOverFlowScene(HBox root, double width, double height) {
		super(root, width, height);
		//Form
		TextArea textInput = new TextArea();
		button = new Button("Enter");
		button.setOnAction( e -> enterText(textInput));



		VBox layout = new VBox(10);
		layout.setPadding(new Insets(20,20,20,20));
		layout.getChildren().addAll(textInput, button);		

		scene = new Scene(layout, 300,250);
		
		root.getChildren().addAll(layout);
	}






	//	@Override
	//	public void start(Stage primaryStage) throws Exception {
	//		
	//		window = primaryStage;
	//		window.setTitle("Testing");
	//		//Form
	//		TextArea textInput = new TextArea();
	//		button = new Button("Enter");
	//		button.setOnAction( e -> enterText(textInput));
	//		
	//		
	//		
	//		VBox layout = new VBox(10);
	//		layout.setPadding(new Insets(20,20,20,20));
	//		layout.getChildren().addAll(textInput, button);		
	//		
	//		scene = new Scene(layout, 300,250);
	//		window.setScene(scene);
	//		window.show();
	//	}

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

	}

	//	public static void main(String[] args) {
	//		launch(args);
	//	}
}