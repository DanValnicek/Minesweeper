package sample;

import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.FileBasedConfiguration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.ex.ConfigurationException;

public class ConfigurationHandler {

	private FileBasedConfigurationBuilder<FileBasedConfiguration> builder;
	private static Configuration configuration;
	ConfigurationHandler() {
		org.apache.commons.configuration2.builder.fluent.Parameters params = new org.apache.commons.configuration2.builder.fluent.Parameters();
		builder = new FileBasedConfigurationBuilder<FileBasedConfiguration>(PropertiesConfiguration.class)
				.configure(params.properties()
						.setFileName("gui.properties"));
		try {
			configuration = builder.getConfiguration();
			System.out.println(configuration);
		} catch (
				ConfigurationException e) {
			e.printStackTrace();
		}
	}

	public FileBasedConfigurationBuilder<FileBasedConfiguration> getBuilder() {
		return builder;
	}

	public static Configuration getConfiguration() {
		return configuration;
	}
}
