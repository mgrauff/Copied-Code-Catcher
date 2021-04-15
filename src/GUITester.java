import java.io.*;
import java.util.Scanner;
import javax.swing.JFileChooser;

public class GUITester {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		int response;
		JFileChooser chooser = new JFileChooser(".");
		
		chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		response = chooser.showOpenDialog(null);
		
		
	}

}
