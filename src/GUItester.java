import java.io.File;
import java.util.Scanner;
import javax.swing.JFileChooser;

public class GUItester {

	public static void main(String[] args) {
		

		//Variables
		File file;
		Scanner fileIn;
		int response;
		JFileChooser chooser = new JFileChooser(".");
	
		chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
	
		response = chooser.showOpenDialog(null);

	}

}
