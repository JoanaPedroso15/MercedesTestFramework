package framework;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author jpedroso
 * @description - Auxiliary class with methods to create files
 */
public class FileCreator {

	private static final Logger LOG = LoggerFactory.getLogger(FileCreator.class);
	private final static String fileSeparator = System.getProperty("file.separator");
	public static String fileDir = System.getProperty("user.dir") + fileSeparator + "Reports" + fileSeparator;

	
	public static void writeTextFile(String lowestPrice, String highestPrice) throws IOException {
		File fout = new File(fileDir + "A-Class prices.txt");
		FileOutputStream fos = new FileOutputStream(fout);
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
		bw.write("############ A-Class PRICE RANGE VALIDATION ####################");
		bw.newLine();
		bw.newLine();
		bw.write("Lowest Price: " + lowestPrice);
		bw.newLine();
		bw.write("Highest Price: " + highestPrice);
		bw.close();
	}

}
