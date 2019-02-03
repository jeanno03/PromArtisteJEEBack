package myconstants;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import javax.ejb.EJB;

import mysingleton.MySingleton;

public interface MyConstant {

	public static final Properties PROP = new Properties();
	public static final String PATH_FILE_INPUT_STREAM = "/home/jeanno/eclipse-workspace-09-2018/Git/PromArtisteJEEBack/PromArtisteJEEBack-ejb/FileInputStream.properties";
	//To modify before build
	//	public static final String PATH_FILE_INPUT_STREAM = "FileInputStream.properties";

	public static final Logger LOGGER = Logger.getLogger("logs");

	public static String getPropertyParameter(String para) {
		String str = MyConstant.PROP.getProperty(para);
		return str;
	}
	
	public static void getFileHandler() {

		boolean append = true;
		Date day = new Date();
		SimpleDateFormat formater = null;
		formater = new SimpleDateFormat("ddMMyy");

		try {  

			FileHandler fh = new FileHandler(MyConstant.getPropertyParameter("logs")+""+formater.format(day)+"-prom-artiste.log", append); 
			
			LOGGER.addHandler(fh);
			SimpleFormatter formatter = new SimpleFormatter();  
			fh.setFormatter(formatter);  

		} catch (SecurityException ex) {  
			ex.printStackTrace();  
		} catch (IOException ex) {  
			ex.printStackTrace();  
		}  

	}



}
