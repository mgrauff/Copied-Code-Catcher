import java.io.BufferedInputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

public class FileProcessor {
    //definitely a more elegant way to do this, but this was quick
    //public Operators[] operatorList;
	Operators operators;
    public File processFile;
    //will manually change in source code if more operators are added
    public final int numOperators = 29; 
    Map<String,Integer> operatorMap; 
    
    //public class Operators{ 
      //  public int occurences = 0;
        //public String operator ="";
    //}


    public FileProcessor() throws FileNotFoundException {
        //=, +, -, *, /, %, ++, --, +=, -=, *=, /=, %=, !, ==, !=, >, <, <=, >=, &&, ||, ~, <<, >>, >>>, &, ^, |
    	operatorMap = new HashMap<String,Integer>();
    	
    	//It is probable we won't want multiple of these Operator classes
    	//We should really just pass one as a reference to this
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
}
