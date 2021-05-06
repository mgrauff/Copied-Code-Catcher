package base;

public class Compare {
	private FileProcessor f1;
	private FileProcessor f2;
	private Operators operators;
	
	/**
	 * 
	 * @param file1, file2 - files to be compared
	 * @param operators -operators that are being used to compare
	 */
	public Compare(FileProcessor file1, FileProcessor file2, Operators operators) {
		f1 = file1;
		f2 = file2;
		this.operators = operators;
	}
	
	
	/**
	 * Compares files in compare class
	 * @return similarity score between files in compare
	 */
	public double compareFiles() {
		//Calculate 1 - RMSE to find similarity score
		double scaleSum = 0.0;
		int num1,num2, sum;
		int numOps = 0;
		int maxUse =0;
		//We first need the max number of times an operator appears between the two
		for(String op:operators.operatorList) {
			num1 = f1.mapGet(op);
			num2 = f2.mapGet(op);
			
			if(num1 < 0 || num2< 0) {
				//This shouldn't be possible
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
	
		//Now we need to actually calculate RMSE
		for(String op: operators.operatorList) {
			num1 = f1.mapGet(op);
			num2 = f2.mapGet(op);
			
			sum = num1+num2;
			double scale1,scale2;

			//If they're not used, we shouldn't include them
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
	
}

