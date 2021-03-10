import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Operators {
	ArrayList<String> operatorList;
	//note we can have another list for while,if, etc...
	//That could mean Operators is a bad name for the class... But are you gonna complain?
	
	public Operators(String operatorsFileName) throws FileNotFoundException {
		File file = new File(operatorsFileName);
		Scanner scnr = new Scanner(file);
		while(scnr.hasNext()) {
			operatorList.add(scnr.next());
		}
		scnr.close();
	}
}

