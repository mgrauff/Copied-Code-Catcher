package base;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Results {
	//Class for combining the results
	
	//Master list of files
	ArrayList<FileProcessor> files;
	
	//Master list of comparisons
	ArrayList<Comparison> results;
	
	
	/**
	 * Constructor
	 * @param fs Master list of file processors
	 */
	public Results(ArrayList<FileProcessor> fs) {
		
		files = fs;
		results = new ArrayList<Comparison>();
		
	}
	
	/**
	 * Fills the results
	 * Each comparasion will have a unique pair
	 * i.e. if FileA is put in to a Comparison as file1 and FileB is put in as file2 then there
	 * will not be a comparison with FileB as file1 and File as file2
	 * @throws FileNotFoundException 
	 */
	public void fillResults() throws FileNotFoundException {
		
		//Comparing the files to each other and building comparison list
		
		for (int i = 0; i < files.size(); i++) {
			for (int j = i; j < files.size(); j++) {
				
				if (i == j) {
					//Don't compare a file to itself
				}
				else {
					
					Comparison tempComp = new Comparison(files.get(i), files.get(j));
					tempComp.generateScore();
					
					results.add(tempComp);
				}
			}
		}
		
	}
	
	
	/**
	 * Prints the results to console
	 * 
	 */
	public void printToConsole() {
		
		System.out.println("File1:          File2:          Score:");
		
		for (int i = 0; i < results.size(); i++) {
			
			System.out.print(results.get(i).getFile1().getName());
			System.out.print("          ");
			System.out.print(results.get(i).getFile2().getName());
			System.out.print("          ");
			System.out.println(results.get(i).getScore());
		}
		
	}
	
	/**
	 * Prints to a file using File object
	 * @param outFile File to print to
	 * @throws IOException 
	 */
	public void printToFile(File outFile) throws IOException {
		
		FileWriter outStream = new FileWriter(outFile);
		
		outStream.write("File1:          File2:          Score:\n");
		
		for (int i = 0; i < results.size(); i++) {
			
			results.get(i).getScore();
			
			outStream.write(results.get(i).getFile1().getName());
			outStream.write("          ");
			outStream.write(results.get(i).getFile2().getName());
			outStream.write("          ");
			outStream.write(String.valueOf(results.get(i).getScore()));
			
		}
		
		outStream.close();
		
	}
	
	/**
	 * Prints to a file using FileOutputStream
	 * @param outFile File to print to
	 * @throws IOException 
	 */
	public void printToFile(FileWriter outFile) throws IOException {
		
		outFile.write("File1:          File2:          Score:\n");
		
		for (int i = 0; i < results.size(); i++) {
			
			results.get(i).getScore();
			
			outFile.write(results.get(i).getFile1().getName());
			outFile.write("          ");
			outFile.write(results.get(i).getFile2().getName());
			outFile.write("          ");
			outFile.write(String.valueOf(results.get(i).getScore()));
			
		}
		
		outFile.close();
		
	}

}
