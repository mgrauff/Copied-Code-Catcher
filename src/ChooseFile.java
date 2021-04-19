import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.stage.FileChooser;

public class ChooseFile {
	
	Unzipper uz;
	String fileToUnzipPath;
	String unzipToThisFilePath;
	List<File> selectedFiles;
	int i;
	
	public ChooseFile() {
		
		unzipToThisFilePath = null;
		try {
			uz = new Unzipper(unzipToThisFilePath);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		selectedFiles = new ArrayList<File>();
		fileToUnzipPath = null;
		
	}
	
	
	
	public List<File> StartButtonAction(ActionEvent event) {
		FileChooser fc = new FileChooser();
		fc.setInitialDirectory(new File("C:\\Users\\chenat18\\Documents\\2021Spring\\COMP350"));
		selectedFiles = fc.showOpenMultipleDialog(null);
		
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
	
	public void UnzipToSelectedFileAction(ActionEvent event) throws IOException {
		
		FileChooser fc = new FileChooser();
		fc.setInitialDirectory(new File("C:\\Users\\chenat18\\Documents\\2021Spring\\COMP350"));
		File selectedFile = fc.showOpenDialog(null);
		unzipToThisFilePath = selectedFile.getAbsolutePath();
		
		
		for(int i = 0; i < selectedFiles.size(); i++) {
			//System.out. println(selectedFiles.get(i).getAbsolutePath());
			
			fileToUnzipPath = selectedFiles.get(i).getAbsolutePath();
			
			if(Unzipper.isZipped(fileToUnzipPath)) {
				
				uz.unzipTo(unzipToThisFilePath);
			}
		}
	}
	
	
}
