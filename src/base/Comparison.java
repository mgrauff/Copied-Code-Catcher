package base;

import java.io.FileNotFoundException;

public class Comparison {
	
	//Files
	FileProcessor file1;
	FileProcessor file2;
	double score; //Compare score
	
	
	/**
	 * Contructor
	 * @param f1 File 1
	 * @param f2 File 2
	 */
	public Comparison(FileProcessor f1, FileProcessor f2) {
		
		file1 = f1;
		file2 = f2;
		
	}
	
	/**
	 * Generates the similarity score between the two files
	 * @throws FileNotFoundException
	 */
	public void generateScore() throws FileNotFoundException {
		
		Operators op = new Operators();
		
		Compare c = new Compare(file1, file2, op);
		
		score = c.compareFiles();
	}
	
	
	//Getters
	public FileProcessor getFile1() { return file1; }


	public FileProcessor getFile2() { return file2; }


	public double getScore() { return score; }


}
