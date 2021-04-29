import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;

public class ChooseFile {
	
	Unzipper uz;
	String fileToUnzipPath;
	String unzipToThisFilePath;
	List<File> selectedFiles;
	//
	public ChooseFile() {
		
		unzipToThisFilePath = null;
		selectedFiles = new ArrayList<File>();
		fileToUnzipPath = null;
		
	}
	
	public ArrayList<File> fileDirectory(ActionEvent event) {
		ArrayList<File> projectList = new ArrayList<File>();
		DirectoryChooser dc = new DirectoryChooser();
		File directory = dc.showDialog(null);
		
		File[] files = directory.listFiles();
		if(directory.isDirectory()) {
			for(int fileNum = 0; fileNum < files.length; fileNum++) {
				if(files[fileNum].isFile()) {
					projectList.add(files[fileNum]);
					selectedFiles.add(files[fileNum]);
				}
			}
		}
		
		
		return projectList;
	}
	
	
	public List<File> StartButtonAction(ActionEvent event) {
		FileChooser fc = new FileChooser();
		//fc.setInitialDirectory(chooseDirectory());
		//fc.setInitialDirectory(new File("C:\\Users\\chenat18\\Documents\\2021Spring\\COMP350"));
		selectedFiles.addAll(fc.showOpenMultipleDialog(null));
		
		if(selectedFiles != null) {
			
			return selectedFiles;
			
			//for(int i = 0; i < selectedFiles.size(); i++) {
				
				//System.out. println(selectedFiles.get(i).getAbsolutePath());
			//}
		} 
		else {
			System.out.println("file is not valid");
		}
		
		return null;
	}
	
	
	//This function does not select the dest file, instead it opens it, which is very weird
	public void UnzipToDestFileAction(ActionEvent event) throws IOException {
	
		DirectoryChooser dc = new DirectoryChooser();
		//unzipToThisFilePath = "C:\\Users\\chenat18\\Documents\\2021Spring\\COMP350\\SectA_OrigUnzippedToThisFile";
		
		unzipToThisFilePath = dc.showDialog(null).getAbsolutePath();
		
		for(int i = 0; i < selectedFiles.size(); i++) {
			
			fileToUnzipPath = selectedFiles.get(i).getAbsolutePath();
			
			uz = new Unzipper(fileToUnzipPath);
			
			if(Unzipper.isZipped(fileToUnzipPath)) {
				
				uz.unzipTo(unzipToThisFilePath);
			}		
		}
	}
	
	public void removeFileButton(ActionEvent event, ObservableList<String> myFiles) {
		for(String fileName: myFiles) {
			selectedFiles.remove(fileName);
		}
	}
	
	


	
}
