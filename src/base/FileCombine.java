package base;
//This class is used to combine multiple files into one
//The output of this class should be fed into fileprocessor

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class FileCombine {
	ArrayList<File> filesToCombine; //the files to combine into 1
	String outFileName; //the name that the outputfile should be called
	
	public FileCombine(ArrayList<File> filesToCombine, String outFileName) {
		this.filesToCombine = filesToCombine;
		this.outFileName = outFileName;
		
	}
	
	
	/**
	 * will combine the files fed to output file named outfilename
	 */
	public void combineFiles() {
		try {
			PrintWriter writer = new PrintWriter(outFileName, "UTF-8");
			//For each file, write it to the output file
			for(File f: filesToCombine) {
				
				//Handle if the file is corrupt
				if(isFileCorrupt(f)) {
					System.out.println("FILE " + f.getName() + " Was corrupt");
					//We'll want to handle this better
				}
				else {
					Scanner scnr = new Scanner(f);
					while(scnr.hasNextLine()) {
						writer.println(scnr.nextLine());
					}
					
					scnr.close();
				}
			}
			
			writer.close();
		}
		catch (Exception e) {
			System.err.println(e);
		}
	}
	
	
	
  /**
   * Determines if a file is corrupt
   * @param file
   * @return boolean value
   */
	private boolean isFileCorrupt(File file) {
		if(!file.canRead()) {
			System.out.println("File was unreadable");
			return true;
		}
		
		
		if(file.length()<= 0) {
			System.out.println("File was empty");
			return true;
		}

		Scanner scnr;
		try {
			scnr = new Scanner(file);
			scnr.next();
			scnr.close();
		} catch (Exception e) {
			System.out.println("Scanner.next() did not exist");
			return true;
		}
		
		
		
		return false;
	}
	
}