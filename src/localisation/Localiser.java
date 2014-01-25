package localisation;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class Localiser {
	private static final String BUNDLE_NAME = "localisation.de";

	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME);

	private Localiser() {
	}

	public static String getString(String key) {
		try {
			return RESOURCE_BUNDLE.getString(key);
		} catch (MissingResourceException e) {
			return '!' + key + '!';
		}
	}
}
