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
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Main extends Application {
	
	IntroScene intro;
	ChooseFileScene fileChoose;
	final public static Color BEIGE = Color.web("0xf3f4ed");
	final public static String BEIGE_COLOR = "f3f4ed";
	final public static Color GRAYISH_CYAN = Color.web("0x536162");
	final public static String GRAYSIH_CYAN_COLOR = "f3f4ed";
	final public static Color DARK_GREY = Color.web("0x424642");
	final public static String DARK_GREY_COLOR = "f3f4ed";
	final public static Color ORANGE = Color.web("0xc06014");
	final public static String ORANGE_COLOR = "f3f4ed";
	
	
	
	@Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Copied Code Catcher");
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
        primaryStage.getIcons().add(new Image("file:src/Robin.png"));
        primaryStage.show();
    }
	
	public static void setRobinButtonStyle(Button button) {
		
		DropShadow shadow = new DropShadow();
		button.setStyle("-fx-background-color: #" + Main.BEIGE_COLOR + "; -fx-border-width: 5px;");
		//Adding the shadow when the mouse cursor is on
		button.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
		    button.setEffect(shadow);
		});
		
		//Removing the shadow when the mouse cursor is off
		button.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
		    button.setEffect(null);
		});
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
