package sample;


import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Slider;
import javafx.scene.paint.Color;
import org.apache.commons.configuration.ConfigurationException;

import java.net.URL;
import java.util.ResourceBundle;

public class SettingsTabController extends AppSubScene implements Initializable {
	@FXML
	private Slider volumeSlider;
	@FXML
	private ColorPicker tileColorPicker;

	@FXML
	private void changeVolume() throws ConfigurationException {
		System.out.println(volumeSlider.getValue());
		Main.getConfigurationHandler().getConfiguration().setProperty("Volume", volumeSlider.getValue() / 100);
		Main.saveConfig();
	}

	@FXML
	private void changeTileColor() throws ConfigurationException {
		System.out.println("color: " + tileColorPicker.getValue());
		Main.getConfigurationHandler().getConfiguration().setProperty("tileColorRed", tileColorPicker.getValue().getRed());
		Main.getConfigurationHandler().getConfiguration().setProperty("tileColorGreen", tileColorPicker.getValue().getGreen());
		Main.getConfigurationHandler().getConfiguration().setProperty("tileColorBlue", tileColorPicker.getValue().getBlue());
		Main.saveConfig();
	}

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		volumeSlider.setValue(Main.getConfigurationHandler().getConfiguration().getDouble("Volume") * 100);
		tileColorPicker.setValue(Color.color(Main.getConfigurationHandler().getConfiguration().getDouble("tileColorRed"),
				Main.getConfigurationHandler().getConfiguration().getDouble("tileColorGreen"),
				Main.getConfigurationHandler().getConfiguration().getDouble("tileColorBlue")));
	}
}
