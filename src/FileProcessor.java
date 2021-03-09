import java.io.BufferedInputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class FileProcessor {
    //definitely a more elegant way to do this, but this was quick
    public Operators[] operatorList;
    public File processFile;
    //will manually change in source code if more operators are added
    public final int numOperators = 29; 
    public class Operators{ 
        public int occurences = 0;
        public String operator ="";
    }

    //chen
    public FileProcessor() {
        //=, +, -, *, /, %, ++, --, +=, -=, *=, /=, %=, !, ==, !=, >, <, <=, >=, &&, ||, ~, <<, >>, >>>, &, ^, |
        operatorList = new Operators[numOperators];
        for(int i = 0; i < numOperators; i++) {
            operatorList[i] = new Operators();
            operatorList[i].occurences = 0;
        }
        operatorList[0].operator = "=";
        operatorList[1].operator = "+";
        operatorList[2].operator = "-";
        operatorList[3].operator = "*";
        operatorList[4].operator = "/";
        operatorList[5].operator = "%";
        operatorList[6].operator = "++";
        operatorList[7].operator = "--";
        operatorList[8].operator = "+=";
        operatorList[9].operator = "-=";
        operatorList[10].operator = "*=";
        operatorList[11].operator = "/=";
        operatorList[12].operator = "%=";
        operatorList[13].operator = "!=";
        operatorList[14].operator = "==";
        operatorList[15].operator = "!=";
        operatorList[16].operator = ">";
        operatorList[17].operator = "<";
        operatorList[18].operator = "<=";
        operatorList[19].operator = ">=";
        operatorList[20].operator = "&&";
        operatorList[21].operator = "||";
        operatorList[22].operator = "~";
        operatorList[23].operator = "<<";
        operatorList[24].operator = ">>";
        operatorList[25].operator = "<<<";
        operatorList[26].operator = "&";
        operatorList[27].operator = "^";
        operatorList[28].operator = "|";
        processFile = new File("null");
    }//end default constructor

    public FileProcessor(File sourceCode) {
        processFile = sourceCode;
        operatorList = new Operators[numOperators];
        for(int i = 0; i < numOperators; i++) {
            operatorList[i] = new Operators();
            operatorList[i].occurences = 0;
        }
        operatorList[0].operator = "=";
        operatorList[1].operator = "+";
        operatorList[2].operator = "-";
        operatorList[3].operator = "*";
        operatorList[4].operator = "/";
        operatorList[5].operator = "%";
        operatorList[6].operator = "++";
        operatorList[7].operator = "--";
        operatorList[8].operator = "+=";
        operatorList[9].operator = "-=";
        operatorList[10].operator = "*=";
        operatorList[11].operator = "/=";
        operatorList[12].operator = "%=";
        operatorList[13].operator = "!=";
        operatorList[14].operator = "==";
        operatorList[15].operator = "!=";
        operatorList[16].operator = ">";
        operatorList[17].operator = "<";
        operatorList[18].operator = "<=";
        operatorList[19].operator = ">=";
        operatorList[20].operator = "&&";
        operatorList[21].operator = "||";
        operatorList[22].operator = "~";
        operatorList[23].operator = "<<";
        operatorList[24].operator = ">>";
        operatorList[25].operator = "<<<";
        operatorList[26].operator = "&";
        operatorList[27].operator = "^";
        operatorList[28].operator = "|";
        
    }//end parameterized constructor

    public void read() {
        try {    
            String processString = "";
            boolean done = false;
            char currChar;
            BufferedInputStream reader = new BufferedInputStream(
                new FileInputStream(processFile));
                    
                

            while(reader.available()!= 0) {
                try {
                    currChar = (char) reader.read(); 
                    if(currChar == '='){
                        operatorList[0].occurences += 1;
                    } //add the rest of the operator conditions
                } catch(EOFException e) {
                    done = true;
                }
                
            }

            reader.close();
        } catch (IOException e) {
            System.out.println("Error opening file: " + processFile.getAbsolutePath());
        }
        
    }//end read
}
