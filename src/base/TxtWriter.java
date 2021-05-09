package base;
import java.io.PrintWriter;
import java.util.Scanner;
import java.io.IOException;


public class TxtWriter {
	 private String fileName;
	 private String pathName = "src/files/";
	 
	 /**
	  * 
	  * @param fileName - name of output file
	  * @param fileNumber - number it should be given
	  */
	 public TxtWriter(String fileName, int fileNumber) {
		 //Create filename in format of: fileName#.txt
		 String fileCombo =fileName.concat( String.valueOf(fileNumber)).concat(".txt") ;
		 fileCombo = pathName.concat(fileCombo);
		 this.fileName = fileCombo;
	 }
 
	 /**
	  * Will write text to file
	  * @param text - text to be written to file
	  */
	 public String writeFile(String text)   {
		 //Scan through text line by line
		 boolean error = false;
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
			 error = true;
		 }
		 scnr.close();
		 if(error) {
			 return "";
		 }
		 
		 
		 return fileName;
	 }
}