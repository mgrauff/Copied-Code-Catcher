package application;
	

import java.io.File;
//import java.text.DecimalFormat;
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
import javafx.stage.Screen;
import javafx.stage.Stage;

public class Main extends Application {
	
	IntroScene intro;
	ChooseFileScene fileChoose;
	StackOverFlowScene pasteCode;
	static String directory = "src/files/";
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
        
        
        
        intro = new IntroScene(Screen.getPrimary().getBounds().getWidth(), Screen.getPrimary().getBounds().getHeight()-50);   
        intro.begin.setOnAction(new EventHandler<ActionEvent>() {
        	@Override
        	public void handle(ActionEvent event) {
        		primaryStage.setScene(fileChoose);
        	}
        });
        
        fileChoose = new ChooseFileScene(Screen.getPrimary().getBounds().getWidth(), Screen.getPrimary().getBounds().getHeight()-50);
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
					
					//CALCULATE AVG SCORE
					double avg = 0;
					int size = scores.length;
					for(int i=0; i< size; i++) {
						for(int j=0; j<size; j++) {
							if(i!=j) {
								avg +=scores[i][j];
							}
						}
					}
					int numSamples = (size*size)-size;
					avg = avg/ (1.0*numSamples);
					System.out.println("AVERAGE SCORE: " + avg);
					
					
					//CALCULATE STD DEVIATION
					//The one group who used standard deviation inspired us
					//This code is NOT COPIED, but the idea comes from them
					//I'm not sure which group that is
					double SD = 0.0;
					double sum=0.0;
					double sqDist;
					for(int i=0; i< size; i++) {
						for(int j=0; j<size; j++) {
							if(i!=j) {
								sqDist = Math.pow((scores[i][j] - avg),2);
								sum+= sqDist;
							}
						}
					}
					SD = Math.sqrt(sum/(1.0*numSamples));
					System.out.println("STANDARD DEVIATION: " + SD);
					
					double[][] zScores = new double[size][size];
					for(int i=0; i< size; i++) {
						for(int j=0; j<size; j++) {
							if(i!=j) {
								double Z = (scores[i][j] - avg) / SD;
								zScores[i][j] = Z;
							}
						}
					}
					
					
					ResultsScene results = new ResultsScene(Screen.getPrimary().getBounds().getWidth(), Screen.getPrimary().getBounds().getHeight()-50, scores,zScores, names, SD);
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
        
        pasteCode = new StackOverFlowScene(Screen.getPrimary().getBounds().getWidth(), Screen.getPrimary().getBounds().getHeight()-50);
        pasteCode.backButton.setOnAction(new EventHandler<ActionEvent>() {
        	
        	@Override 
        	public void handle(ActionEvent event) {
        		primaryStage.setScene(fileChoose);
        	}
        	
        });
        
        
        

        primaryStage.setScene(intro);
        primaryStage.setMaximized(true);
        primaryStage.getIcons().add(new Image("file:src/Icon.png"));
        primaryStage.show();
    }
	
	/**
	 * Takes a button paramater and changes that button according to the style
	 * choices contained in the function.
	 * @param button
	 */
	public static void setRobinButtonStyle(Button button) {
		
		//Set the textFill and background colors
		button.setStyle("-fx-background-color: #" + Main.GRAYISH_CYAN_COLOR + 
				"; -fx-border-width: 5px; -fx-text-fill: #" + Main.BEIGE_COLOR + ";");
		DropShadow shadow = new DropShadow();
		
		//Adding the shadow when the mouse cursor is on
		button.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
		    button.setEffect(shadow);
		});
		
		//Removing the shadow when the mouse cursor is off
		button.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
		    button.setEffect(null);
		});

	}//end setRobinButtonStyle
	
	//This method is used to clear our files folder in src
	//Note this is an incredibly dangerous method and can ONLY BE USED ON SRC/FILES
	private static void clearFilesFolder(File folder) {
	    File[] files = folder.listFiles();
	    //Clear through files in folder
	    if(files!=null) {
	        for(File file: files) {
	            if(file.isDirectory()) {
	            	//recurse
	                clearFilesFolder(file);
	                file.delete();
	            } else {
	                file.delete();
	            }
	        }
	    }
	}
	
	
    @Override
    public void stop() {
    	//Clear files folder on exit
    	clearFilesFolder(new File(directory));
    }
	
	public static void main(String[] args) {
		clearFilesFolder(new File(directory));
		launch(args);
	}
	
}
