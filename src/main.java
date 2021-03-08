import java.io.File;

public class main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		File myFile = new File("C:/Users/StaatsPD17/git/Copied-Code-Catcher/Copied-Code-Catcher/src/example.txt");
		FileProcessor myProcessor = new FileProcessor(myFile);
		myProcessor.read();
	}

}
