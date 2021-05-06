package base;
//This class is used to combine multiple files into one
//The output of this class should be fed into fileprocessor

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class FileCombine {
//	ArrayList<File> filesToCombine; //the files to combine into 1
//	String outFileName; //the name that the outputfile should be called
	String directory = "src/files/";
	
	public FileCombine() {
		
	}
	
	
	/**
	 * will combine the files fed to output file named outfilename
	 * saves files as txt
	 */
	public void combineFiles(File f, String outFileName) {
		try {
			PrintWriter writer = new PrintWriter(outFileName, "UTF-8");

			//Handle if the file is corrupt
			if(isFileCorrupt(f)) {
				System.out.println("FILE " + f.getName() + " Was corrupt");
				//We'll want to handle this better
			}
			else {
				//We scan through file and print to output as we go
				Scanner scnr = new Scanner(f);
				while(scnr.hasNextLine()) {
					writer.println(scnr.nextLine());
				}
				
				scnr.close();
			}
		
		
			writer.close();
		}
		catch (Exception e) {
			//TODO: We'll want to handle this better. Perhaps just an error box pop-up, but we'll need something.
			System.err.println(e);
		}
	}
	
	
	
	/**
	 * will combine the files fed to output file named outfilename
	 * saves files as txt
	 */
	public File combineFiles(ArrayList<File> filesToCombine, String outFileName) {
		try {
			PrintWriter writer = new PrintWriter(outFileName, "UTF-8");
			//For each file, write it to the output file
			for(File f: filesToCombine) {
				
				//Handle if the file is corrupt
				if(isFileCorrupt(f) || isInvalid(f)) {
					System.out.println("FILE " + f.getName() + " Was corrupt");
					//We'll want to handle this better
				}
				else {
					//We scan through file and print to output as we go
					Scanner scnr = new Scanner(f);
					while(scnr.hasNextLine()) {
						writer.println(scnr.nextLine());
					}
					
					scnr.close();
					
					
				}
			}
			
			writer.close();
			File file = new File(outFileName);
			return file;
		}
		catch (Exception e) {
			//TODO: We'll want to handle this better. Perhaps just an error box pop-up, but we'll need something.
			System.err.println(e);
			
		}
		return null;
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
	
	private boolean isInvalid(File file) {
		if(!file.getName().endsWith(".txt") && !file.getName().endsWith(".java")) {
			System.out.println("Can't open non txt or java: " + file.getName());
			return true;
		}
		
		return false;
	}
	
}