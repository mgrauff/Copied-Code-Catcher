package application;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;
import java.io.IOException;
import java.io.InputStreamReader;


public class TxtWriter {
	 private String fileName;
	 private String text;
	 private int fileNumber;
	 
	 
	 public TxtWriter(String fileName, int fileNumber) {
		 //Create filename in format of: fileName#.txt
		 String fileCombo =fileName.concat( String.valueOf(fileNumber)).concat(".txt") ;
		 this.fileName = fileCombo;
	 }
 
	 public void writeFile(String text)   {
		 //Scan through text line by line
		 Scanner scnr = new Scanner(text);
		 try {
			 PrintWriter writer = new PrintWriter(fileName, "UTF-8");
			 while(scnr.hasNextLine()) {
				 text = scnr.nextLine();
				 writer.println(text);
			 }
			 writer.close();
		 }
		 catch (IOException e) {
			 e.printStackTrace();
		 }
		 scnr.close();
	 }
}