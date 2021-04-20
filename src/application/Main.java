package application;
	
import javafx.application.Application;
import javafx.stage.Stage;

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
        primaryStage.setScene(new ResultsScene(500, 400, scores, names));
        primaryStage.setMaximized(true);
        primaryStage.show();
    }
	
	public static void main(String[] args) {
		launch(args);
	}
}
