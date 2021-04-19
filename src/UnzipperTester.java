import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.junit.jupiter.api.Test;

class UnzipperTester {

	//Testing isZipped methods
	@Test
	void isZippedTest() throws IOException {
		
		/* Making zipped files to use to test */
		File zFile = new File("src/files/MyZip.txt");
		zFile.createNewFile();
		
		//Add zip tag
		String zipFileName = zFile.getName().concat(".zip");
		
		//Zip up file
        FileOutputStream fos = new FileOutputStream(zipFileName);
        ZipOutputStream zos = new ZipOutputStream(fos);
        zos.putNextEntry(new ZipEntry(zFile.getName()));

        byte[] bytes = Files.readAllBytes(Paths.get("src/files/MyZip.txt"));
        zos.write(bytes, 0, bytes.length);
        zos.closeEntry();
        zos.close();
        
        //Test
        assertTrue(Unzipper.isZipped("MyZip.txt.zip"));
        
        //Deleting the files
        File zipFile = new File("MyZip.txt.zip");
		zFile.delete();
		zipFile.delete();
	}
	
	//Testing unzip to method TODO
	@Test
	void unzipTest() throws IOException {
		
		/* Making zipped files to use to test */
		File zFile = new File("src/files/MyZip.txt");
		zFile.createNewFile();
		
		//Write Hello World to the file
		FileWriter fw = new FileWriter(zFile);
		fw.write("Hello World!");
		fw.close();
		
		//Add zip tag
		String zipFileName = zFile.getName().concat(".zip");
		 
		//Zip up file
        FileOutputStream fos = new FileOutputStream(zipFileName);
        ZipOutputStream zos = new ZipOutputStream(fos);
        zos.putNextEntry(new ZipEntry(zFile.getName()));

        byte[] bytes = Files.readAllBytes(Paths.get("src/files/MyZip.txt"));
        zos.write(bytes, 0, bytes.length);
        zos.closeEntry();
        zos.close();
        zFile.delete();
        
        //Call unzipper
        Unzipper unZippy = new Unzipper("MyZip.txt.zip");
        
        //Unzip my zipped file
        unZippy.unzipTo("src/files/");
        
        //Turn the file contents into a string
        File unzedFile = new File("src/files/Myzip.txt");
        Scanner scany = new Scanner(unzedFile);
        String str = scany.nextLine();
        scany.close();
        
        //Deleting the files
        unzedFile.delete();
        File zipFile = new File("MyZip.txt.zip");
		zipFile.delete();
        
		//Test that text has not changed
        assertTrue(str.equals("Hello World!"));
        

       
		
	}
}
