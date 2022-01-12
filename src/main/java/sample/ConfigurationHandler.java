package sample;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

import java.io.File;

public class ConfigurationHandler {

	public PropertiesConfiguration config;


	public ConfigurationHandler() throws ConfigurationException {
		try {
			File file = new File("src/main/resources/gui.properties");
			config = new PropertiesConfiguration(file);
			System.out.println(file.getAbsolutePath());
			// config contains all properties read from the file
		} catch (ConfigurationException cex) {
			System.err.println(cex.getStackTrace());
			// loading of the configuration file failed
		}

	}

	public PropertiesConfiguration getConfiguration() {
		return this.config;
	}

}
