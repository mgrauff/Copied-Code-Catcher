package application;

import base.Unzipper;
import java.io.File;
import java.io.IOException;
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
			ArrayList<File> allFiles = new ArrayList<File>();
			
			for(File f : files) {
				try {
					if(Unzipper.isZipped(f)) {
						Unzipper u = new Unzipper(f);
						ArrayList<File> unzippedFiles = u.unzipTo("src/files/");
						for(File unzFile : unzippedFiles) {
							selectedFiles.add(unzFile);
							allFiles.add(unzFile);
						}
					}
					else {
						selectedFiles.add(f);
						allFiles.add(f);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			return allFiles; //return the files to the user
		}
		
		return null; //don't return anything if nothing was selected
	}//end AddFileButtonAction
	
	public void removeFileButton(ActionEvent event, ObservableList<String> myFiles) {
		ArrayList<File> removedFiles = new ArrayList<File>();
		
		for(String s : myFiles) {
			for(int i = 0; i < selectedFiles.size(); i++) {
				File f = selectedFiles.get(i);
				if(f.getPath() == s) {
					removedFiles.add(f);
				}
			}
		}
		
		for(File f : removedFiles) {
			selectedFiles.remove(f);
		}
//		for(String fileName: myFiles) {
//			selectedFiles.remove(fileName);
//		}
	}
	
}
