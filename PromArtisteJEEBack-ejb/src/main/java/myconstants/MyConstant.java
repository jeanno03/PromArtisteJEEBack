package myconstants;

import java.util.Properties;
import java.util.logging.Logger;

import javax.ejb.EJB;

import mysingleton.MySingleton;

public interface MyConstant {
	
	public static final Logger LOGGER = Logger.getLogger("logs");
	public static final String PATH_LOG = "/home/jeanno/eclipse-workspace-09-2018/Git/PromArtisteJEEBack/PromArtisteJEEBack-ejb/src/";
	public static final String PATH_FILE_INPUT_STREAM = "/home/jeanno/eclipse-workspace-09-2018/Git/PromArtisteJEEBack/PromArtisteJEEBack-ejb/src/FileInputStream.properties";
	public static final Properties PROP = new Properties();

	public static String getPropertyParameter(String para) {
		String str = MyConstant.PROP.getProperty(para);
		return str;
	}
	


}
