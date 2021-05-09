package base;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.jupiter.api.Test;

class CompareTester {

	@Test
	void sameReturns1() {
		HashMap<String, Integer> f1 = new HashMap<String,Integer>();
		ArrayList<String> operatorList = new ArrayList<String>();
		operatorList.add("+");
		operatorList.add("-");
		f1.put("+", 1);
		f1.put("-", 3);
		assertEquals(1,compareFiles(f1,f1,operatorList));
	}
	
	@Test
	void zeroReturnsNeg1() {
		HashMap<String, Integer> f1 = new HashMap<String,Integer>();
		ArrayList<String> operatorList = new ArrayList<String>();
		operatorList.add("+");
		operatorList.add("-");
		f1.put("+", 0);
		f1.put("-", 0);
		assertEquals(-1,compareFiles(f1,f1,operatorList));
	}
	
	@Test
	void differentReturnsNon1() {
		HashMap<String, Integer> f1 = new HashMap<String,Integer>();
		HashMap<String, Integer> f2 = new HashMap<String,Integer>();
		ArrayList<String> operatorList = new ArrayList<String>();
		operatorList.add("+");
		operatorList.add("-");
		f1.put("+", 1);
		f1.put("-", 3);
		f2.put("+", 3);
		f2.put("-", 1);
		assertTrue(1!=compareFiles(f1,f2,operatorList));
	}
	
	
	@Test
	void veryDifferentLessThanPoint4() {
		HashMap<String, Integer> f1 = new HashMap<String,Integer>();
		HashMap<String, Integer> f2 = new HashMap<String,Integer>();
		ArrayList<String> operatorList = new ArrayList<String>();
		operatorList.add("+");
		operatorList.add("-");
		f1.put("+", 100);
		f1.put("-", 300);
		f2.put("+", 3);
		f2.put("-", 1);
		assertTrue(.4>compareFiles(f1,f2,operatorList));
	}
	
	@Test
	void sameValueDiffFilesLargeNumberAreEqual() {
		HashMap<String, Integer> f1 = new HashMap<String,Integer>();
		HashMap<String, Integer> f2 = new HashMap<String,Integer>();
		ArrayList<String> operatorList = new ArrayList<String>();
		operatorList.add("+");
		operatorList.add("-");
		f1.put("+", 100);
		f1.put("-", 300);
		f2.put("+", 100);
		f2.put("-", 300);
		assertEquals(1,compareFiles(f1,f2,operatorList));
	}
	
	@Test
	void valueLessThan0ReturnsNeg1() {
		HashMap<String, Integer> f1 = new HashMap<String,Integer>();
		HashMap<String, Integer> f2 = new HashMap<String,Integer>();
		ArrayList<String> operatorList = new ArrayList<String>();
		operatorList.add("+");
		operatorList.add("-");
		f1.put("+", -1);
		f1.put("-", 2);
		f2.put("+", 3);
		f2.put("-", 1);
		assertEquals(-1,compareFiles(f1,f2,operatorList));
	}
	
	

	
	
	
	static double compareFiles(HashMap<String, Integer> f1, HashMap<String, Integer> f2, ArrayList<String> operatorList) {
		//Calculate 1 - RMSE to find similarity score
		double scaleSum = 0.0;
		int num1,num2, sum;
		int numOps = 0;
		int maxUse =0;
		for(String op:operatorList) {
			num1 = f1.get(op);
			num2 = f2.get(op);
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
	
		for(String op: operatorList) {
			num1 = f1.get(op);
			num2 = f2.get(op);
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
}



