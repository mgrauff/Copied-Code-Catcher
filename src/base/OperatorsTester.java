package base;
import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;

import org.junit.jupiter.api.Test;

class OperatorsTester {

	//Testing if the Operators file is present
	@Test 
	void testOperatorsFileExists() throws FileNotFoundException {
		try {
				Operators operators = new Operators();
				operators.toString();
				
		} catch (FileNotFoundException FNFE) {
			fail("Operators file not found.");
		}
	}
	
	//Testing if operators arraylist has been populated with operators
	@Test
	void testOperatorsArrayPopulated() throws FileNotFoundException {
		
		Operators operators = new Operators();
		
		assertTrue(operators.operatorList.size() > 0);
		assertTrue(operators.operatorList.contains("="));
		assertTrue(operators.operatorList.contains("+"));
		assertTrue(operators.operatorList.contains("-"));
		assertTrue(operators.operatorList.contains("*"));
		assertTrue(operators.operatorList.contains("/"));
		assertTrue(operators.operatorList.contains("%"));
		assertTrue(operators.operatorList.contains("++"));
		assertTrue(operators.operatorList.contains("--"));
		assertTrue(operators.operatorList.contains("+="));
		assertTrue(operators.operatorList.contains("-="));
		assertTrue(operators.operatorList.contains("*="));
		assertTrue(operators.operatorList.contains("/="));
		assertTrue(operators.operatorList.contains("%="));
		assertTrue(operators.operatorList.contains("^="));
		assertTrue(operators.operatorList.contains("!"));
		assertTrue(operators.operatorList.contains("=="));
		assertTrue(operators.operatorList.contains("!="));
		assertTrue(operators.operatorList.contains(">"));
		assertTrue(operators.operatorList.contains("<"));
		assertTrue(operators.operatorList.contains("<="));
		assertTrue(operators.operatorList.contains(">="));
		assertTrue(operators.operatorList.contains("&&"));
		assertTrue(operators.operatorList.contains("||"));
		assertTrue(operators.operatorList.contains("~"));
		assertTrue(operators.operatorList.contains("<<"));
		assertTrue(operators.operatorList.contains(">>"));
		assertTrue(operators.operatorList.contains(">>>"));
		assertTrue(operators.operatorList.contains("&"));
		assertTrue(operators.operatorList.contains("^"));
		assertTrue(operators.operatorList.contains("|"));
	}
	

}
