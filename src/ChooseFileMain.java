import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
//import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class ChooseFileMain extends Application implements EventHandler<ActionEvent>{
	
	Stage window;
	Button selectFilesButton;
	Button unzipToDestFileButton;
	Button addDirectoryButton;
	Button removeFileButton;
	ListView<String> fileList;
	ObservableList<String> data = FXCollections.observableArrayList();
	ChooseFile fc = new ChooseFile();
	
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
		
		selectFilesButton = new Button("Add Files");
		selectFilesButton.setOnAction(this);
		GridPane.setConstraints(selectFilesButton, 1, 2);
		
		unzipToDestFileButton = new Button("Unzip to dest File");
		unzipToDestFileButton.setOnAction(this);
		GridPane.setConstraints(unzipToDestFileButton, 2, 3);
		
		addDirectoryButton = new Button("Add Directory");
		addDirectoryButton.setOnAction(this);
		GridPane.setConstraints(addDirectoryButton, 3, 4);
		
		removeFileButton = new Button("Remove File(s)");
		removeFileButton.setDisable(true);
		removeFileButton.setOnAction(this);
		GridPane.setConstraints(removeFileButton, 4, 5);
		
		fileList = new ListView();
		fileList.setItems(data);
		fileList.setEditable(false);
		fileList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		fileList.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				// TODO Auto-generated method stub
				removeFileButton.setDisable(false);
			}
			
		});
		
		
		Label fileListLabel = new Label("File List");
		Font font = Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 12);
	    fileListLabel.setFont(font);
	    
		VBox ncf = new VBox(fileListLabel, fileList, selectFilesButton, unzipToDestFileButton, addDirectoryButton, removeFileButton);
		ncf.setSpacing(20);
		ncf.setPadding(new Insets(20, 50, 50, 60));
		
		
		grid.getChildren().addAll(ncf);
		
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
		if(event.getSource()== selectFilesButton) {
			String fileName = fc.StartButtonAction(event).toString();
			fileName = fileName.substring(1, fileName.length()-1);
			data.add(fileName);
			
		}
		
		if(event.getSource() == addDirectoryButton) {
			ArrayList<File> files = fc.fileDirectory(event);
			for(File name: files) {
				data.add(name.toString() + "\n");
			}
			
		}
		
		
		if(event.getSource() == unzipToDestFileButton) {
			try {
				fc.UnzipToDestFileAction(event);
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Error unzipping file(s)");
			}
		}
		
		if(event.getSource() == removeFileButton) {
			try {
				ObservableList<Integer> selectedIndeces = fileList.getSelectionModel().getSelectedIndices();
				ObservableList<String> myFiles = fileList.getSelectionModel().getSelectedItems();
				fc.removeFileButton(event, myFiles);
				for(int index: selectedIndeces) {
					fileList.getItems().remove(index);
				}
			} catch (Exception e) {}
		}
	}

	


}
