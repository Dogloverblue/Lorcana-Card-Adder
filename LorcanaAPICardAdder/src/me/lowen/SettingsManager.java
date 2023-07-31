package me.lowen;

import java.io.File;
import java.util.prefs.Preferences;

public class SettingsManager {

	public static Preferences prefs = Preferences.userRoot();

	public static Preferences getPreferences() {
		return prefs;
	}

	public static void setPreferences(Preferences prefs) {
		SettingsManager.prefs = prefs;
	}
	
	public static String getSavePath() {
		return prefs.get("LorcanaCardAdder.SaveDirectory", "Select a Path");
	}
	
	public static void setSavePath(String value) {
		prefs.put("LorcanaCardAdder.SaveDirectory", value);
	}
	
	

}
