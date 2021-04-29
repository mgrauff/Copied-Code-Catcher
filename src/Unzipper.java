import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

public class Unzipper {
	boolean error = false; //IF error exists in zipped folder
	
	public static boolean isZipped(File f) throws IOException {
		boolean isZipped = false;

		
		DataInputStream di = new DataInputStream(new FileInputStream(f));	
		try {	
			long zipKey = di.readInt();
			
			//all zip files start with one of three specific integers
			if(zipKey == 0x504B0304 || zipKey == 0x504B0506 || zipKey == 0x504B0708) {
				isZipped = true;
			}
		} catch (IOException e) {
			throw e;
		} finally {
			di.close();
		}
		
		return isZipped;
	}
	public static boolean isZipped(String src) throws IOException {
		return isZipped(new File(src));
	}
	
	private String src;	//location of zipped folder
	
	/**
	 * Creates an Unzipper object that allows a zipped folder to be extracted to a new location
	 * 
	 * @param src the relative or absolute path of the zipped folder to be extracted
	 * @throws IOException 
	 */
	public Unzipper(String src) {
		File f = new File(src);
		try {
			if(!f.exists()) {	//check if provided file exists
				throw new FileNotFoundException("The provided filepath does not exist");
			}
			
			//verify that the source is zipped
			if(!Unzipper.isZipped(f)) {
				throw new IOException("The provided file is not zipped");
			}
			
			//Verify that the source is not corrupted
			if(isZipCorrupt(f)) {
				throw new IOException("The provided file is corrupt");
			}
		}
		catch(Exception E) {
			//NOTE: WE CANNOT USE THIS ZIPPED FOLDER
			//TODO: Notify user of issue
			error = true;
			System.err.println(E);
		}
		
		this.src = src;
	}//constructor 
	
	
	
	
	/**
	 * Extracts all files from the zipped folder and copies them to the provided directory 
	 * 
	 * @param destDir the directory to which all files should be copied
	 * @throws IOException 
	 * @returns ArrayList of Unzipped files
	 */
	public ArrayList<File> unzipTo(String destDir) throws IOException {
		
		ArrayList<File> entryListOut = new ArrayList<File>(); //create list for returning files
		
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
			
			entryListOut.add(new File(destDir + entry.getName())); //Add file to list
			
			entry = zipStream.getNextEntry();	//progress to next entry
		}
		zipStream.closeEntry();
		zipStream.close();
		
		return entryListOut;
		
	}//unzipTo
	
	/**
	 * 
	 * @param file to check if corrupt
	 * @return true if corrupt
	 */
	 public boolean isZipCorrupt(final File file) {
		    ZipFile zFile;
		    try {
		        zFile = new ZipFile(file);
		        zFile.close();
		        return false;
		        
		        
		    } catch (Exception e) {
		    	System.out.println(file.getName() + " was corrupt");
		    	
		        return true;
		    }

		}

	
}//Unzipper 
