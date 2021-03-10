import java.io.*;
import java.util.*;


public class FileProcessor {
	Operators operators;
    public File processFile;
    //Would be much nicer to read in operators from a .txt
    //Removes need for repetitive hard coding
    public final int numOperators = 29; 
    private Map<String,Integer> operatorMap; 
    


    public FileProcessor() throws FileNotFoundException {
        //=, +, -, *, /, %, ++, --, +=, -=, *=, /=, %=, !, ==, !=, >, <, <=, >=, &&, ||, ~, <<, >>, >>>, &, ^, |
    	operatorMap = new HashMap<String,Integer>();
    	
    	//It is probable we won't want multiple of these Operator classes
    	//But in our default constructor, we can do this
    	operators = new Operators("OperatorsInJava.txt"); 
    	for(String op: operators.operatorList) {
    		operatorMap.put(op, 0);
    	}
    	

        processFile = new File("null");
    }//end default constructor

    public FileProcessor(File sourceCode, Operators operators) {
        processFile = sourceCode;
    	operatorMap = new HashMap<String,Integer>();
    	
    	//Passing operators as an argument will be better
    	this.operators = operators;
    	for(String op: operators.operatorList) {
    		operatorMap.put(op, 0);
    	}
        
    }//end parameterized constructor

    public void read() {
        try {    
            char currChar;
            BufferedInputStream reader = new BufferedInputStream(
                new FileInputStream(processFile));
                    
                

            while(reader.available()!= 0) {
                try {
                    currChar = (char) reader.read(); 
                    if(currChar == '='){
                        operatorMap.put("=", operatorMap.get("=") + 1);
                    } //add the rest of the operator conditions
                } catch(EOFException e) {
                }
                
            }

            reader.close();
        } catch (IOException e) {
            System.out.println("Error opening file: " + processFile.getAbsolutePath());
        }
        
    }//end read
    
    
    //Returns a value for a map
    //#AbstractionForLife
    public int mapGet(String op) {
    	return operatorMap.get(op);
    }
}
