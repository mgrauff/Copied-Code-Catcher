import java.io.*;
import java.util.*;


public class FileProcessor {
	Operators operators;
    public File processFile;
    //Would be much nicer to read in operators from a .txt
    //Removes need for repetitive hard coding
    public final int numOperators = 29; 
    private Map<String,Integer> operatorMap; 
    enum state {
        LOOKING,
        GOT_OPERATOR,
        IN_LINE_COMMENT,
        IN_MULTI_LINE_COMMENT,
        IN_STRING,
        GOT_KEYWORD, //not currently used
        FINISHED //not currently used
    }
    

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
        Scanner sourceScanner = null; //scanner for reading file
        System.out.println("check read");
        state control = state.LOOKING;
        char currChar = ' ', prevChar = ' ', backslash = ' ';;
        try {
            sourceScanner = new Scanner(this.processFile);
            sourceScanner.useDelimiter("");
        } catch (FileNotFoundException e) {
            System.out.println("Error opening file");
        }
        
        while (sourceScanner.hasNext()) {
            
            switch (control) {
                case LOOKING:
                currChar = sourceScanner.next().charAt(0);
                    if (currChar == '=' ||
                        currChar == '<' ||
                        currChar == '>' ||
                        currChar == '%' ||
                        currChar == '/' ||
                        currChar == '*' ||
                        currChar == '+' ||
                        currChar == '-' ||
                        currChar == '&' ||
                        currChar == '|' ||
                        currChar == '^') {
                        /*save the current char before next cycle
                        if the operator could be the beginning of a
                        multi-character operator*/
                        prevChar = currChar; 
                        control = state.GOT_OPERATOR;
                    } else if (currChar == '~') {
                        operatorMap.put("~", operatorMap.get("~") + 1);
                    } else if (currChar == '"') {
                        control = state.IN_STRING;
                    }
                break;
                case GOT_OPERATOR:
                currChar = sourceScanner.next().charAt(0);
                    if (currChar != ' ' ||
                        currChar != '\t' ||
                        currChar != '\n') { 
                    //disregard whitespace separating operator parts
                        if(prevChar == '=') {
                            if (currChar == '=') {
                                operatorMap.put("==", operatorMap.get("==") + 1);
                                control = state.LOOKING;
                            } else {
                            operatorMap.put("=", operatorMap.get("=") + 1);  
                            control = state.LOOKING;
                            }
                        } else if (prevChar == '<') {
                            if (currChar == '=') {
                                operatorMap.put("<=", operatorMap.get("<=") + 1);
                                control = state.LOOKING;
                            } else if (currChar == '<') {
                                operatorMap.put("<<", operatorMap.get("<<") + 1);  
                                control = state.LOOKING;
                            } else {
                                operatorMap.put("<", operatorMap.get("<") + 1);  
                                control = state.LOOKING;
                            }
                        } else if (prevChar == '>') {
                            if (currChar == '=') {
                                operatorMap.put(">=", operatorMap.get(">=") + 1);
                                control = state.LOOKING;
                            } else if (currChar == '>') {
                                operatorMap.put(">>", operatorMap.get(">>") + 1);  
                                control = state.LOOKING;
                            }else {
                                operatorMap.put(">", operatorMap.get(">") + 1);  
                                control = state.LOOKING;
                            }
                        } else if (prevChar == '*') {
                            if (currChar == '=') {
                                operatorMap.put("*=", operatorMap.get("*=") + 1);
                                control = state.LOOKING;
                            } else {
                                operatorMap.put("*", operatorMap.get("*") + 1);  
                                control = state.LOOKING;
                            }
                        } else if (prevChar == '+') {
                            if (currChar == '=') {
                                operatorMap.put("+=", operatorMap.get("+=") + 1);
                                control = state.LOOKING;
                            } else if (currChar == '+') {
                                operatorMap.put("++", operatorMap.get("++") + 1);
                                control = state.LOOKING;
                            } else {
                                operatorMap.put("+", operatorMap.get("+") + 1);  
                                control = state.LOOKING;
                            }
                        } else if (prevChar == '-') {
                            if (currChar == '=') {
                                operatorMap.put("-=", operatorMap.get("-=") + 1);
                                control = state.LOOKING;
                            } else if (currChar == '-') {
                                operatorMap.put("--", operatorMap.get("--") + 1);
                                control = state.LOOKING;
                            } else {
                                operatorMap.put("-", operatorMap.get("-") + 1);  
                                control = state.LOOKING;
                            }
                        } else if (prevChar == '^') {
                            if (currChar == '=') {
                                operatorMap.put("^=", operatorMap.get("^=") + 1);
                                control = state.LOOKING;
                            } else {
                                operatorMap.put("^", operatorMap.get("^") + 1);  
                                control = state.LOOKING;
                            }
                        } else if (prevChar == '%') {
                            if (currChar == '=') {
                                operatorMap.put("%=", operatorMap.get("%=") + 1);
                                control = state.LOOKING;
                            } else {
                                operatorMap.put("%", operatorMap.get("%") + 1);  
                                control = state.LOOKING;
                            }
                        } else if (prevChar == '&') {
                            if (currChar == '&') {
                                operatorMap.put("&&", operatorMap.get("&&") + 1);
                                control = state.LOOKING;
                            } else {
                                operatorMap.put("&", operatorMap.get("&") + 1);  
                                control = state.LOOKING;
                            }
                        } else if (prevChar == '|') {
                            if (currChar == '|') {
                                operatorMap.put("||", operatorMap.get("||") + 1);
                                control = state.LOOKING;
                            } else {
                                operatorMap.put("|", operatorMap.get("|") + 1);  
                                control = state.LOOKING;
                            }
                        } else if (prevChar == '/') {
                            if (currChar == '=') {
                                operatorMap.put("/=", operatorMap.get("/=") + 1);
                                control = state.LOOKING;
                            } else if (currChar == '/') {
                                control = state.IN_LINE_COMMENT;
                            } else if (currChar == '*') {
                                control = state.IN_MULTI_LINE_COMMENT;
                            } else {
                                operatorMap.put("/", operatorMap.get("/") + 1);  
                                control = state.LOOKING;
                            }
                        }
                    }
                break;
                case IN_STRING:
                    currChar = sourceScanner.next().charAt(0);
                    if (currChar == '\\') {
                        backslash = '\\'; //store potential escape character
                    } else {
                        backslash = '1';
                    }
                    
                    if (currChar == '"' && backslash == '\\') { 
                        //only end the quotation if the '"' is the end of the quote
                        control = state.LOOKING;
                    }
                break;
                case GOT_KEYWORD:
                break;
                case IN_LINE_COMMENT:
                //read the next line so the comment will be ignored
                sourceScanner.nextLine(); 
                control = state.LOOKING;
                break;
                case IN_MULTI_LINE_COMMENT:
                currChar = sourceScanner.next().charAt(0);
                if (currChar == '\\') {
                    backslash = '\\'; //store potential escape character
                } else {
                    backslash = '1';
                }
                if (prevChar == '*') {
                    if (currChar == '/') { //ends multiline comment
                        control = state.LOOKING;
                    }
                }
                if(currChar == '*') { //potentially ends the comment
                    prevChar = currChar; //save the * character
                    control = state.IN_MULTI_LINE_COMMENT;
                } else {
                    //only save the * character if it directly precedes the /
                    prevChar = ' ';
                }
                 
                
                
                break;
                case FINISHED:
                break;


            }
            // if(currChar == '\n')
            // System.out.println();
            // else
            // System.out.print(currChar);

        }
        
        for(String op: operators.operatorList) {
    		System.out.println(op + " occurs: " + operatorMap.get(op));
    	}

        // try {    
            
        //     BufferedInputStream reader = new BufferedInputStream(
        //         new FileInputStream(processFile));
                    
                

        //     while(reader.available()!= 0) {
        //         try {
        //             currChar = (char) reader.read(); 
        //             if(currChar == '='){
        //                 operatorMap.put("=", operatorMap.get("=") + 1);
        //             } //add the rest of the operator conditions
        //         } catch(EOFException e) {
        //         }
                
        //     }

        //     reader.close();
        // } catch (IOException e) {
        //     System.out.println("Error opening file: " + processFile.getAbsolutePath());
        // }
        
    }//end read
    
    
    //Returns a value for a map
    //#AbstractionForLife
    public int mapGet(String op) {
    	return operatorMap.get(op);
    }
}
