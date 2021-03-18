import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;


//Alex Chen
//Enoch Andreades
//Luke Greenway
//Matthew Grauff
//Paul Staats
//Copied Code Catcher ~~~ COMP 350 A


public class main {

	public static void main(String[] args) throws IOException {

		

		String filePath; //Where all the files are coming from
		ArrayList<FileProcessor> files = new ArrayList<FileProcessor>(); //ArrayList for our files

		Operators opt = new Operators("OperatorsInJava.txt");
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

				Unzipper zippy = new Unzipper(filePath + "/" + input);
				zippy.unzipTo(filePath);

				files.add(new FileProcessor(new File(filePath + "/" + input), opt));

			}

		}

		
		scany.close();

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

			for (int j = 0; j < files.size(); i++) {
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

}
