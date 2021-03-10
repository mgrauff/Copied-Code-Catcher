import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class Unzipper {
	
	private String src;	//location of zipped folder
	
	/**
	 * Creates an Unzipper object that allows a zipped folder to be extracted to a new location
	 * 
	 * @param src the relative or absolute path of the zipped folder to be extracted
	 * @throws FileNotFoundException when the provided path does not exist
	 */
	public Unzipper(String src) throws FileNotFoundException {
		File f = new File(src);
		if(!f.exists()) {	//check if provided file exists
			throw new FileNotFoundException("The provided filepath does not exist");
		}
		this.src = src;
	}//constructor 
	
	/**
	 * Extracts all files from the zipped folder and copies them to the provided directory 
	 * 
	 * @param destDir the directory to which all files should be copied
	 * @throws IOException 
	 */
	public void unzipTo(String destDir) throws IOException {
		
		//make sure the directory path ends with a / or \
		char finalChar = destDir.charAt(destDir.length() - 1);
		if(finalChar != '/' && finalChar != '\\') {	
			destDir += '\\';
		}
		
		ZipInputStream zipStream = new ZipInputStream(new BufferedInputStream(new FileInputStream(src)));
		byte[] buffer = new byte[8192];	//buffers data between IO streams
		
		ZipEntry entry = zipStream.getNextEntry();
		while(entry != null) {	//iterate through all entries
			File f = new File(destDir + entry.getName());	//desired file to be added
			f.getParentFile().mkdirs();	//create all missing directories
			f.createNewFile();			//create file
			
			BufferedOutputStream outStream = new BufferedOutputStream(new FileOutputStream(f));
			
			int bytesRead = zipStream.read(buffer);	//returns bytes read or -1
			while(bytesRead > 0) {
				outStream.write(buffer, 0, bytesRead);	//write data to new file
				bytesRead = zipStream.read(buffer);
			}
			
			outStream.close();
			
			entry = zipStream.getNextEntry();	//progress to next entry
		}
		zipStream.closeEntry();
		zipStream.close();
	}//unzipTo

}//Unzipper 
