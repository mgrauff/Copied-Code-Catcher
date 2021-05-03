//TODO: Link this to other GUI
//TODO: Link files made to fileprocessor

package application;
	
import base.TxtWriter;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;


public class StackOverFlowScene extends Application {
	String fileName = "pastedCode";
	
	Stage window;
	Scene scene;
	Button button;
	String lastText = "";
	int count = 1;
	
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		window = primaryStage;
		window.setTitle("Testing");
		
		//Button that when pressed will pass input to be written
		TextArea textInput = new TextArea();
		button = new Button("Enter");
		button.setOnAction( e -> enterText(textInput));
		
		
		
		VBox layout = new VBox(10);
		layout.setPadding(new Insets(20,20,20,20));
		layout.getChildren().addAll(textInput, button);		
		
		scene = new Scene(layout, 300,250);
		window.setScene(scene);
		window.show();
	}
	
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
	
}