package me.lowen;

import java.util.HashMap;

public class CardAdderUtils {

	public static HashMap<String, String> getImagesURLs(String name) {
		HashMap<String, String> urls = new HashMap<String, String>();
		
		String[] imageTypes = {"large", "medium", "small", "art-crop", "no-art"};
		
		for (String imageType: imageTypes) {
			urls.put(imageType, "https://lorcana-api.com/images/" + name + "/" + imageType + ".png");
		}
		return urls;
	}

}
