import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;


//Alex Chen
//Enoch Andreades
//Luke Greenway
//Matthew Grauff
//Paul Staats
//Copied Code Catcher ~~~ COMP 350 A


public class main {

	public static void main(String[] args) {

		

		String filePath; //Where all the files are coming from
		ArrayList<File> files = new ArrayList<File>(); //ArrayList for our files

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

				files.add(new File(filePath + "/" + input));

				Operators opt = new Operators("OperatorsInJava.txt");

				FileProcessor processor = new FileProcessor(files.get(files.size() - 1));

				processor.read();

			}

		}

		scany.close();



		//Paul was using this to test FileProcessor
		// File myFile = new File("C:/Users/StaatsPD17/git/Copied-Code-Catcher/Copied-Code-Catcher/src/example.txt");
		// try {
			
		// 	Operators opt = new Operators("OperatorsInJava.txt");
		// 	System.out.println("check");
		// FileProcessor myProcessor = new FileProcessor(myFile, opt);
		// myProcessor.read();
		// } catch (Exception e) {
		// 	System.out.println("Error");
		
		// }
	}

}
