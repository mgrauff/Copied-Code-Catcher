package application;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class IntroScene extends Scene {

	private VBox root;
	public Button begin;
	
	public IntroScene(double width, double height) {
		this(new VBox(), width, height);
	}
	private IntroScene(VBox root, double width, double height) {
		super(root, width, height);
		
		this.root = root;
		root.setAlignment(Pos.CENTER);
		
		Text welcomeText = new Text("Welcome to Team Robin's Copied Code Catcher");
		welcomeText.setFill(Main.DARK_GREY);
		welcomeText.setScaleX(3.0);
		welcomeText.setScaleY(3.0);
		welcomeText.setStroke(Main.DARK_GREY);
		Font font = Font.font("verdana", FontWeight.NORMAL, FontPosture.ITALIC, 12);
		welcomeText.setFont(font);
		Background myBackground = new Background(new BackgroundFill(Main.ORANGE, null, null));
		root.setBackground(myBackground);
		
		begin = new Button("Begin");
		begin.setScaleX(1.2);
		begin.setScaleY(1.2);

		Main.setRobinButtonStyle(begin);
		begin.setTextFill(Main.DARK_GREY);
		welcomeText.setLayoutY(height);
		VBox myVBox = new VBox(welcomeText, begin);
		myVBox.setSpacing(50.0);
		myVBox.setAlignment(Pos.CENTER);
		root.getChildren().add(myVBox);
	}
}
