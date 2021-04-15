package application;
	
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
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


public class Main extends Application {
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
        primaryStage.setScene(this.resultsScene(scores, names));
        primaryStage.show();
    }
	
	private Scene resultsScene(double[][] scores, String[] names) {
		
		double YELLOW_THRESHOLD = 0.6;
		double RED_THRESHOLD = 0.8;
		
		VBox root = new VBox();
		root.setSpacing(50);
		root.setAlignment(Pos.CENTER);
		
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
		
		root.getChildren().add(worstScores);
		
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
		
		Button b = new Button("Show All Scores");
		b.setOnAction(new EventHandler<ActionEvent>() {
			 
            @Override
            public void handle(ActionEvent event) {
            	if(root.getChildren().size() == 2) {
            		root.getChildren().add(1, allScores);
            		b.setText("Collapse All Scores");
            	}
            	else {
            		root.getChildren().remove(allScores);
            		b.setText("Show All Scores");
            	}
            }
        });
		root.getChildren().add(b);
	
		return new Scene(root, 500, 400);
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
