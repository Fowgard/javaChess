package Game;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Reflection;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class GUI extends Application{
	private Label text;
	public GUI()
	{
		launch();
	}

	@Override
	public void start(Stage stage) throws Exception {
		text = new Label("textasdnasl,dasd");
		VBox root = new VBox();
		root.getChildren().addAll(text);
		
		Scene scene = new Scene(root,500,500);
		stage.setScene(scene);
		
		stage.show();
	}

}
