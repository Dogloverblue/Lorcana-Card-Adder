package me.lowen.ezintegrate;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * A class for easily integrating new card JSON data into a directory of existing data
 * @author jnlowen_wccnet
 *
 */

public class EZIntegrater {

	private File directory;
	/**
	 * 
	 * @param directory The directory with the existing card data
	 */
	public EZIntegrater(File directory) {
		this.directory = directory;
	}
	
	public void integrateJSONObject(JSONObject jo) {
		String nameToLookFor = jo.getString("name").toLowerCase().replace(" ", "_");
		boolean found = false;
		for (File file : directory.listFiles()) {
			try {
				if (file.getName().equalsIgnoreCase(nameToLookFor + ".txt")) {
					found = true;
					JSONObject jo2 = new JSONObject(Files.readString(Paths.get(file.getAbsolutePath())));
					jo2.put(jo.getString("subtitle").toLowerCase().replace(" ", "_"), jo);
					BufferedWriter br = new BufferedWriter(new FileWriter(file));
					jo2.write(br, 2, 2);
					br.close();
					System.out.println("Found and placed in " + file.getName());
					break;
				}
			
			} catch (JSONException e) {
				System.out.println("JSONFailed at " + file.getName());
			} catch (IOException e) {
			System.out.println("IOFailed at " + file.getName());
			e.printStackTrace();
		}
		}
		// If a file doesn't already exist, create one
		if (found == false) {
			File file = new File(directory + System.getProperty("file.separator") + jo.getString("name").toLowerCase().replace(" ", "_") + ".txt");
			JSONObject jo2 = new JSONObject();
			
			try {
				BufferedWriter br = new BufferedWriter(new FileWriter(file));
				try {
				jo2.put(jo.getString("subtitle").toLowerCase().replace(" ", "_"), jo);
				jo2.write(br, 2, 2);
				
				} catch (JSONException e) {
					jo.write(br, 2, 2);
				}
				br.close();
				System.out.println("Created " + file.getName());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void integrateJSONStringData(String JSONDataString) {
		integrateJSONObject(new JSONObject(JSONDataString));;
	}
	
	public void integrateJSONFile(File file) {
		try {
			integrateJSONObject(new JSONObject(Files.readString(Paths.get(file.getAbsolutePath()))));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
