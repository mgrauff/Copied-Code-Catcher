package base;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Operators {
	ArrayList<String> operatorList;
	//note we can have another list for while,if, etc...
	//That could mean Operators is a bad name for the class... But are you gonna complain?
	
	public Operators() throws FileNotFoundException {
		//File file = new File("src/"+operatorsFileName);
		File file = new File("src/OperatorsInJava.txt");
		Scanner scnr = new Scanner(file);
		operatorList = new ArrayList<String>();
		while(scnr.hasNext()) {
			operatorList.add(scnr.nextLine());
		}
		scnr.close();
	}
}

