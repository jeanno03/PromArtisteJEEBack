package services;

import java.io.FileInputStream;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class PathService implements PathServiceInterface {

	private static Properties prop;
	private static FileInputStream propFile;


	private void loadPropertiesFile() {
		prop = new Properties();
		try {
			propFile = new FileInputStream("/home/jeanno/eclipse-workspace-09-2018/PromArtisteJEEBack/PromArtisteJEEBack-ejb/src/FileInputStream.properties");
		} catch (FileNotFoundException e) {

			e.printStackTrace();
		} 
		try {
			prop.load(propFile);
		} catch (IOException e) {

			e.printStackTrace();
		}
	}

	public String getServerLocation(){
		loadPropertiesFile();
		String serverLocation = prop.getProperty("serverLocation");
		System.out.println("serverLocation properties : " + serverLocation);
		return serverLocation; 

	}

}
