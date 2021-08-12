package framework;

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
