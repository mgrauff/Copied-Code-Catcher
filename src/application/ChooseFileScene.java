package application;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import base.FileProcessor;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

public class ChooseFileScene extends Scene implements EventHandler<ActionEvent> {

	Button selectFilesButton;
	Button addDirectoryButton; //adds all the files in a given directory to the fileList
	Button removeFileButton; //button for removing files from the fileList
	Button startComparisonButton; //Start comparing the current filelist.
	Button stackOverflowButton;
	Button myGCCButton;
	String[] filesHolder; 
	static ListView<String> fileList; //text area displaying all files in the ChooseFile object's selectedFile list
	ObservableList<String> data = FXCollections.observableArrayList(); //stores the items in fileList
	ChooseFile fc = new ChooseFile(); //ChooseFile object

	public ChooseFileScene(double width, double height) {
		this(new GridPane(), width, height);
	}
	private ChooseFileScene(GridPane grid, double width, double height) {
		super(grid, width, height);


		grid.setPadding(new Insets(10,10,10,10));
		grid.setVgap(8);
		grid.setHgap(10);

		Background myBackground = new Background(
				//				new BackgroundImage(
				//						new Image("file:src/RobinBackground.png"), 
				//						BackgroundRepeat.NO_REPEAT, 
				//						BackgroundRepeat.NO_REPEAT, 
				//						BackgroundPosition.DEFAULT, 
				//						new BackgroundSize(1.0,1.0, true, true, false, false)),
				new BackgroundImage(
						new Image("file:src/FileSelectionBackground.png"),
						BackgroundRepeat.NO_REPEAT, 
						BackgroundRepeat.NO_REPEAT, 
						BackgroundPosition.DEFAULT, 
						new BackgroundSize(1.0,1.0, true, true, false, false)));

		grid.setBackground(myBackground);



		//selectFilesButton
		selectFilesButton = new Button("Add File(s)");
		selectFilesButton.setOnAction(this);
		Main.setRobinButtonStyle(selectFilesButton);
		//GridPane.setConstraints(selectFilesButton, 1, 0);
		
		myGCCButton = new Button("Add myGCC Zip(s)");
		myGCCButton.setOnAction(this);
		Main.setRobinButtonStyle(myGCCButton);
		

		//addDirectoryButton
		addDirectoryButton = new Button("Add Directory");
		addDirectoryButton.setOnAction(this);
		Main.setRobinButtonStyle(addDirectoryButton);
		GridPane.setConstraints(addDirectoryButton, 3, 0);

		//removeFileButton
		removeFileButton = new Button("Remove File");
		removeFileButton.setDisable(true);
		removeFileButton.setOnAction(this);
		Main.setRobinButtonStyle(removeFileButton);
		GridPane.setConstraints(removeFileButton, 4, 0);

		//compare files button
		startComparisonButton = new Button("Start Comparison");
		Main.setRobinButtonStyle(startComparisonButton);

		stackOverflowButton = new Button("Add StackOverFlow Code");
		Main.setRobinButtonStyle(stackOverflowButton);
		GridPane.setConstraints(stackOverflowButton, 4, 0);

		fileList = new ListView<String>();
		filesHolder = new String[ChooseFile.selectedFiles.size()];
		fileList.setItems(data); //populate the fileList
		fileList.setEditable(false);//we don't want the user messing with the list
		fileList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		//Only have the remove file button active when something is selected
		fileList.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				removeFileButton.setDisable(false);
			}//handle

		});//end fileList eventHandler		

		//Label the fileList area
		Label fileListLabel = new Label("File List");
		fileListLabel.setTextFill(Main.DARK_GREY);
		fileListLabel.setScaleX(1.5);
		Font font = Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 30);
		fileListLabel.setFont(font);

		//Set up the window visually
		VBox ncf = new VBox(
				fileListLabel, 
				fileList, 
				selectFilesButton, 
				myGCCButton,
				addDirectoryButton, 
				removeFileButton, 
				stackOverflowButton,
				startComparisonButton
				);
		ncf.setSpacing(20);
		ncf.setPadding(new Insets(20, 50, 50, 60));
		grid.getChildren().addAll(ncf);
	}

	@Override
	public void handle(ActionEvent event) {

		if(event.getSource()== selectFilesButton) {
			List<File> files = fc.AddFileButtonAction(event);
			if(files != null) {

//				for(int i = 0; i < files.size(); i++) {
//					//String fileName = files.get(i).getName();
//					String fileName = files.get(i).getAbsolutePath();
//					//					System.out.println(filesHolder.length);
//					//					for(int j = 0; j < filesHolder.length; j++) {
//					//						System.out.println(filesHolder[i]);
//					//						data.add(filesHolder[i]);
//					//					}
//					//fileName = fileName.substring(1, fileName.length()-1);
//					data.add(fileName);
//				}


				ArrayList<String> corruptFiles = new ArrayList<String>();

				for(File f : files) {

					if(!FileProcessor.isFileCorrupt(f)) {
						data.add(f.toString());	
					}
					else {
						
						//corruptFiles.add(f.toString());
					}
				}

				if(corruptFiles.size() > 0) {
					Alert a = new Alert(AlertType.WARNING);
					a.setHeaderText("We encountered issues with the following files (they may be corrupt or empty):");
					a.setContentText(corruptFiles.toString());
					a.show();

				}

			}
		}
		
		if(event.getSource() == myGCCButton) {
			List<File> files = fc.AddMyGCCButton(event);
			if(files != null) {

//				for(int i = 0; i < files.size(); i++) {
//					//String fileName = files.get(i).getName();
//					String fileName = files.get(i).getAbsolutePath();
//					//					System.out.println(filesHolder.length);
//					//					for(int j = 0; j < filesHolder.length; j++) {
//					//						System.out.println(filesHolder[i]);
//					//						data.add(filesHolder[i]);
//					//					}
//					//fileName = fileName.substring(1, fileName.length()-1);
//					data.add(fileName);
//				}


				ArrayList<String> corruptFiles = new ArrayList<String>();

				for(File f : files) {

					if(!FileProcessor.isFileCorrupt(f)) {
						data.add(f.toString());	
					}
					else {
						
						//corruptFiles.add(f.toString());
					}
				}

				if(corruptFiles.size() > 0) {
					Alert a = new Alert(AlertType.WARNING);
					a.setHeaderText("We encountered issues with the following files (they may be corrupt or empty):");
					a.setContentText(corruptFiles.toString());
					a.show();

				}

			}
		}

		if(event.getSource() == addDirectoryButton) {
			try{
				ArrayList<File> allFiles = fc.fileDirectory(event);
				for(File name: allFiles) {
					//data.add(name.getName() + "\n");
					data.add(name.toString() + "\n");
				}
			} catch (Exception e) {}
		}

		if(event.getSource() == removeFileButton) {
			try {
				ObservableList<Integer> selectedIndeces = fileList.getSelectionModel().getSelectedIndices();
				ObservableList<String> myFiles = fileList.getSelectionModel().getSelectedItems();
				ObservableList<Integer> copy = FXCollections.observableArrayList(selectedIndeces);

				fileList.getItems().removeAll(myFiles);
				fc.removeFileButton(event, copy);
				
				//				fileList.getItems().removeAll(myFiles);
				//				for(int index: selectedIndeces) {
				//					fileList.getItems().remove(index);
				//				}
				//								for(String i: myFiles) {
				//									System.out.println("Removed fileName: " + i);
				//									fileList.getItems().remove(i);
				//								}
				
				//data.removeAll(myFiles);
				fileList.getSelectionModel().clearSelection();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

	}//end handle

	/**
	 * gets selectedFiles from fc
	 * @return
	 */
	public List<File> selectedFiles() {
		return ChooseFile.selectedFiles;
	}//selected Files
}
