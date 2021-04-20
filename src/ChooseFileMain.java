import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
//import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;

import java.io.IOException;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class ChooseFileMain extends Application implements EventHandler<ActionEvent>{
	
	Stage window;
	Button startButton;
	Button selectUnzipToFileButton;
	ChooseFile fc = null;//new ChooseFile();
	
	public void start(Stage primaryStage) {
		//
		/*
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/application/ChooseFile.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		}catch(Exception e) {
			e.printStackTrace();
		}*/
		
		
		//
		
		window = primaryStage;
		window.setTitle("Copy-Code-Catcher by Team Robin");
		GridPane grid = new GridPane();
		grid.setPadding(new Insets(10,10,10,10));
		grid.setVgap(8);
		grid.setHgap(10);
		
		startButton = new Button("Start");
		selectUnzipToFileButton = new Button("Select Unzip to File");
		
		
		startButton.setOnAction(this);
		GridPane.setConstraints(startButton, 1, 2);
		
		selectUnzipToFileButton.setOnAction(this);
		GridPane.setConstraints(selectUnzipToFileButton, 2, 3);
		
		
		grid.getChildren().addAll(startButton, selectUnzipToFileButton);
		
		Scene scene = new Scene(grid, 500, 500);
		window.setScene(scene);
		window.show();
	}

	public static void main(String[] args){
		launch(args);

	}

	@Override
	public void handle(ActionEvent event) {
		// TODO Auto-generated method stub
		if(event.getSource()== startButton) {
			
			//fc.StartButtonAction(event);
		}
		
		if(event.getSource() == selectUnzipToFileButton) {
			try {
				fc = new ChooseFile();
				fc.UnzipToSelectedFileAction(event);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		//listView.getItems().
	}


}
