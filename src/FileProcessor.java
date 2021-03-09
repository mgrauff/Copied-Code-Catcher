import java.io.BufferedInputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class FileProcessor {

    public File processFile;
    public HashMap<String, Integer> operators = new HashMap <String, Integer>();
    //add keywords as wanted
    //=, +, -, *, /, %, ++, --, +=, -=, *=, /=, %=, !, ==, !=, >, <, <=, >=, &&, ||, ~, <<, >>, >>>, &, ^, |
    private String[] keywords = {
        "=",
        "+",
        "-",
        "*",
        "/",
        "%",
        "++",
        "--",
        "+=",
        "-=",
        "*=",
        "/=",
        "%=",
        "!",
        "==",
        "!=",
        ">",
        "<",
        "<=",
        ">=",
        "&&",
        "||",
        "~",
        "<<",
        ">>",
        ">>>",
        "&",
        "^",
        "|"};

    public FileProcessor() {
        for(int i = 0; i < keywords.length; i++) {
            //put each keyword from the array of keywords into the map
            operators.put(keywords[i], 0);
        }
        processFile = new File("null");
    }//end default constructor

    public FileProcessor(File sourceCode) {
        processFile = sourceCode;
        for(int i = 0; i < keywords.length; i++) {
            //put each keyword from the array of keywords into the map
            operators.put(keywords[i], 0);
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
                        operators.put("=", operators.get("=") + 1);
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
