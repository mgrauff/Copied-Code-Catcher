package base;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class testFileProcessor {
    
    public static void main(String[] args) throws IOException{
        //Variable declaration
        File myFile = null;
        myFile = new File("src/testFileProcessor.txt");
        Operators o = null;
        FileWriter w = null;
        int errors = 0; 
        int arg = 1;
        try {
            o = new Operators();
            w = new FileWriter(myFile);
           
            
        }
        catch (Exception e) {}
        
        FileProcessor f = new FileProcessor(myFile, o);

        // HashSet<String> e = f.multiCharOperators();
        // HashSet<String> l = f.operatorEndings();
        //     for(String op: f.operators.operatorList) {
        //         System.out.print(op + ", ");
        //     }
        //     System.out.println();
        //     System.out.println(e.toString());
        //     System.out.println(l.toString());

        if (arg == 1) {
        myFile = new File("src/testFileProcessor.txt");
        //Scanner myScanner = new Scanner(myFile);
        //Test 1 Blank file
        //w.write();
        for(String op: f.operators.operatorList) {
            if(f.mapGet(op) >  0) { //no operators should be added
                errors++;
            }
    	}
        w.flush();
        f.read();

        //Test 2 add +
        w.write("adsfasdf+ 0;");
        w.flush();
        f = new FileProcessor(myFile, o);
        f.read();
        if (f.mapGet("+") != 1) {
            errors++;
        }
        
        //Test 3 add multicharacter operator
        w.write(">> afjf}");
        w.flush();
        f = new FileProcessor(myFile, o);
        f.read();
        if(f.mapGet(">>") != 1)
            errors++;

        //Test 4 add operator within single line comment
        w.write("//int i = 12 * 15;");
        w.flush();
        f = new FileProcessor(myFile, o);
        f.read();
        if(f.mapGet("*") != 0)
            errors++;
        
        //Test 5 add operator within multi-line comment
        w.write("\n/*for(int i = 0; i<10; i++) {\n" + 
        "i += 8/9; \n }/*");
        w.flush();
        f = new FileProcessor(myFile, o);
        f.read();
        if(f.mapGet("+=") != 0) {
            errors++;
        }
        
        //Test 6 add operator within string quote
        w.write("System.out.println(\"i /= 9;\");");
        w.flush();
        f = new FileProcessor(myFile, o);
        f.read();
        if(f.mapGet("/=") != 0) {
            errors++;
        }
              
        System.out.println("Testing finished with: " + errors + " errors"); 
    } else if (arg == 0) {
        myFile = new File("src/example.txt");
        f = new FileProcessor(myFile, o);
        f.read();
    }

        
    }//end main

}//end testFileProcessor
