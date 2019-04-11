package application;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class RGBChannel {
	public static AnchorPane root;

	public static Image redChannelImage, greenChannelImage, blueChannelImage;

	// This starts the program and loads the mainMenu scene

	public void start(Stage primaryStage) {
		try {
			AnchorPane root = FXMLLoader.load(getClass().getResource("RGBChannelView.fxml"));

			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setTitle("Image Viewer/RGB Channels");
			primaryStage.getIcons().add(new Image("file:icon.png"));
			primaryStage.show();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}