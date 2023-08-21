package me.lowen;

import java.util.HashMap;

public class CardAdderUtils {

	public static HashMap<String, String> getImagesURLsForCharacter(String name, String subtitle) {
		HashMap<String, String> urls = new HashMap<String, String>();
		
		String[] imageTypes = {"large", "medium", "small", "art-crop", "no-art"};
		
		for (String imageType: imageTypes) {
			urls.put(imageType, "https://lorcana-api.com/images/" + name + "/" + subtitle + "/" + name + "-" + subtitle + "-" + imageType + ".png");
		}
		return urls;
	}
	
	public static HashMap<String, String> getImagesURLsForNonCharacter(String name) {
		HashMap<String, String> urls = new HashMap<String, String>();
		
		String[] imageTypes = {"large", "medium", "small", "art-crop", "no-art"};
		
		for (String imageType: imageTypes) {
			urls.put(imageType, "https://lorcana-api.com/images/" + name + "/" + name + "-" +  imageType + ".png");
		}
		return urls;
	}

}
