import java.util.Map;

public class Compare {
	private FileProcessor f1;
	private FileProcessor f2;
	private Operators operators;
	
	public Compare(FileProcessor file1, FileProcessor file2, Operators operators) {
		f1 = file1;
		f2 = file2;
		this.operators = operators;
	}
	
	
	public double compareFiles() {
		double score = 0;
		double ratio;
		int num1,num2, sum;
		int numOps = 0;
		for(String op: operators.operatorList) {
			num1 = f1.mapGet(op);
			num2 = f2.mapGet(op);
			sum = num1+num2;
			//If they're not used, we shouldn't include them in the avg
			if(sum > 0) {
				//if both numbers aren't used, who cares
				if(num2 != 0 ) {
					ratio = num1/num2;
				}
				else {
					ratio = 1;
				}
				score+= sum*ratio;
				numOps ++;
			}

			
		}
		
		return score/numOps;
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

