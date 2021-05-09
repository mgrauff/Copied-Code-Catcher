package application;

import javafx.scene.text.Font;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;

import javax.imageio.ImageIO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ListView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class ResultsScene extends Scene {

	final static double YELLOW_THRESHOLD = 0.6;
	final static double RED_THRESHOLD = 0.8;
	final static double RED_SD = 1.5;
	final static double YELLOW_SD = 1;
	
	private ScrollPane root;
	private VBox leftHalf;
	public Button backButton;
	
	private double[][] scores;
	private double[][]zScores;
	private double SD;
	private String[] names;
	
	private boolean showAllScores;
	
	/**
	 * Constructs a Results scene that graphically displays scores and names provided
	 * @param width - width of the scene
	 * @param height - height of the scene
	 * @param scores - 2D array of similarity scores. Width and height must be equal
	 * @param names - array of names to match to scores. Length must equal size of scores
	 */
	public ResultsScene(double width, double height, double[][] scores, double[][] zScores, String[] names, double stdDev) {
		this(new ScrollPane(), width, height, scores,zScores, names, stdDev);
	}
	/**
	 * Internal constructor used to keep reference to root node
	 * @param root
	 * @param width
	 * @param height
	 * @param scores
	 * @param names
	 */
	private ResultsScene(ScrollPane root, double width, double height, double[][] scores, double[][]zScores, String[] names, double SD) {
		super(root, width, height);
		
		this.root = root;
		this.scores = scores;
		this.zScores = zScores;
		this.SD = SD;
		this.names = names;
		this.showAllScores = false;
		
		Background myBackground = new Background(new BackgroundFill(Main.BEIGE, null, null));
		root.setBackground(myBackground);
		backButton = new Button("Back");
		Main.setRobinButtonStyle(backButton);
		
		HBox back = new HBox();
//		
//		if(!validateParams(scores, names)) {
//			root.getChildren().add(errorBox("The dimensions of the provided scores and names do not match"));
//			System.out.println("ERROR");
//			return;
//		}
		System.out.println("Success");
		back.setSpacing(50);
		back.setAlignment(Pos.CENTER);
		
		/*
		 * Left Half of screen contains: 
		 * 		Table of worst scores
		 * 		(Toggleable) Grid showing all scores
		 * 		Button to toggle grid
		 */
		leftHalf = new VBox();
		leftHalf.setAlignment(Pos.CENTER);
		leftHalf.setSpacing(50);
		leftHalf.getChildren().add(worstScores());
		leftHalf.getChildren().add(toggleButton());
		leftHalf.getChildren().add(saveButton());
		leftHalf.getChildren().add(backButton);
		
		back.getChildren().add(leftHalf);
		back.getChildren().add(resultsGraph(scores));
		root.setContent(back);
	}
	
	/**
	 * resultsGraph generates a stacked bar chart what percent of submission
	 * fall into which category of similarity to other submissions 
	 * @param scores
	 * @return barChart - a StackedBarChart object that is the results graph
	 */
	private StackedBarChart resultsGraph(double [][] scores) {
		//Variable Dictionary
		CategoryAxis xAxis = new CategoryAxis();
		NumberAxis yAxis = new NumberAxis();
		StackedBarChart<String, Number> barChart = new StackedBarChart<>(xAxis, yAxis);
		double percentGreen = 0, //stores the number of comparisons that are below the yellow threshold
				percentYellow = 0, //stores the number of comparisons that are above the yellow threshold
				percentRed = 0, //stores the number of comparisons that are above the red threshold
				total = 0;	//stores the total number of comparison scores so that percentages may be calculated	
		XYChart.Series<String, Number> percentRedSeries = new XYChart.Series<>(), //series that correpsond to the double percent scores 
				percentYellowSeries = new XYChart.Series<>(),  
				percentGreenSeries = new XYChart.Series<>();
		
		//Set title of chart and axes
		barChart.setTitle("Analysis results");
		xAxis.setLabel("Plagiarized Categories");
		yAxis.setLabel("Percent Plagiarized");
		
		//Get the number of comparison scores for each category
		for(int row = 0; row < zScores.length; row++) {
			for(int col = 0; col < zScores[row].length; col++) {
				if(zScores[row][col] > RED_SD) {
					percentRed++;
				} else if (zScores[row][col] > YELLOW_SD) {
					percentYellow++;
				} else if (zScores[row][col] <= YELLOW_SD) {
					percentGreen++;
				}
			}//end for col
		}//end for row
		
		//Calculate percentages for the chart
		total = percentGreen + percentYellow + percentRed;
		percentGreen /= total;
		percentYellow /= total;
		percentRed /= total;
		
		//Add the percentages to the chart
		final XYChart.Data<String, Number> overEighty = new XYChart.Data("Red%", percentRed);
		final XYChart.Data<String, Number> overSixty = new XYChart.Data<>("Yellow%", percentYellow);
		final XYChart.Data<String, Number> underSixty = new XYChart.Data<>("Green%", percentGreen);
		percentRedSeries.getData().add(overEighty);
		percentYellowSeries.getData().add(overSixty);
		percentGreenSeries.getData().add(underSixty);
		barChart.getData().addAll(percentRedSeries, percentYellowSeries, percentGreenSeries);
		
		//Graphical settings for the chart
		barChart.lookupAll(".default-color0.chart-bar").forEach(n -> n.setStyle("-fx-bar-fill: red;"));
		barChart.lookupAll(".default-color1.chart-bar").forEach(n -> n.setStyle("-fx-bar-fill: yellow;"));
		barChart.lookupAll(".default-color2.chart-bar").forEach(n -> n.setStyle("-fx-bar-fill: green;"));
		barChart.setLegendVisible(false);
		barChart.setMaxWidth(800);
		
		return barChart;
	}//end resultsGraph

	/**
	 * validateParams verifies that the parameters are all of the correct form. 
	 * Scores array must be square, and names must match the length of scores
	 * 
	 * @param scores - An array of doubles representing similarity. Rows and columns must be equal 
	 * @param names - An array of strings. Length must be equal to rows and columns of scores
	 * @return
	 */
	private boolean validateParams(double[][] scores, String[] names) {
		int length = names.length;
		
		//check 1st dimension of scores against length of names
		if(scores.length != length) {
			return false;
		}
		
		//check 2nd dimension of scores against length of 1st dimension 
		for(int i = 0; i < scores.length; i++) {
			if(scores[i].length != length) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Constructs a graphical grid displaying every student, their highest similarity score, 
	 * and the name of the student they were most similar to
	 * @return
	 */
	private ListView worstScores() {
		GridPane worstScores = new GridPane();
		DecimalFormat df = new DecimalFormat("0.000");
		ObservableList<Text> data = FXCollections.observableArrayList();
		ListView<Text> fileScores = new ListView<Text>(data);
		fileScores.setPrefWidth(600.0);
		//default size of columns, first name should be noticeably bigger than other columns
		worstScores.getColumnConstraints().add(new ColumnConstraints(70)); 
		worstScores.getColumnConstraints().add(new ColumnConstraints(30)); 
		worstScores.setAlignment(Pos.CENTER);
		
		for(int i = 0; i < scores.length; i++) {

			String currColumn = names[i];
			int maxScoreIndex = (i+1) % zScores.length;
			
			//add name of current student to left column of current row
			//find highest similarity score for current student
			for(int j = 0; j < zScores.length; j++) {
				if(i != j && zScores[i][j] > zScores[i][maxScoreIndex]) {
					maxScoreIndex = j;
				}
			}
			
			double maxScore = zScores[i][maxScoreIndex];
			double scoreToDisplay = scores[i][maxScoreIndex];
			
			//assign color of grid cell based on what the highest score was
			Color col = Color.GREEN;
			if(maxScore > RED_SD) {
				col = Color.RED;
			}
			else if(maxScore > YELLOW_SD) {
				col = Color.ORANGE;
			}
			
			//set text of entire row to indicator color
			currColumn = String.format("%-35.30s %-10s %35.30s ", names[i], df.format(scoreToDisplay), names[maxScoreIndex]);
			System.out.println(currColumn);
			Text cell = new Text(currColumn);
			Font font = Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 12);
			cell.setFill(col);
			cell.setFont(font);
			data.add(cell);

		}
		
		//return worstScores;
		return fileScores;
	}

	/**
	 * Constructs a graphical grid displaying every student on each axis, 
	 * and the corresponding similarity scores in the middle cells. 
	 * @return
	 */
	private GridPane allScores() {
		GridPane allScores = new GridPane();
		DecimalFormat df = new DecimalFormat("0.000");
		
		allScores.setVgap(5);
		allScores.setHgap(5);
		allScores.setAlignment(Pos.CENTER);
		
		//add names to top row and left column
		for(int i = 0; i < scores.length; i++) {
			allScores.add(new Text(names[i]), 0, i+1);
			allScores.add(new Text(names[i]), i+1, 0);
		}
		
		//iterate through entire array
		for(int c = 0; c < scores.length; c++) {
			for(int r = 0; r < scores.length; r++) {
				//add a pane to grid cell
				StackPane p = new StackPane();
				allScores.add(p, c+1, r+1);
				
				//add corresponding score to cell
				p.getChildren().add(new Text("" + df.format(scores[r][c])));
				
				//get indicator color based on score, or black if comparing to self
				Color col = Color.GREEN;
				if(r == c) {
					col = Color.BLACK;
				}
				else if(zScores[r][c] > RED_SD) {
					col = Color.RED;
				}
				else if(zScores[r][c] > YELLOW_SD) {
					col = Color.YELLOW;
				}
				
				p.setBackground(new Background(new BackgroundFill(col, null, null)));
			}
		}
		
		return allScores;
	}

	/**
	 * Constructs a button to toggle visibility of grid showing all scores
	 * @return
	 */
	private Button toggleButton() {
		Button b = new Button("Show All Scores");
		Main.setRobinButtonStyle(b);
		b.setOnAction(new EventHandler<ActionEvent>() {	 
            @Override
            public void handle(ActionEvent event) {
            	//when clicked
            	//toggle showAllScores
            	showAllScores = !showAllScores;
            	if(showAllScores) {
            		//add allScores grid above this button and change button text
            		leftHalf.getChildren().add(1, allScores());
            		b.setText("Collapse All Scores");
            	}
            	else {
            		//delete allScores grid and change button text
            		leftHalf.getChildren().remove(1);
            		b.setText("Show All Scores");
            	}
            }
        });
		
		return b;
	}
	
	/**
	 * Function saving the results as a png
	 * @return Save as PNG button
	 */
	private Button saveButton() {
		Button b = new Button("Save as PNG");
		Main.setRobinButtonStyle(b);
		b.setOnAction(new EventHandler<ActionEvent>() {	 
            @Override
            public void handle(ActionEvent event) {
            	saveImg(); //Calling save image helper function
            }
        });
		
		return b;
	}

	/**
	 * Simple Pane containing an error message
	 * @param msg
	 * @return
	 */
	private StackPane errorBox(String msg) {
		StackPane s = new StackPane();
		s.getChildren().add(new Text("ERROR: " + msg));
		return s;
	}
	
	/**
	 * Save image helper function
	 */
	public void saveImg() {		
		FileChooser fc = new FileChooser(); //For choosing destination folder
		fc.getExtensionFilters().add(new ExtensionFilter("png images", "*.png"));
		
		File destFile = fc.showSaveDialog(null); //Destination folder
		
		if(destFile != null) { //If it exists send PNG to file
			WritableImage wi = new WritableImage((int)this.getWidth(), (int)this.getHeight());
			this.snapshot(wi);	
			
			RenderedImage img = SwingFXUtils.fromFXImage(wi, null);
			
			try { //Write PNG to dest file within try catch block
				ImageIO.write(img, "png", destFile);
			} catch (IOException e) {//Catch any errors
				System.out.println("Error saving file");
//				this.root.getChildren().add(errorBox("Error saving file"));
			}
		}
	}
	
	/**
	 * Helper function setting scores
	 * @param passedScores
	 */
	public void setScores(double[][] passedScores) {
		
		for(int row = 0; row < passedScores.length; row++) {
			for(int col = 0; col < passedScores[row].length; row++) {
				this.scores[row][col] = passedScores[row][col];
			}
		}	
	}
	
	/**
	 * Helper function setting names
	 * @param passedNames
	 */
	public void setNames(String[] passedNames) {
		
		for(int index = 0; index < passedNames.length; index++) {
			this.names[index] = passedNames[index];
		}
	}
	
	public static void main(String[] args) {
		Main.main(args);
	}
}
