package pageobjects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/**
 * @author jpedroso
 * @description - class with just getters and setters used to share information between different steps 
 */
public class TestInputs {

	private static final Logger LOG = LoggerFactory.getLogger(TestInputs.class);

	private static List<Map<String, String>> listResults = new ArrayList<>();
	private static Map<String, List<String>> filter = new HashMap<>();
	private static String model = "";
	private static String subModel = "";
	private static String navigationUrl = "";
	
	
	
	

	public static String getNavigationUrl() {
		return navigationUrl;
	}

	public static void setNavigationUrl(String navigationUrl) {
		TestInputs.navigationUrl = navigationUrl;
	}

	public static String getModel() {
		return model;
	}

	public static void setModel(String model) {
		TestInputs.model = model;
	}

	public static String getSubModel() {
		return subModel;
	}

	public static void setSubModel(String subModel) {
		TestInputs.subModel = subModel;
	}

	public static List<Map<String, String>> getListResults() {
		return listResults;
	}

	public static void setListResults(List<Map<String, String>> listResults) {
		TestInputs.listResults = listResults;
	}

	
	
	public static Map<String, List<String>> getFilter() {
		return filter;
	}

	public static void setFilter(String key, String value) {
		List<String> values;
		if (TestInputs.filter.isEmpty()) {
			values = new ArrayList<>();
			values.add(value);
		} else {
			values = TestInputs.filter.get(key);
			values.add(value);
		}

		TestInputs.filter.put(key, values);
	}

}
