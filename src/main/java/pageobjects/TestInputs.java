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
