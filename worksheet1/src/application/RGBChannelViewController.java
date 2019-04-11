package application;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class RGBChannelViewController {
	@FXML
	MenuBar mainMenuBar;
	@FXML
	Menu systemMenu;
	@FXML
	MenuItem returnMenuItem, exitMenuItem;
	@FXML
	ImageView redImageView;
	@FXML
	ImageView greenImageView;
	@FXML
	ImageView blueImageView;
	@FXML
	Button launchButton;
	@FXML
	Image redChannelImage, greenChannelImage, blueChannelImage;

	@FXML
	public void returnPage(ActionEvent e) {

		Stage s = (Stage) mainMenuBar.getScene().getWindow();
		try {
			AnchorPane root = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));

			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			s.setScene(scene);
		} catch (Exception e2) {
			e2.printStackTrace();
		}
	}

	// This exits the program when the exit option is selected from the "System"
	// menu
	@FXML
	public void exit(ActionEvent e) {
		System.out.println("---------------------------------------------------------------------------");
		System.out.println("----------------------APPLICATION HAS BEEN TERMINATED----------------------");
		System.out.println("---------------------------------------------------------------------------");
		Platform.exit();

	}

	public void setImageChannels(Image image) {
		System.out.println("processing red image");
		redChannelImage = processRedChannel(MainMenuController.image);
		System.out.println("setting red image");
		redImageView.setImage(redChannelImage);
		System.out.println("red image set");
		System.out.println("processing green image");
		greenChannelImage = processGreenChannel(MainMenuController.image);
		System.out.println("setting green image");
		greenImageView.setImage(greenChannelImage);
		System.out.println("green image set");
		System.out.println("processing blue image");
		blueChannelImage = processBlueChannel(MainMenuController.image);
		System.out.println("setting blue image");
		blueImageView.setImage(blueChannelImage);
		System.out.println("blue image set");
	}

	public static Image processRedChannel(Image image) {

		PixelReader pixelReader = MainMenuController.image.getPixelReader();

		int imageHeightInt = (int) MainMenuController.image.getHeight();
		int imageWidthInt = (int) MainMenuController.image.getWidth();

		WritableImage redImage = new WritableImage(imageWidthInt, imageHeightInt);

		@SuppressWarnings("unused")
		PixelWriter pixelWriter = redImage.getPixelWriter();
		for (int x = 0; x < imageWidthInt; x++) {
			for (int y = 0; y < imageHeightInt; y++) {
				int pixel = pixelReader.getArgb(x, y);

				int red = ((pixel >> 16) & 0xff);

				int redProcess = (0xff000000) | (red << 16);

				redImage.getPixelWriter().setArgb(x, y, redProcess);

			}
		}
		return redImage;

	}

	public static Image processBlueChannel(Image image) {
		PixelReader pixelReader = MainMenuController.image.getPixelReader();

		int imageHeightInt = (int) MainMenuController.image.getHeight();
		int imageWidthInt = (int) MainMenuController.image.getWidth();

		WritableImage blueImage = new WritableImage(imageWidthInt, imageHeightInt);

		@SuppressWarnings("unused")
		PixelWriter pixelWriter = blueImage.getPixelWriter();
		for (int x = 0; x < imageWidthInt; x++) {
			for (int y = 0; y < imageHeightInt; y++) {
				int pixel = pixelReader.getArgb(x, y);

				int blue = (pixel & 0xff);

				int blueProcess = (0xff000000) + (blue << 0xff000000);

				blueImage.getPixelWriter().setArgb(x, y, blueProcess);

			}
		}
		return blueImage;

	}

	public static Image processGreenChannel(Image image) {
		PixelReader pixelReader = MainMenuController.image.getPixelReader();

		int imageHeightInt = (int) MainMenuController.image.getHeight();
		int imageWidthInt = (int) MainMenuController.image.getWidth();

		WritableImage greenImage = new WritableImage(imageWidthInt, imageHeightInt);
		@SuppressWarnings("unused")
		PixelWriter pixelWriter = greenImage.getPixelWriter();
		for (int x = 0; x < imageWidthInt; x++) {
			for (int y = 0; y < imageHeightInt; y++) {
				int pixel = pixelReader.getArgb(x, y);

				int green = ((pixel >> 8) & 0xff);
				int greenProcess = (0xff000000) + (green << 8);

				greenImage.getPixelWriter().setArgb(x, y, greenProcess);

			}
		}
		return greenImage;
	}

	@FXML
	public void initialize() {

		setImageChannels(MainMenuController.image);
	}
}
