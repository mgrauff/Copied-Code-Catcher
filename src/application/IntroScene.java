package application;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class IntroScene extends Scene {

	public Button begin;
	
	public IntroScene(double width, double height) {
		
		this(new Pane(), width, height);
		
	}//default constructor
	
	private IntroScene(Pane root, double width, double height) {
		
		super(root, width, height);
		
		//Welcome Text customization
		Text welcomeText = new Text("Welcome to Team Robin's Copied Code Catcher");
		Font font = Font.font("verdana", FontWeight.NORMAL, FontPosture.ITALIC, 12);
		welcomeText.setFill(Main.DARK_GREY);
		welcomeText.setScaleX(3.0);
		welcomeText.setScaleY(5.0);
		welcomeText.setStroke(Main.DARK_GREY);		
		welcomeText.setFont(font);
		welcomeText.setLayoutY(height);
		
		//Background customization
		Background myBackground = new Background(new BackgroundImage(
				new Image("file:src/HoodRobinFullLogo.png"), 
				BackgroundRepeat.NO_REPEAT, 
				BackgroundRepeat.NO_REPEAT, 
				BackgroundPosition.DEFAULT, 
				new BackgroundSize(1.0,1.0, true, true, false, false)));
		root.setBackground(myBackground);
		
		//Begin button customization
		begin = new Button("Begin");
		Main.setRobinButtonStyle(begin);
		begin.setScaleX(4.0);
		begin.setScaleY(3.0);
//		begin.setLayoutX(width/6.4);
//		begin.setLayoutY(height/3.6);
		begin.setLayoutX(600.0);
		begin.setLayoutY(600.0);
		begin.setTextFill(Main.DARK_GREY);
		begin.setAlignment(Pos.BOTTOM_CENTER);
		
		//Add everything in
		root.getChildren().add(begin);
		
	}//end private constructor
	
}//end IntroScene
