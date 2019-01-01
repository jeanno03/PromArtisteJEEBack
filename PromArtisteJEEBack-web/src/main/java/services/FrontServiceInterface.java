package services;

import java.io.IOException;

import javax.ws.rs.core.MultivaluedMap;

public interface FrontServiceInterface {

	public Boolean testExtension(String fileName) ;
	public  String getFileName(MultivaluedMap<String, String> header) ;
	public void writeFile(byte[] content, String filename) throws IOException ;

}
