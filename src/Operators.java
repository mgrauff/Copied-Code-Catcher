import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Operators {
	ArrayList<String> operatorList;
	
	public Operators(String operatorsFileName) throws FileNotFoundException {
		File file = new File(operatorsFileName);
		Scanner scnr = new Scanner(file);
		while(scnr.hasNext()) {
			operatorList.add(scnr.next());
		}
		scnr.close();
	}
}

