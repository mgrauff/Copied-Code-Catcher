package base;
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
		//Calculate 1 - RMSE to find similarity score
		double scaleSum = 0.0;
		int num1,num2, sum;
		int numOps = 0;
		int maxUse =0;
		for(String op:operators.operatorList) {
			num1 = f1.mapGet(op);
			num2 = f2.mapGet(op);
			
			if(num1 < 0 || num2< 0) {
				System.out.println("ERROR: Num appearances less than 0");
				return -1;
			}
			
			if(num1> maxUse) {
				maxUse = num1;
			}
			if(num2>maxUse) {
				maxUse = num2;
			}
		}
		
		//This shouldn't be possible.
		if(maxUse <= 0) {
			System.out.println("ERROR: No operators detected");
			return -1;
		}
	
		for(String op: operators.operatorList) {
			num1 = f1.mapGet(op);
			num2 = f2.mapGet(op);
			sum = num1+num2;
			double scale1,scale2;

			//If they're not used, we shouldn't include them in the avg
			if(sum > 0) {
				scale1 = num1/(1.0*maxUse);
				scale2 = num2/(1.0*maxUse);
				//Get difference squared
				scaleSum += Math.pow(Math.abs(scale1-scale2), 2);
				numOps ++;
			}

			
		}
		
		
		return 1.0 - Math.sqrt(scaleSum/(numOps*1.0));
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

