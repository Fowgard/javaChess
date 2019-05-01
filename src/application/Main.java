package application;
	
import javafx.application.Application;
import javafx.scene.layout.ColumnConstraints;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.geometry.HPos;
import javafx.geometry.VPos;




import javafx.scene.layout.Priority;




public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			
			GridPane root = new GridPane();
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
	                root.add(square, col, row);
	            }
	        }
	        
	        //TODO okraje
	       
	        
	        
	        for (int i = 0; i < size; i++) {
	            root.getColumnConstraints().add(new ColumnConstraints(5, Control.USE_COMPUTED_SIZE, Double.POSITIVE_INFINITY, Priority.ALWAYS, HPos.CENTER, true));
	            root.getRowConstraints().add(new RowConstraints(5, Control.USE_COMPUTED_SIZE, Double.POSITIVE_INFINITY, Priority.ALWAYS, VPos.CENTER, true));
	        }
	        root.getColumnConstraints().add(new ColumnConstraints(50, Control.USE_COMPUTED_SIZE, Double.POSITIVE_INFINITY, Priority.ALWAYS, HPos.CENTER, true));
	        
	        StackPane square = new StackPane();
	        square.setStyle("-fx-background-color: "+"green"+";");
            root.add(new Button(), 8, 0);
	        
	        primaryStage.setScene(new Scene(root, 400, 400));
	        primaryStage.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
