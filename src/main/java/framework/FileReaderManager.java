package framework;

/**
 * @author jpedroso
 * @description - class that makes sure there's only one instance of each one of the classes
 * responsible for reading the properties of certain files. For example, it makes sure there's only
 * one instance of the ConfigurationFilerReader class that has methods to read the properties of the
 * Configuration.properties file
 */
public class FileReaderManager {
	
	private static FileReaderManager fileReaderManager = new FileReaderManager();
	private static ConfigurationFileReader fileReader;


	private FileReaderManager() {
	}

	 public static FileReaderManager getInstance( ) {
	      return fileReaderManager;
	 }

	 public ConfigurationFileReader getConfigReader() {
		 return (fileReader == null) ? new ConfigurationFileReader() : fileReader;
	 }
	

}
