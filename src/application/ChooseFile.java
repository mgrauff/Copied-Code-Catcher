package application;

import base.Unzipper;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;

public class ChooseFile {

	Unzipper uz;
	String fileToUnzipPath;
	String unzipToThisFilePath;
	List<File> selectedFiles;

	public ChooseFile() {

		unzipToThisFilePath = null;
		selectedFiles = new ArrayList<File>();
		fileToUnzipPath = null;

	}

	public ArrayList<File> fileDirectory(ActionEvent event) {
		ArrayList<File> projectList = new ArrayList<File>();
		DirectoryChooser dc = new DirectoryChooser();
		File directory = dc.showDialog(null);

		if(directory != null) {
			File[] files = directory.listFiles();
			if(directory.isDirectory()) {
				for(int fileNum = 0; fileNum < files.length; fileNum++) {
					if(files[fileNum].isFile()) {
						projectList.add(files[fileNum]);
						selectedFiles.add(files[fileNum]);
					}
				}
			}
		}

		return projectList;
	}

	/**
	 * AddFileButtonAction handles the user clicking the Add File(s) button 
	 * @param event: the user clicked the Add file(s) button
	 * @return the list of files selected by the user
	 */
	public List<File> AddFileButtonAction(ActionEvent event) {
		FileChooser fc = new FileChooser();
		List<File> files = fc.showOpenMultipleDialog(null);

		if(selectedFiles != null && files != null) {
			selectedFiles.addAll(files);
			return files; //return the files to the user
		}

		return null; //don't return anything if nothing was selected
	}//end AddFileButtonAction

	public void removeFileButton(ActionEvent event, ObservableList<Integer> myFiles) {
		if(myFiles.size() > 0) {
			for(int fileNum: myFiles) {
				selectedFiles.remove(fileNum);
			}
		}
	}

}
