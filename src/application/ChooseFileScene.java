package application;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

public class ChooseFileScene extends Scene implements EventHandler<ActionEvent> {
	
	Button selectFilesButton;
	Button unzipToDestFileButton;
	Button addDirectoryButton; //adds all the files in a given directory to the fileList
	Button removeFileButton; //button for removing files from the fileList
	ListView<String> fileList; //text area displaying all files in the ChooseFile object's selectedFile list
	ObservableList<String> data = FXCollections.observableArrayList(); //stores the items in fileList
	ChooseFile fc = new ChooseFile();

	public ChooseFileScene(double width, double height) {
		this(new GridPane(), width, height);
	}
	private ChooseFileScene(GridPane grid, double width, double height) {
		super(grid, width, height);
		
		grid.setPadding(new Insets(10,10,10,10));
		grid.setVgap(8);
		grid.setHgap(10);
		
		//selectFilesButton
		//TODO fix formatting if multiple files are selected
		selectFilesButton = new Button("Add File(s)");
		selectFilesButton.setOnAction(this);
		GridPane.setConstraints(selectFilesButton, 1, 2);
		
		//unziptoDestFileButton
		unzipToDestFileButton = new Button("Unzip to dest File");
		unzipToDestFileButton.setOnAction(this);
		GridPane.setConstraints(unzipToDestFileButton, 2, 3);
		
		//addDirectoryButton
		//TODO fix no files selected exception handling
		addDirectoryButton = new Button("Add Directory");
		addDirectoryButton.setOnAction(this);
		GridPane.setConstraints(addDirectoryButton, 3, 4);
		
		//removeFileButton
		removeFileButton = new Button("Remove File(s)");
		removeFileButton.setDisable(true);
		removeFileButton.setOnAction(this);
		GridPane.setConstraints(removeFileButton, 4, 5);
		
		fileList = new ListView<String>();
		fileList.setItems(data); //populate the fileList
		fileList.setEditable(false);//we don't want the user messing with the list
		fileList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		//Only have the remove file button active when something is selected
		fileList.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				removeFileButton.setDisable(false);
			}//handle
			
		});//end fileList eventHandler
		
		
		//Label the fileList area
		Label fileListLabel = new Label("File List");
		Font font = Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 12);
	    fileListLabel.setFont(font);
	    
		//Set up the window visually
	    VBox ncf = new VBox(fileListLabel, fileList, selectFilesButton, unzipToDestFileButton, addDirectoryButton, removeFileButton);
	    ncf.setSpacing(20);
		ncf.setPadding(new Insets(20, 50, 50, 60));
		grid.getChildren().addAll(ncf);
	}
	
	@Override
	public void handle(ActionEvent event) {
		// TODO Auto-generated method stub
		if(event.getSource()== selectFilesButton) {
			List<File> files = fc.StartButtonAction(event);
			if(files != null) {
				String fileName = files.toString();
				fileName = fileName.substring(1, fileName.length()-1);
				data.add(fileName);	
			}
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
	}//end handle
}
