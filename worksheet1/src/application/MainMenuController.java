package application;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
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
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class MainMenuController {

	@FXML
	Menu systemMenu, fileMenu, viewOptionsMenu;
	@FXML
	MenuItem exitMenuItem, chooseFileMenuItem, grayScaleMenuItem, showRGBChannelsMenuItem;
	@FXML
	ImageView chosenFileImageView;
	@FXML
	Text fileNameText, filePathText, fileSizeText, imageHeightText, imageWidthText;
	@FXML
	Button button;
	@FXML
	FileChooser filechooser;
	@FXML
	MenuBar mainMenuBar;
	@FXML
	Image grayScaledImage;

	public static Image image;

	File selectedFile;
	public static int imageWidth;
	public static int imageHeight;
	String imageWidthString, imageHeightString;

	// This exits the program when the exit option is selected from the "System"
	// menu
	@FXML
	public void exit(ActionEvent e) {
		System.out.println("---------------------------------------------------------------------------");
		System.out.println("----------------------APPLICATION HAS BEEN TERMINATED----------------------");
		System.out.println("---------------------------------------------------------------------------");
		Platform.exit();

	}

	@FXML
	public void openFileChooser(ActionEvent e) {
		FileChooser fileChooser = new FileChooser();

		selectedFile = fileChooser.showOpenDialog(null);

		if (selectedFile != null) {
			try {
				BufferedImage bufferedImage = ImageIO.read(selectedFile);
				image = SwingFXUtils.toFXImage(bufferedImage, null);
				String fileName = selectedFile.getName();
				String filePath = selectedFile.getAbsolutePath();
				Long fileSizeLong = selectedFile.length() / 1024;
				String fileSize = fileSizeLong.toString();
				double imageWidthDouble = image.getWidth();
				double imageHeigthDouble = image.getHeight();
				imageWidthString = Double.toString(imageHeigthDouble);
				imageHeightString = Double.toString(imageWidthDouble);

				chosenFileImageView.setImage(image);
				fileSizeText.setText(fileSize + "KB");
				filePathText.setText(filePath);
				fileNameText.setText(fileName);
				imageHeightText.setText(imageHeightString);
				imageWidthText.setText(imageWidthString);

			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		} else {
			System.out.println("---------------------------------------------------------------------------");
			System.out.println("-----------------------------INVALID FILE INPUT----------------------------");
			System.out.println("---------------------------------------------------------------------------");

		}
	}

	@FXML
	public void showRGBChannels(ActionEvent e) {
		Stage s = (Stage) mainMenuBar.getScene().getWindow();

		try {
			AnchorPane root = FXMLLoader.load(getClass().getResource("RGBChannelView.fxml"));

			Scene scene = new Scene(root);
			
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			s.setScene(scene);
		} catch (Exception e2) {
			e2.printStackTrace();
		}
	}

	@FXML
	public void setImageToGrayScale(ActionEvent e) {
		System.out.println("processing the image to grayscale");
		grayScaledImage = processToGrayScale(image);
		System.out.println("setting image");
		chosenFileImageView.setImage(grayScaledImage);
		System.out.println("image set");
	}

	public Image processToGrayScale(Image image) {
		System.out.println("image reading to pixel reader");
		PixelReader pixelReader = image.getPixelReader();
		System.out.println("image read");

		int imageHeightInt = imageHeight;
		int imageWidthInt = imageWidth;

		imageHeightInt = (int) image.getHeight();
		imageWidthInt = (int) image.getWidth();

		WritableImage grayImage = new WritableImage(imageWidthInt, imageHeightInt);

		@SuppressWarnings("unused")
		PixelWriter pixelWriter = grayImage.getPixelWriter();
		for (int x = 0; x < imageWidthInt; x++) {
			for (int y = 0; y < imageHeightInt; y++) {
				int pixel = pixelReader.getArgb(x, y);

				int red = ((pixel >> 16) & 0xff);
				int green = ((pixel >> 8) & 0xff);
				int blue = (pixel & 0xff);

				int grayLevel = (int) (0.2162 * red + 0.7152 * green + 0.0722 * blue) / 3;
				grayLevel = 170 - grayLevel;
				int gray = (grayLevel << 16) + (grayLevel << 8) + grayLevel;

				grayImage.getPixelWriter().setArgb(x, y, -gray);

			}
		}
		return grayImage;

	}

	public static void main(String[] args) {
		Application.launch(args);
	}

}
