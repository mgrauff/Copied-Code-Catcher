package application;

import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.chart.*;
import javafx.scene.chart.XYChart.Data;

public class ResultsScene extends Scene {

	final static double YELLOW_THRESHOLD = 0.6;
	final static double RED_THRESHOLD = 0.8;
	
	private VBox root;
	
	private double[][] scores;
	private String[] names;
	
	private boolean showAllScores;
	
	private ResultsScene(VBox root, double width, double height, double[][] scores, String[] names) {
		super(root, width, height);
		
		this.root = root;
		this.scores = scores;
		this.names = names;
		this.showAllScores = false;
		
		if(!validateParams(scores, names)) {
			root.getChildren().add(errorBox("The dimensions of the provided scores and names do not match"));
			return;
		}
		
		root.setSpacing(50);
		root.setAlignment(Pos.CENTER);
		
		root.getChildren().add(worstScores());
		root.getChildren().add(toggleButton());
		root.getChildren().add(resultsGraph(scores));
		
	}
	
	private StackedBarChart resultsGraph(double [][] scores) {
		CategoryAxis xAxis = new CategoryAxis();
		NumberAxis yAxis = new NumberAxis();
		StackedBarChart<String, Number> barChart = new StackedBarChart<>(xAxis, yAxis);
		barChart.setTitle("Analysis results");
		double percentGreen = 0, percentYellow = 0, percentRed = 0, total = 0;
		
		
		XYChart.Series<String, Number> series1 = new XYChart.Series<>(), 
				series2 = new XYChart.Series<>(), 
				series3 = new XYChart.Series<>();
		
		xAxis.setLabel("Plagiarized Categories");
		yAxis.setLabel("Percent Plagiarized");
		
		for(int i = 0; i < scores.length; i++) {
			for(int j = 0; j < scores[i].length; j++) {
				if(scores[i][j] <= YELLOW_THRESHOLD) {
					percentGreen ++;
				} else if (scores[i][j] <= RED_THRESHOLD) {
					percentYellow ++;
				} else if (scores[i][j] < 1) {
					percentRed ++;
				}
			}
		}
		
		total = percentGreen + percentYellow + percentRed;
		percentGreen /= total;
		percentYellow /= total;
		percentRed /= total;
		
		final XYChart.Data<String, Number> overEighty = new XYChart.Data("Over 80%", percentRed);
		final XYChart.Data<String, Number> overSixty = new XYChart.Data<>("Over 60%", percentYellow);
		final XYChart.Data<String, Number> underSixty = new XYChart.Data<>("Under 60%", percentGreen);
		
		series1.getData().add(overEighty);
		series2.getData().add(overSixty);
		series3.getData().add(underSixty);
		
		barChart.getData().addAll(series1, series2, series3);
		barChart.lookupAll(".default-color0.chart-bar").forEach(n -> n.setStyle("-fx-bar-fill: red;"));
		barChart.lookupAll(".default-color1.chart-bar").forEach(n -> n.setStyle("-fx-bar-fill: yellow;"));
		barChart.lookupAll(".default-color2.chart-bar").forEach(n -> n.setStyle("-fx-bar-fill: green;"));
		barChart.setLegendVisible(false);
		barChart.setMaxWidth(800);
		
		return barChart;
	}//end resultsGraph
	

	
	public ResultsScene(double width, double height, double[][] scores, String[] names) {
		this(new VBox(), width, height, scores, names);
	}
	
	private boolean validateParams(double[][] scores, String[] names) {
		int length = names.length;
		if(scores.length != length) {
			return false;
		}
		for(int i = 0; i < scores.length; i++) {
			if(scores[i].length != length) {
				return false;
			}
		}
		return true;
	}

	private GridPane worstScores() {
		GridPane worstScores = new GridPane();
		worstScores.getColumnConstraints().add(new ColumnConstraints(70)); 
		worstScores.getColumnConstraints().add(new ColumnConstraints(30)); 
		worstScores.setAlignment(Pos.CENTER);
		
		for(int i = 0; i < scores.length; i++) {
			
			HBox p0 = new HBox();	
			HBox p1 = new HBox();	
			HBox p2 = new HBox();
			p0.prefWidth(100);
			worstScores.add(p0, 0, i+1);
			worstScores.add(p1, 1, i+1);
			worstScores.add(p2, 2, i+1);
			
			int maxScoreIndex = (i+1) % scores.length;
			
			p0.getChildren().add(new Text(names[i]));
			
			for(int j = 0; j < scores.length; j++) {
				if(i != j && scores[i][j] > scores[i][maxScoreIndex]) {
					maxScoreIndex = j;
				}
			}
			
			double maxScore = scores[i][maxScoreIndex];
			
			Color col = Color.GREEN;
			if(maxScore > RED_THRESHOLD) {
				col = Color.RED;
			}
			else if(maxScore > YELLOW_THRESHOLD) {
				col = Color.YELLOW;
			}
			
			p0.setBackground(new Background(new BackgroundFill(col, null, null)));
			p1.setBackground(new Background(new BackgroundFill(col, null, null)));
			p2.setBackground(new Background(new BackgroundFill(col, null, null)));
			
			p1.getChildren().add(new Text("" + maxScore));
			p2.getChildren().add(new Text(names[maxScoreIndex]));
		}
		
		return worstScores;
	}

	private GridPane allScores() {
		GridPane allScores = new GridPane();
		
		allScores.setVgap(5);
		allScores.setHgap(5);
		allScores.setAlignment(Pos.CENTER);
		
		for(int i = 0; i < scores.length; i++) {
			allScores.add(new Text(names[i]), 0, i+1);
			allScores.add(new Text(names[i]), i+1, 0);
		}
		
		for(int c = 0; c < scores.length; c++) {
			for(int r = 0; r < scores.length; r++) {
				Pane p = new StackPane();
				allScores.add(p, c+1, r+1);
				p.getChildren().add(new Text("" + scores[r][c]));
				
				Color col = Color.GREEN;
				if(r == c) {
					col = Color.BLACK;
				}
				else if(scores[r][c] > RED_THRESHOLD) {
					col = Color.RED;
				}
				else if(scores[r][c] > YELLOW_THRESHOLD) {
					col = Color.YELLOW;
				}
				
				p.setBackground(new Background(new BackgroundFill(col, null, null)));
			}
		}
		
		return allScores;
	}

	private Button toggleButton() {
		Button b = new Button("Show All Scores");
		b.setOnAction(new EventHandler<ActionEvent>() {	 
            @Override
            public void handle(ActionEvent event) {
            	showAllScores = !showAllScores;
            	if(showAllScores) {
            		root.getChildren().add(1, allScores());
            		b.setText("Collapse All Scores");
            	}
            	else {
            		root.getChildren().remove(1);
            		b.setText("Show All Scores");
            	}
            }
        });
		
		return b;
	}
	
	private StackPane errorBox(String msg) {
		StackPane s = new StackPane();
		s.getChildren().add(new Text("ERROR: " + msg));
		return s;
	}
	
	
	
	
	
}
