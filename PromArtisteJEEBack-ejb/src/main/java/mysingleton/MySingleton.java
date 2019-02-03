package mysingleton;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import javax.ejb.LocalBean;
import javax.ejb.Singleton;

import myconstants.MyConstant;
import services.ArtistService;

/**
 * Session Bean implementation class MySingleton
 */
@Singleton
@LocalBean
public class MySingleton{

	private static MySingleton mySingleton = new MySingleton();

	public MySingleton() {	
		super();
		startSingleton();

	}

	public static MySingleton getInstance() {
		return mySingleton;
	}

	private void startSingleton() {

		System.out.println("***********Singleton start **************");
		loadPropertiesFile();	
		//logs start here first then follow in MyConstant
		MyConstant.getFileHandler();
		MyConstant.LOGGER.info("Application start");

	}
	
	private void loadPropertiesFile() {

		FileInputStream propFile = null;
		
		try {
			
			propFile = new FileInputStream(MyConstant.PATH_FILE_INPUT_STREAM);
			MyConstant.PROP.load(propFile);

		} catch (FileNotFoundException ex) {

			ex.printStackTrace();
			
		} catch (IOException ex) {
			ex.printStackTrace();			
		}finally {
			
			if(null!=propFile) {
				try {
					propFile.close();
				}catch(Exception ex) {
					ex.printStackTrace();	
				}
			}
		}

	}

}
