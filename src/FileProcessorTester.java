import static org.junit.Assert.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.junit.Test;

public class FileProcessorTester {

		File myFile = new File("src/testFileProcessor.txt");
        Operators o = null;
        FileWriter w = null;
	
	@Test
	public void testBlankFile() throws IOException {
		try {
            o = new Operators("OperatorsInJava.txt");
            w = new FileWriter(myFile);
        } catch (Exception e) {}
		FileProcessor f = new FileProcessor(myFile, o);
		w.write("");
		w.flush();
        f.read();

        for(String op: f.operators.operatorList) {
            assertEquals(0, f.mapGet(op));
    	}
	}//testBlankFile

	@Test
	public void addOperator() throws IOException {
        try {
            o = new Operators("OperatorsInJava.txt");
            w = new FileWriter(myFile);
        } catch (Exception e) {}
		FileProcessor f = new FileProcessor(myFile, o);

		w.write("adsfasdf+ 0;");
		w.flush();
        f.read();

        for(String op: f.operators.operatorList) {
            assertEquals(0, f.mapGet(op));
    	}
	}//addOperator

	@Test
	public void addMultiCharOperator() throws IOException {
        try {
            o = new Operators("OperatorsInJava.txt");
            w = new FileWriter(myFile);
        } catch (Exception e) {}
		FileProcessor f = new FileProcessor(myFile, o);

        w.write(">> afjf}");
		w.flush();
        f.read();

        for(String op: f.operators.operatorList) {
            assertEquals(0, f.mapGet(op));
    	}
	}//addMultiCharOperator

	@Test
	public void singleLineCommentOperator() throws IOException {
        try {
            o = new Operators("OperatorsInJava.txt");
            w = new FileWriter(myFile);
        } catch (Exception e) {}
		FileProcessor f = new FileProcessor(myFile, o);

        w.write("//int i = 12 * 15;");
		w.flush();
        f.read();

        for(String op: f.operators.operatorList) {
            assertEquals(0, f.mapGet(op));
    	}
	}//singleLineCommentOperator

	@Test
	public void multiLineCommentOperator() throws IOException {
        try {
            o = new Operators("OperatorsInJava.txt");
            w = new FileWriter(myFile);
        } catch (Exception e) {}
		FileProcessor f = new FileProcessor(myFile, o);

		w.write("\n/*for(int i = 0; i<10; i++) {\n" + 
        "i += 8/9; \n }/*");
		w.flush();
        f.read();

        for(String op: f.operators.operatorList) {
            assertEquals(0, f.mapGet(op));
    	}
	}//multiLineCommentOperator

	@Test
	public void singleQuoteOperator() throws IOException {
        try {
            o = new Operators("OperatorsInJava.txt");
            w = new FileWriter(myFile);
        } catch (Exception e) {}
		FileProcessor f = new FileProcessor(myFile, o);

        w.write("System.out.println(\"i /= 9;\");");
		w.flush();
        f.read();

        for(String op: f.operators.operatorList) {
            assertEquals(0, f.mapGet(op));
    	}
	}//singleQuoteOperator


}
