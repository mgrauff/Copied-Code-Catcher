import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
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
	
	
	//Testing unzip to with multiple files
	@Test
	void multiFileUnzipTest() throws IOException {
		
		/* Making zipped files to use to test */
		File zFile1 = new File("src/files/MyZip1.txt");
		zFile1.createNewFile();
		File zFile2 = new File("src/files/MyZip2.txt");
		zFile1.createNewFile();
		File zFile3 = new File("src/files/MyZip3.txt");
		zFile1.createNewFile();
		
		
		//Write to files
		/*
		 * File1 = "Hello World!"
		 * File2 = "Nope!"
		 * File3 = "Goodbye World!"
		 */
		FileWriter fw1 = new FileWriter(zFile1);
		fw1.write("Hello World!");
		fw1.close();
		FileWriter fw2 = new FileWriter(zFile2);
		fw2.write("Nope!");
		fw2.close();
		FileWriter fw3 = new FileWriter(zFile3);
		fw3.write("Goodbye World!");
		fw3.close();
		
		
		//Add zip tag
		String zipFileName = "MyZipM.zip";
		 
		//Zip up files into one zip
		//File 1
        FileOutputStream fos1 = new FileOutputStream(zipFileName);
        ZipOutputStream zos1 = new ZipOutputStream(fos1);
        
        zos1.putNextEntry(new ZipEntry(zFile1.getName()));
        byte[] bytes1 = Files.readAllBytes(Paths.get("src/files/MyZip1.txt"));
        zos1.write(bytes1, 0, bytes1.length);
        zos1.closeEntry();
        zFile1.delete();
        
        //File 2
        zos1.putNextEntry(new ZipEntry(zFile2.getName()));
       
        byte[] bytes2 = Files.readAllBytes(Paths.get("src/files/MyZip2.txt"));
        zos1.write(bytes2, 0, bytes2.length);
        zos1.closeEntry();
        zFile2.delete();
        
        //File 3
        zos1.putNextEntry(new ZipEntry(zFile3.getName()));
        byte[] bytes3 = Files.readAllBytes(Paths.get("src/files/MyZip3.txt"));
        zos1.write(bytes3, 0, bytes3.length);
        zos1.closeEntry();
        zFile3.delete();

       
        zos1.close();//Close Zip Stream
        
        //Call unzipper
        Unzipper unZippy = new Unzipper(zipFileName);
        
        //Unzip my zipped file
        ArrayList<File> unzippedFiles = unZippy.unzipTo("src/files/");
      
        //Turn the file contents into a string
        //File1
        File unzedFile1 = unzippedFiles.get(0);
        Scanner scany1 = new Scanner(unzedFile1);
        String str1 = scany1.nextLine();
        scany1.close();
        
        //File2
        File unzedFile2 = unzippedFiles.get(1);
        Scanner scany2 = new Scanner(unzedFile2);
        String str2 = scany2.nextLine();
        scany2.close();
        
        //File3
        File unzedFile3 = unzippedFiles.get(2);
        Scanner scany3 = new Scanner(unzedFile3);
        String str3 = scany3.nextLine();
        scany3.close();
        
        //Deleting the files
        unzedFile1.delete();
        unzedFile2.delete();
        unzedFile3.delete();
        
        File zipFile = new File("MyZipM.zip");
        zipFile.delete();

        
		//Test that text has not changed
        assertTrue(str1.equals("Hello World!"));
        assertTrue(str2.equals("Nope!"));
        assertTrue(str3.equals("Goodbye World!"));
		
	}
}
