import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
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
		
		String zipFileName = zFile.getName().concat(".zip");
		 
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
	void unzipTest() {
		fail("TODO");
	}
}
