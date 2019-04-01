package application;
	
import java.io.File;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXMLLoader;


public class Main extends Application {
	public static Stage stage;
	@Override
	public void start(Stage primaryStage) {
		stage = primaryStage;
		try {
			Parent root = FXMLLoader.load(getClass().getResource("view/Main.fxml"));
			primaryStage.setScene(new Scene(root));
			primaryStage.setTitle("DNA Processor");
			primaryStage.getIcons().add(new Image(new File("assets/dna.png").toURI().toString()));
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
