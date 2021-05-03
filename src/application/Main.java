package application;
	
import java.awt.Toolkit;
import java.io.File;
import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import base.Compare;
import base.FileProcessor;
import base.Operators;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;

public class Main extends Application {
	
	IntroScene intro;
	ChooseFileScene fileChoose;
	
	@Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Scene Test");
        double[][] scores = 
           {{ 1, .2, .3, .4, .5},
			{.2,  1, .6, .7, .8},
			{.3, .6,  1, .9, .1},
			{.4, .7, .9,  1, .2}, 
			{.5, .8, .1, .2,  1}};
        String[]  names = {"Matt", "Luke", "Paul", "Enoch", "Alex"};
        
        intro = new IntroScene(Toolkit.getDefaultToolkit().getScreenSize().getWidth(), Toolkit.getDefaultToolkit().getScreenSize().getHeight());   
        intro.begin.setOnAction(new EventHandler<ActionEvent>() {
        	@Override
        	public void handle(ActionEvent event) {
        		primaryStage.setScene(fileChoose);
        	}
        });
        
        fileChoose = new ChooseFileScene(Toolkit.getDefaultToolkit().getScreenSize().getWidth(), Toolkit.getDefaultToolkit().getScreenSize().getHeight());
        fileChoose.startComparisonButton.setOnAction(new EventHandler<ActionEvent>() {
        	@Override
        	public void handle(ActionEvent event) {
        		try {
        			List<File> selectedFiles = fileChoose.selectedFiles();
					Operators op = new Operators();
					FileProcessor[] fpAry= new FileProcessor[selectedFiles.size()];
					String[] names = new String[selectedFiles.size()];
					double[][] scores = new double[selectedFiles.size()][selectedFiles.size()];
					
					for(int i = 0; i < selectedFiles.size(); i++) {
						File f = selectedFiles.get(i);
						fpAry[i] = new FileProcessor(f, op);
						fpAry[i].read();
						names[i] = f.getName();
					}

					for(int i = 0; i < fpAry.length; i++) {
						for(int j = i+1; j < fpAry.length; j++) {
							Compare c = new Compare(fpAry[i], fpAry[j], op);
							double score = c.compareFiles();
							scores[i][j] = score;
							scores[j][i] = score;
						}
						scores[i][i] = 1;
					}
					
					for(int i = 0; i < scores.length; i++) {
						
						for(int j = 0; j < scores[i].length; j++) {
							
							
							DecimalFormat df = new DecimalFormat("0.000");
							
							System.out.print(df.format(scores[i][j]) + "\t");
							
						}						
						System.out.println("");
					}
					
	        		primaryStage.setScene(new ResultsScene(intro.getWidth(), intro.getHeight(), scores, names));
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
        	}
        });

        primaryStage.setScene(intro);
        primaryStage.setMaximized(true);
        primaryStage.show();
    }
	
	public static void main(String[] args) {
		launch(args);
	}
}
