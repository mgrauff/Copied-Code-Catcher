import java.util.Map;

public class Compare {
	private FileProcessor f1;
	private FileProcessor f2;
	Operators operators;
	
	public Compare(FileProcessor file1, FileProcessor file2, Operators operators) {
		f1 = file1;
		f2 = file2;
		this.operators = operators;
	}
	
	
	public double compareFiles() {
		double score = 0;
		for(String op: operators.operatorList) {
			//Do math
		}
		
		return score;
	}
	
	/*
	 * compareFiles(file1, file2)

		score = 0
		for each operator in operators:
			ratio = num operartor in file1/ num operator in file 2
			r1 = file1.map.get(operator)
			sum = numOperator in file1+ numOperator in file 2
			score += sum*ratio
	 */
}

