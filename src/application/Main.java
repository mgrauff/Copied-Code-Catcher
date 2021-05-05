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
import javafx.stage.Screen;
import javafx.stage.Stage;

public class Main extends Application {
	
	IntroScene intro;
	ChooseFileScene fileChoose;
	StackOverFlowScene pasteCode;
	//ResultsScene results;
	final public static Color BEIGE = Color.web("0xf3f4ed");
	final public static String BEIGE_COLOR = "f3f4ed";
	final public static Color GRAYISH_CYAN = Color.web("0x536162");
	final public static String GRAYISH_CYAN_COLOR = "536162";
	final public static Color DARK_GREY = Color.web("0x424642");
	final public static String DARK_GREY_COLOR = "424642";
	final public static Color ORANGE = Color.web("0xc06014");
	final public static String ORANGE_COLOR = "c06014";
	
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
        
        fileChoose = new ChooseFileScene(Screen.getPrimary().getBounds().getWidth(), Screen.getPrimary().getBounds().getHeight());
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
					
					ResultsScene results = new ResultsScene(Screen.getPrimary().getBounds().getWidth(), Screen.getPrimary().getBounds().getHeight(), scores, names);
	        		primaryStage.setScene(results);
	        		
	        		results.backButton.setOnAction(new EventHandler<ActionEvent>() {
	                	
	                	@Override
	                	public void handle(ActionEvent event) {
	                		primaryStage.setScene(fileChoose);
	                	}
	                	
	                });
	        		
				} catch (Exception e) {
					e.printStackTrace();
				}
        		
        	}
        });
        
        fileChoose.stackOverflowButton.setOnAction(new EventHandler<ActionEvent>() {
        	
        	@Override
        	public void handle(ActionEvent event) {

	        	primaryStage.setScene(pasteCode);

        	}
        });
        
        pasteCode = new StackOverFlowScene(Screen.getPrimary().getBounds().getWidth(), Screen.getPrimary().getBounds().getHeight());
        pasteCode.backButton.setOnAction(new EventHandler<ActionEvent>() {
        	
        	@Override 
        	public void handle(ActionEvent event) {
        		primaryStage.setScene(fileChoose);
        	}
        	
        });
        
        
        

        primaryStage.setScene(intro);
        primaryStage.setMaximized(true);
        primaryStage.getIcons().add(new Image("file:src/HoodRobinRobin.png"));
        primaryStage.show();
    }
	
	public static void setRobinButtonStyle(Button button) {
		
		DropShadow shadow = new DropShadow();
		button.setStyle("-fx-background-color: #" + Main.GRAYISH_CYAN_COLOR + "; -fx-border-width: 5px; -fx-text-fill: #" + Main.BEIGE_COLOR + ";");
		//Adding the shadow when the mouse cursor is on
		button.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
		    button.setEffect(shadow);
		});
		
		//Removing the shadow when the mouse cursor is off
		button.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
		    button.setEffect(null);
		});
	}
	
	//This method is used to clear our files folder in src
	//Note this is an incredibly dangerous method and can ONLY BE USED ON SRC/FILES
	private static void clearFilesFolder(File folder) {
	    File[] files = folder.listFiles();
	    if(files!=null) { //some JVMs return null for empty dirs
	        for(File f: files) {
	            if(f.isDirectory()) {
	                clearFilesFolder(f);
	            } else {
	                f.delete();
	            }
	        }
	    }
	}
	
	public static void main(String[] args) {
		String directory = "src/files/";
		clearFilesFolder(new File(directory));
		launch(args);
	}
	
}
