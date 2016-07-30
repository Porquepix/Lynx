package core.translation;

import java.util.List;

public interface Translator {

    public String translate(String key);

    public List<String> translateAll(List<String> keys);

    public static String getValidLanguage(List<String> availiableLangs, String... attemptLangs) {
	int i = 0;
	boolean found = false;
	while (i < attemptLangs.length && !found) {
	    if (availiableLangs.contains(attemptLangs[i])) {
		found = true;
	    } else {
		i++;
	    }
	}
	return found ? attemptLangs[i] : null;
    }

}
