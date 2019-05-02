package application;
	


import javafx.application.Application;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;




import javafx.scene.layout.Priority;




public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			
			//main border pane
			BorderPane borderpane = new BorderPane();
			
			//pane with chess
			GridPane chesspane = new GridPane();
			chesspane.setPadding(new Insets(10,20,20,20));
			
			//pane with controls
			HBox controls = new HBox();
			controls.setPadding(new Insets(20,20,10,20));
			Button undoButton = new Button("Undo");
		
			Button redoButton = new Button("Redo");
			controls.getChildren().addAll(undoButton, redoButton);
			
			
	        int size = 8 ;
	        for (int row = 0; row < size; row++) {
	            for (int col = 0; col < size; col ++) {
	                StackPane square = new StackPane();
	                String color ;
	                if ((row + col) % 2 == 0) {
	                    color = "white";
	                } else {
	                    color = "black";
	                }
	                square.setStyle("-fx-background-color: "+color+";");
	                chesspane.add(square, col, row);
	            }
	        }
	        
	        //TODO okraje
	       
	        
	        
	        for (int i = 0; i < size; i++) {
	            chesspane.getColumnConstraints().add(new ColumnConstraints(5, Control.USE_COMPUTED_SIZE, Double.POSITIVE_INFINITY, Priority.ALWAYS, HPos.CENTER, true));
	            chesspane.getRowConstraints().add(new RowConstraints(5, Control.USE_COMPUTED_SIZE, Double.POSITIVE_INFINITY, Priority.ALWAYS, VPos.CENTER, true));
	        }
	        //root.getColumnConstraints().add(new ColumnConstraints(50, Control.USE_COMPUTED_SIZE, Double.POSITIVE_INFINITY, Priority.ALWAYS, HPos.CENTER, true)).add(new ColumnConstraints(50, Control.USE_COMPUTED_SIZE, Double.POSITIVE_INFINITY, Priority.ALWAYS, HPos.CENTER, true));
	        StackPane square = new StackPane();
	        square.setStyle("-fx-background-color: "+"green"+";");
            borderpane.setRight((new Button()));
	        
            //put it together
            borderpane.setCenter(chesspane);
            borderpane.setTop(controls);
            
            
            
            
	        primaryStage.setScene(new Scene(borderpane, 400, 400));
	        primaryStage.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
