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
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class IntroScene extends Scene {

	public Button begin; //button for starting the program
	
	/**
	 * Public constructor
	 * @param width
	 * @param height
	 */
	public IntroScene(double width, double height) {
		
		this(new Pane(), width, height);
		
	}//end public constructor
	
	/**
	 * Private constructor
	 * @param root - root node
	 * @param width - width of the screen
	 * @param height - height of the screen
	 */
	private IntroScene(Pane root, double width, double height) {
		
		super(root, width, height);
		
		//Background customization
		Background myBackground = new Background(new BackgroundImage(
				new Image("file:src/Background.png"), 
				BackgroundRepeat.NO_REPEAT, 
				BackgroundRepeat.NO_REPEAT, 
				BackgroundPosition.DEFAULT, 
				new BackgroundSize(1.0,1.0, true, true, false, false)));
		root.setBackground(myBackground);
		
		//Begin button customization
		begin = new Button("Begin");
		Main.setRobinButtonStyle(begin);
		begin.setMaxWidth(60.0); //size
		begin.setMinWidth(60.0);
		begin.setScaleX(4.0);	
		begin.setScaleY(3.0);
		//Set the position based on the screen size
		begin.setLayoutX(width/2.0 - begin.getMaxWidth()/2.0); //position
		begin.setLayoutY(height/1.125);
		
		//Add everything in	
		root.getChildren().add(begin);
		
	}//end private constructor
	
}//end IntroScene
