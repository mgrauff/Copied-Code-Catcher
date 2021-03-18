import java.io.File;
import java.io.FileNotFoundException;
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

		boolean done = false; //When user has no more files to give

		
		Scanner scany = new Scanner(System.in); //Scanner for scaning the console

		System.out.println("--------Copied Code Catcher--------\n");

		System.out.print("Please enter file path to read from: "); //Asking for file path

		filePath = scany.next(); //Recieving file path
		Operators opt = new Operators("OperatorsInJava.txt");

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

		for (int i =)

		for (int i = 0; i < files.size()/2; i++) {
			for (int j = 0; j < files.size(); j++) {
				Compare compy = new Compare(files.get(i), files.get(j));
			}
		}

		scany.close();



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
