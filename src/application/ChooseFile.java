package application;

import base.FileCombine;
import base.FileProcessor;
import base.TxtWriter;
import base.Unzipper;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;

public class ChooseFile {
	
	//Variable Dictionary
	Unzipper uz; //Unzipper class
	String fileToUnzipPath;	//Path to file that will be unzipped
	String unzipToThisFilePath; //Path to where the file should be unzipped to
	static List<File> selectedFiles; //List of files that are currently selected
	int count =0;
	/**
	 * Default constructor
	 */
	public ChooseFile() {
		
		//Default init
		unzipToThisFilePath = null;
		selectedFiles = new ArrayList<File>();
		fileToUnzipPath = null;

	}
	
	/**
	 * fileDirectory selects all the files in a directory
	 * @param event user clicking the button
	 * @return list of files selected
	 */
	public ArrayList<File> fileDirectory(ActionEvent event) {
		ArrayList<File> projectList = new ArrayList<File>(); //Return array
		DirectoryChooser dc = new DirectoryChooser();//For accessing directory
		File directory = dc.showDialog(null);

		if(directory != null) { //If the directory exists
			File[] files = directory.listFiles(); //Make an array of the files in the directory
			if(directory.isDirectory()) {
				//Add all the files to return array and to selected files
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
		FileChooser fc = new FileChooser(); //For getting files
		List<File> files = fc.showOpenMultipleDialog(null);
		
		if(selectedFiles != null && files != null) { 
			ArrayList<File> allFiles = new ArrayList<File>(); //Return array
			
			for(File f : files) {
				try {
					if(Unzipper.isZipped(f)) { //check if zipped
						Unzipper u = new Unzipper(f); //Unzip if so
						ArrayList<File> unzippedFiles = u.unzipTo("src/files/"+f.getName());
						HashMap<String, ArrayList<File>> map = new HashMap<String,ArrayList<File>>();
						for(String name: u.names) {
							name = name.substring(0, name.length()-1);
							map.put(name, null);
						}
						
						ArrayList<File> al;
						for(File unzFile : unzippedFiles) {
							//ONLY LOOK AT JAVA FILES
							if(unzFile.getName().endsWith(".java")) {
								for(String name: u.names) {
									name = name.substring(0,name.length()-1);
									if(unzFile.getPath().contains(name)) {
										if(map.get(name) == null) {
											al = new ArrayList<File>();
											al.add(unzFile);
											map.put(name, al);
										}
										else {
											al = map.get(name);
											al.add(unzFile);
											map.put(name,al);
										}
									}
								}
								

							}
							
							
						}
						for(String name: map.keySet()) {
							al = map.get(name);
							FileCombine fileCombine = new FileCombine();
							String out = "src/files/" + name + count + ".txt";
							count ++;
							File outFile = fileCombine.combineFiles(al, out);
							selectedFiles.add(outFile); //Add to selectedFiles and to return array
							
							
							allFiles.add(outFile);
						}
						
						
					}
					else {//else not zipped
						selectedFiles.add(f);//Add to selectedFiles and to return array
						allFiles.add(f);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
			for(File f : allFiles) {
				if(FileProcessor.isFileCorrupt(f)) {
					selectedFiles.remove(f);
				}
			}
			
			return allFiles; //return the files to the user
		}

		return null; //don't return anything if nothing was selected
	}//end AddFileButtonAction
	
	/**
	 * Removes file from selected
	 * @param event Clicking the button
	 * @param myFiles Files to remove
	 */
	public void removeFileButton(ActionEvent event, ObservableList<Integer> myFiles) {
		if(myFiles.size() > 0) {
			for(int fileName: myFiles) {
				selectedFiles.remove(fileName);
			}
		}
	}

}
