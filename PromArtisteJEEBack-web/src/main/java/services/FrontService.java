package services;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.ws.rs.core.MultivaluedMap;

/**
 * @author jeanno
 *Cette class contient toutes les méthodes de contôles de tous les controleurs services rest
 */
public class FrontService implements FrontServiceInterface{



	//méthode pour controler que le fichier recu comporte une extension
	public Boolean testExtension(String fileName) {
		try {
			String [] fileTab = fileName.split("");
			int t= fileTab.length;
			System.out.println("extension du fichier : " +fileTab[t-4]+fileTab[t-3]+fileTab[t-2]+fileTab[t-1]);
			if(fileTab[t-4].equals(".")) {
				return true;
			}
		}catch(NullPointerException ex) {
			ex.printStackTrace();
		}catch(ArrayIndexOutOfBoundsException ex) {
			ex.printStackTrace();
		}
		return false;

	}


	public  String getFileName(MultivaluedMap<String, String> header) {
		String[] contentDisposition = header.getFirst("Content-Disposition").split(";");
		for (String filename : contentDisposition) {
			if ((filename.trim().startsWith("filename"))) {
				String[] name = filename.split("=");
				String finalFileName = name[1].trim().replaceAll("\"", "");
				System.out.println("finalFileName : " + finalFileName);
				return finalFileName;
			}
		}
		return "unknown";
	}


	//save to somewhere
	public void writeFile(byte[] content, String filename) throws IOException {
		File file = new File(filename);
		if (!file.exists()) {
			file.createNewFile();
		}
		FileOutputStream fop = new FileOutputStream(file);
		fop.write(content);
		fop.flush();
		fop.close();
	}


}
