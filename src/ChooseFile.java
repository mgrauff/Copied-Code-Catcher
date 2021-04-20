import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import javafx.event.ActionEvent;
import javafx.stage.FileChooser;

public class ChooseFile {
	
	Unzipper uz;
	String fileToUnzipPath;
	String unzipToThisFilePath;
	List<File> selectedFiles;
	//
	public ChooseFile() {
		
		unzipToThisFilePath = getChosenDirectory().getAbsolutePath();
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
		fc.setInitialDirectory(fc.getInitialDirectory());
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
	
	/**
	 * getChosenDirectory
	 * Opens a gui for saving a file to a specific location
	 * chosen by the user. Displays a cancle window if the
	 * user decides to cancel the save process.
	 * @return the file object chosen in the gui.
	 */
	public static File getChosenDirectory() {
		JFileChooser chooser = null;
		File directory;
		int status;
		chooser = new JFileChooser();
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		TextMain.setFileChooserFont(chooser.getComponents(), 30);
		status = chooser.showOpenDialog(null);

		if (status == JFileChooser.APPROVE_OPTION) {
			directory = chooser.getSelectedFile();
		}	//brings up a menu to select a file to copy
		else {
			JOptionPane.showMessageDialog(null,"Open File Dialog canceled");
			directory = null;
		}	//deals with problem if chosen file is not compatable	

		return directory;
	}//end getChosenDirectory
	
	
}
