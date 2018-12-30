package controllers;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import org.apache.commons.io.IOUtils;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;

import dto.MyUserDto;
import dto.MyVideoDto;
import entities.MySpace;
import entities.MyUser;
import services.ArtistServiceLocal;

@Path("/TestController")
public class TestController {

	ArtistServiceLocal artistServiceLocal = lookupArtistServiceLocal();
	private final String SERVER_UPLOAD_LOCATION_FOLDER = "/home/jeanno/PromoArtistFile/";

	public TestController() {
		super();
	}

	@GET
	@Produces("text/plain")
	//http://localhost:8080/PromArtisteJEEBack-web/rest/TestController
	public String helloWorld() {
		return "Hello World";
	}

	@GET
	@Path("/testMyUsers")
	//http://localhost:8080/PromArtisteJEEBack-web/rest/TestController/testMyUsers
	//insertion jeu d essai dans bdd
	public String dataTestestMyUsers(){
		String myUsersString = artistServiceLocal.getMyUserDataTest();
		return myUsersString;
	}

	@GET
	@Path("/getMySpaceList")
	//http://localhost:8080/PromArtisteJEEBack-web/rest/TestController/getMySpaceList
	public String getMySpaceList(){
		List<MySpace> mySpaces = artistServiceLocal.getAllMySpace();
		for(MySpace m : mySpaces) {
			System.out.println("myspace : " + m.getName());
		}
		return "List mySpace Ok";
	}


	@GET
	@Path("/getMyUserList")
	//http://localhost:8080/PromArtisteJEEBack-web/rest/TestController/getMyUserList
	public String getMyUserList(){
		List<MyUser> myUsers = artistServiceLocal.getAllMyUser();
		for(MyUser m : myUsers) {
			System.out.println("getId : " + m.getId());
			System.out.println("getArtistName : " + m.getArtistName());
		}
		return "List myUsers Ok";
	}

	@GET
	@Path("/getMyUserDtoList")
	@Produces({MediaType.APPLICATION_JSON}) 
	//http://localhost:8080/PromArtisteJEEBack-web/rest/TestController/getMyUserDtoList
	//fonctionne Ok ==> le mappage des objects de la base est obligatoire
	//si pas de dto ==> could not initialize proxy [entities.xxx] - no Session 
	public List<MyUserDto> getMyUserDtoList(){
		List<MyUserDto> myUsersDto = artistServiceLocal.getAllMyUserDto() ;	
		return myUsersDto;
	}

	@GET
	@Path("/getMyUserDto/{id}")
	@Produces({MediaType.APPLICATION_JSON}) 
	//http://localhost:8080/PromArtisteJEEBack-web/rest/TestController/getMyUserDto/2
	//mappage vers un dto ==> OK
	//si pas de dto ==> could not initialize proxy [entities.MyUser#2] - no Session 
	public MyUserDto getMyUserDto(@PathParam("id") Long id) {
		MyUserDto myUserDto = artistServiceLocal.getMyUserDto(id);
		return myUserDto;
	}

	@GET
	@Path("/getAllMyVideoDto")
	@Produces({MediaType.APPLICATION_JSON}) 
	//http://localhost:8080/PromArtisteJEEBack-web/rest/TestController/getAllMyVideoDto
	public List<MyVideoDto> getAllMyVideoDto(){

		List<MyVideoDto> myVideosDto = artistServiceLocal.getAllMyVideoDto();
		return myVideosDto;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/saveMyUserGet/{email}/{artistName}/{firstName}/{lastName}")
	//http://localhost:8080/PromArtisteJEEBack-web/rest/TestController/saveMyUserGet
	public MyUserDto saveMyUserGet(
			@PathParam("email") String email, 
			@PathParam("artistName") String artistName,
			@PathParam("firstName") String firstName,
			@PathParam("lastName") String lastName) {
		MyUser myUser = new MyUser(email, artistName, firstName, lastName);
		artistServiceLocal.saveMyUser(myUser);

		MyUserDto myUserDto = artistServiceLocal.getMyUserDtoByEmail(email);
		return myUserDto;

	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/saveMyUserPost/")
	//http://localhost:8080/PromArtisteJEEBack-web/rest/TestController/saveMyUserPost/
	public MyUserDto saveMyUser(MyUser myUser) {
		System.out.println(myUser.getArtistName());
		artistServiceLocal.saveMyUser(myUser);
		MyUserDto myUserDto = artistServiceLocal.getMyUserDtoByEmail(myUser.getEmail());
		return myUserDto;
	}

	//	https://www.mkyong.com/webservices/jax-rs/file-upload-example-in-resteasy/
	@POST
	@Path("/upload")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	//http://localhost:8080/PromArtisteJEEBack-web/rest/TestController/upload/
	public Response uploadFile(
			MultipartFormDataInput input
			) throws IOException { 

		//https://docs.jboss.org/resteasy/docs/2.2.1.GA/javadocs/org/jboss/resteasy/plugins/providers/multipart/MultipartFormDataInput.html
		String fileName = "";
		Map<String,List<InputPart>> testMap = input.getFormDataMap();
		List<InputPart> inputParts = new ArrayList<InputPart> ();
		for(Map.Entry<String, List<InputPart>> entry : testMap.entrySet()) {
			System.out.println("key of Map : " + entry.getKey());
			System.out.println("Value of Map : " + entry.getValue());
			inputParts = entry.getValue();
		}

		for (InputPart inputPart : inputParts) {
			try {
				MultivaluedMap<String, String> header = inputPart.getHeaders();
				fileName = getFileName(header);
				//convert the uploaded file to inputstream
				InputStream inputStream = inputPart.getBody(InputStream.class,null);
				byte [] bytes = IOUtils.toByteArray(inputStream);
				//constructs upload file path
				fileName = SERVER_UPLOAD_LOCATION_FOLDER + fileName;
				writeFile(bytes,fileName);
				System.out.println("Done");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return Response.status(200)
				.entity("uploadFile is called, Uploaded file name : " + fileName).build();
	}


	private String getFileName(MultivaluedMap<String, String> header) {
		String[] contentDisposition = header.getFirst("Content-Disposition").split(";");
		for (String filename : contentDisposition) {
			if ((filename.trim().startsWith("filename"))) {
				String[] name = filename.split("=");
				String finalFileName = name[1].trim().replaceAll("\"", "");
				return finalFileName;
			}
		}
		return "unknown";
	}


	//save to somewhere
	private void writeFile(byte[] content, String filename) throws IOException {
		File file = new File(filename);
		if (!file.exists()) {
			file.createNewFile();
		}
		FileOutputStream fop = new FileOutputStream(file);
		fop.write(content);
		fop.flush();
		fop.close();
	}


	private ArtistServiceLocal lookupArtistServiceLocal() {
		try {
			Context c = new InitialContext();
			return (ArtistServiceLocal) c.lookup(
					"java:global/PromArtisteJEEBack-ear/PromArtisteJEEBack-ejb/ArtistService!services.ArtistServiceLocal");
		}catch(NamingException ne) {
			Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
			throw new RuntimeException(ne);
		}

	}



}
