
import java.io.*;
import java.util.*;


public class FileProcessor {
	Operators operators; //operators to be found in file 
    public File processFile; //file to be searched for operators
    private Map<String,Integer> operatorMap; //stores the operators and number of instances
    public boolean isCorrupt; //says whether file is corrupt or not
    enum state { //states for controlling the FSM
        LOOKING,
        GOT_OPERATOR,
        IN_LINE_COMMENT,
        IN_MULTI_LINE_COMMENT,
        IN_STRING,
        GOT_KEYWORD, //not currently used
        FINISHED //not currently used
    }//end state
    
    /**
     * Builds a basic FileProcessor 
     * object with the default operators
     */
    public FileProcessor() throws FileNotFoundException {
    	operatorMap = new HashMap<String,Integer>();
    	//But in our default constructor, we can do this
    	operators = new Operators("OperatorsInJava.txt"); 
    	for(String op: operators.operatorList) {
    		operatorMap.put(op, 0);
    	}
    	

        processFile = new File("null");
    }//end default constructor

    /**
     * Creates the FileProcessor object with 
     * file and operators passed as arguments
     * @param soureCode: The file to be processed
     * @param operators: The list of operators to be searched for in the file
     */
    public FileProcessor(File sourceCode, Operators operators) {
        processFile = sourceCode;
        isCorrupt = isFileCorrupt(this.processFile);
        if(isCorrupt) {
        	System.out.println("Corrupted file: " + processFile.getName());
        }
    	operatorMap = new HashMap<String,Integer>();
    	
    	//Passing operators as an argument will be better
    	this.operators = operators;
    	for(String op: operators.operatorList) {
    		operatorMap.put(op, 0);
    	}
        
    }//end parameterized constructor

    /**
     * multiCharOperators iterates through the list of operators
     * and detects operators that are two or more
     * characters long.
     * @return a HashSet of chars that are the start 
     * of mutlichar operators
     */
    public HashSet multiCharOperators() {
        HashSet <Character> mCharSet = new HashSet<Character>();
        for(String op: operators.operatorList) {
            if(op.length() >= 2)
                mCharSet.add(op.charAt(0));
        }
        return mCharSet;
    }

    /**
     * operatorEndings 
     * @return
     */
    public HashSet operatorEndings() {
        HashSet <String> laterCharSet = new HashSet<String>();
        for(String op: operators.operatorList) {
            if(op.length() >= 2)
                laterCharSet.add(op.substring(1));
        }
        return laterCharSet;
    }

    /**
     * Processes the file attribute of the FileProcessor and
     * increments the operatorMaps operators when they are detected
     * within the file.
     */
    public void read() {
    	if(isCorrupt) {
    		System.out.println("Cannot read corrupt file: "+ processFile.getName());
    		return;
    	}
    	
        Scanner sourceScanner = null; //scanner for reading file
        state control = state.LOOKING; //start out in the looking state
        //hold characters of interest in the searching process
        char currChar = ' ', prevChar = ' ', backslash = ' ';
        HashSet<Character> multiCSet = multiCharOperators(); 
        HashSet<String> operatorEndings = operatorEndings();
        
        try { //initialize sourceScanner
            sourceScanner = new Scanner(this.processFile);    
        } catch (FileNotFoundException e) {
            System.out.println("Error opening file");
        }//try catch
        

        
        
        sourceScanner.useDelimiter(""); //important for reading one char at a time
        
        while (sourceScanner.hasNext()) { //Finite State Machine
            
            switch (control) {
                case LOOKING: //look for the start of a new operator
                currChar = sourceScanner.next().charAt(0);
                    if (multiCSet.contains(currChar)) {
                        /*save the current char before next cycle
                        if the operator could be the beginning of a
                        multi-character operator*/
                        prevChar = currChar; //save the char for the next cycle
                        control = state.GOT_OPERATOR; //go to the GOT_OPERATOR state                    
                    //if the character is not the beginning of a multicharacter operator
                    } else if (currChar == '~') { 
                        operatorMap.put("~", operatorMap.get("~") + 1);
                    //a string is starting
                    } else if (currChar == '"') {
                        control = state.IN_STRING;
                    }
                break;
                //end LOOKING state

                case GOT_OPERATOR:
                currChar = sourceScanner.next().charAt(0);
                String totalOperator = "" + prevChar;
                    //disregard whitespace separating operator parts
                    if (currChar != ' ' ||
                        currChar != '\t' ||
                        currChar != '\n') { 
                        //check for comments    
                        if(prevChar == '/') {
                            if(currChar == '/')
                                control = state.IN_LINE_COMMENT;
                            if(currChar =='*') 
                                control = state.IN_MULTI_LINE_COMMENT;
                        } else { //if not in comment                     
                            if(operatorEndings.contains("" + currChar)) {
                                /*keep track of the total operator if the
                                current char is part of an operator ending*/
                                totalOperator = totalOperator + currChar; 
                                operatorMap.put(totalOperator, operatorMap.get(totalOperator) + 1);
                                control = state.LOOKING;
                            } else {
                                operatorMap.put(totalOperator, operatorMap.get(totalOperator) + 1);
                                control = state.LOOKING;
                            }
                        }//operator else ifs
                    }//whitespace if
                break; 
                //end GOT_OPERATOR

                case IN_STRING:
                    if(inString(sourceScanner)) {
                        control = state.LOOKING;
                    }
                break;
                //end IN_STRING

                case GOT_KEYWORD:
                //yet to be implemented
                break;
                //end GOT_KEYWORD

                case IN_LINE_COMMENT:
                //read until a newline character
                currChar = sourceScanner.next().charAt(0);
                if(currChar == '\n') {
                    control = state.LOOKING; //back to looking for operators
                }
                break;
                //end IN_LINE_COMMENT

                case IN_MULTI_LINE_COMMENT:
                if(multiLineComment(sourceScanner)) {
                    control = state.LOOKING;
                }
                break;
                //end IN_MULTI_LINE_COMMENT
                
                case FINISHED:
                //yet to be implemented
                break;
                //end FINISHED

            }//end switch

        }//end while FSM

    }//end read

    public boolean inString(Scanner s) {
        char currChar = ' ', backslash = ' ';
        boolean done = false; 

        currChar = s.next().charAt(0);
                    if (currChar == '\\') {
                        backslash = '\\'; //store potential escape character
                    } else {
                        backslash = '1'; //in case there are other escape characters before the end of the string
                    }
                    
                    if (currChar == '"' && backslash != '\\') { 
                        //only end the quotation if the '"' is the end of the quote and not an escape sequence
                        done = true;
                    }

        return done;
    }

    public boolean multiLineComment(Scanner s) {
        char currChar = ' ', prevChar = ' ';
        boolean done = false;
        
        currChar = s.next().charAt(0);
        if (prevChar == '*') {
            if (currChar == '/') { //ends multiline comment
                done = true;
            }
        }
        if(currChar == '*') { //potentially ends the comment
            prevChar = currChar; //save the * character
        } else {
            //only save the * character if it directly precedes the /
            prevChar = ' ';
        }
        return done;
    }
    
    
    //Returns a value for a map
    //#AbstractionForLife
    public int mapGet(String op) {
    	return operatorMap.get(op);
    }
    
    
    
    /**
     * Determines if a file is corrupt
     * @param file
     * @return boolean value
     */
	private boolean isFileCorrupt(File file) {
		if(!file.canRead()) {
			System.out.println("File was unreadable");
			return true;
		}
		
		
		if(file.length()<= 0) {
			System.out.println("File was empty");
			return true;
		}

		Scanner scnr;
		try {
			scnr = new Scanner(file);
			scnr.next();
			scnr.close();
		} catch (Exception e) {
			System.out.println("Scanner.next() did not exist");
			return true;
		}
		
		
		
		return false;
	}
}
