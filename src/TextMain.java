import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import java.awt.Container;
import java.awt.Font;
import java.awt.Component;


//Alex Chen
//Enoch Andreades
//Luke Greenway
//Matthew Grauff
//Paul Staats
//Copied Code Catcher ~~~ COMP 350 A


public class TextMain {

	public static void main(String[] args) throws IOException {



		String filePath; //Where all the files are coming from
		ArrayList<FileProcessor> files = new ArrayList<FileProcessor>(); //ArrayList for our files

		//Operators opt = new Operators("OperatorsInJava.txt");
		Operators opt = new Operators();
		double myScore;
		double[][] scoresTable;

		boolean done = false; //When user has no more files to give


		Scanner scany = new Scanner(System.in); //Scanner for scaning the console

		System.out.println("--------Copied Code Catcher--------\n");

		System.out.print("Please enter file path to read from: "); //Asking for file path

		filePath = scany.next(); //Recieving file path


		while (!done) { //While user has more files

			//Asking user for next file name
			System.out.print("\nPlease enter file name (if finished please enter \"done\"): ");

			String input = scany.next(); //Getting file name

			if (input.equalsIgnoreCase("done")) { done = true; } //Checking if user is done

			else { //Put file in arraylist and process it

				//Testing if zipped file and if so, unzip it
				//Add new file(s) to ArrayList
				if (Unzipper.isZipped(input)) { //Is a zip

						Unzipper zippy = new Unzipper(filePath + "/" + input);
						ArrayList<File> unzipped = zippy.unzipTo(filePath);
						
						for (int i = 0; i < unzipped.size(); i++) {
							files.add(new FileProcessor(unzipped.get(i), opt));
						}
					}
				else { //Is not a zip

					files.add(new FileProcessor(new File(filePath + "/" + input), opt));
				}
				

			}

		}


		scany.close();

		//Process all files in the arraylist
		for (int i = 0; i < files.size(); i++) {
			files.get(i).read();
		}

		//2D array of files.size by files.size (aka 4 projects == a 2d array of 4x4)
		scoresTable = new double[files.size()][files.size()]; //This is inefficient, more space than we really need but works for now

		//This is hugely inefficient at the moment since it is still comparing projects to themselves
		for (int i = 0; i < files.size()/2; i++) {//Nested loop comparing all projects together

			for (int j = 0; j < files.size(); j++) {

				Compare compy = new Compare(files.get(i), files.get(j), opt);
				myScore = compy.compareFiles(); //Comparing them

				scoresTable[i][j] = myScore; //Depositing them in a 2D array of the projects' indexes in the files arraylist

			}
		}


		//Printing results
		System.out.println("\n\nResults:");

		for (int i = 0; i < files.size(); i++) {
			System.out.print("\t" + i);
		}

		for (int i = 0; i < files.size(); i++) {	

			System.out.print("\n");
			System.out.print(i);

			for (int j = 0; j < files.size(); j++) {
				System.out.print("\t" + scoresTable[i][j]);

			}

		}



		// Paul was using this to test FileProcessor
		// File myFile = new File("C:/Users/StaatsPD17/git/Copied-Code-Catcher/Copied-Code-Catcher/src/example.txt");
		// try {
		// 	System.out.println("check");
		// FileProcessor myProcessor = new FileProcessor(myFile);
		// myProcessor.read();
		// } catch (Exception e) {
		// 	System.out.println("Error");

		// }
	}

	/**
	 * saveOutput
	 * Opens a gui for saving a file to a specific location
	 * chosen by the user. Displays a cancle window if the
	 * user decides to cancel the save process.
	 * @return the file object chosen in the gui.
	 */
	public static File saveOutput() {
		JFileChooser chooser = null;
		File file, directory;
		int status;
		chooser = new JFileChooser();
		setFileChooserFont(chooser.getComponents(), 18);
		status = chooser.showSaveDialog(null);

		if (status == JFileChooser.APPROVE_OPTION) {
			file = chooser.getSelectedFile();
			directory = chooser.getCurrentDirectory();
			System.out.println("Directory: " +directory.getName());
			System.out.println("File Selected to Open: "+ file.getName());
			System.out.println("Full path name: "+file.getAbsolutePath());
		}	//brings up a menu to select a file to copy
		else {
			JOptionPane.showMessageDialog(null,"Open File Dialog canceled");
			file = null;
		}	//deals with problem if chosen file is not compatable	

		return file;
	}//end saveOutput

	/**
	 * Helper method for saveOutput to 
	 * choose the font for the file chooser.
	 * @param comp
	 */
	public static void setFileChooserFont(Component[] comp, int fontSize) {
		final Font font = new Font("Arial",Font.PLAIN, fontSize);
		//creates a font with specifications of type arial and size 18

		for (int i=0; i<comp.length; i++) {		//sets every container
			//to have a font according to the specifications
			if (comp[i] instanceof Container) {
				setFileChooserFont(((Container)comp[i]).getComponents(), fontSize);
			}
			try {comp[i].setFont(font);}	//checks if component has
			catch (Exception e) {}		//the correct font

		}//for i
	}//set FC font

}