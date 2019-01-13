package controllers;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
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
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import org.apache.commons.io.IOUtils;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;

import dto.MyPictureDto;
import dto.MyUserDto;
import dto.MyVideoDto;
import entities.MyPicture;
import entities.MySpace;
import entities.MyUser;
import services.ArtistServiceLocal;
import services.EjbService;
import services.EjbServiceInterface;
import services.FileServiceLocal;
import services.FrontService;
import services.FrontServiceInterface;
import services.PathService;
import services.PathServiceInterface;

@Path("/TestController")
public class TestController {

	private EjbServiceInterface ejbService = new EjbService();
	private FrontServiceInterface frontService = new FrontService ();
	private ArtistServiceLocal artistServiceLocal = ejbService.lookupArtistServiceLocal() ;
	private FileServiceLocal fileServiceLocal = ejbService.lookupFileServiceLocal() ;

	public TestController() {
		super();
	}

	@GET
	@Produces("text/plain")
	//http://localhost:8080/PromArtisteJEEBack-web/rest/TestController
	public String helloWorld() {
		//Je dois récupérer le dernier id de picture
		MyPicture mypicture = artistServiceLocal.getLastPicture();
		String lastId = String.valueOf(mypicture.getId());
		System.out.println("lastId : " + lastId);
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
			@PathParam("lastName") String lastName){
		MyUser myUser = new MyUser(email, artistName, firstName, lastName);
		artistServiceLocal.saveMyUser(myUser);

		MyUserDto myUserDto = artistServiceLocal.getMyUserDtoByEmail(email);
		return myUserDto;

	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/saveMyUserPost/")
	//http://localhost:8080/PromArtisteJEEBack-web/rest/TestController/saveMyUserPost/
	public MyUserDto saveMyUser(MyUser myUser){
		System.out.println(myUser.getArtistName());
		artistServiceLocal.saveMyUser(myUser);
		MyUserDto myUserDto = artistServiceLocal.getMyUserDtoByEmail(myUser.getEmail());
		return myUserDto;
	}

	//	https://www.mkyong.com/webservices/jax-rs/file-upload-example-in-resteasy/
	@PUT
	@Path("/upload/{id}")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	//http://localhost:8080/PromArtisteJEEBack-web/rest/TestController/upload/1
	public Response uploadFile(
			@PathParam("id") Long mySpaceId,
			MultipartFormDataInput input
			) throws IOException, InterruptedException { 

		//Fonctionne mais méthode moin précise
		//https://docs.jboss.org/resteasy/docs/2.2.1.GA/javadocs/org/jboss/resteasy/plugins/providers/multipart/MultipartFormDataInput.html

		//		Map<String,List<InputPart>> mapListInputPart = input.getFormDataMap();
		//		List<InputPart> inputParts = new ArrayList<InputPart> ();

		//		for(Map.Entry<String, List<InputPart>> entry : mapListInputPart.entrySet()) {
		//			System.out.println("key of Map : " + entry.getKey());
		//			System.out.println("Value of Map : " + entry.getValue());
		//			inputParts = entry.getValue();
		//		}	

		
		//********************************Important *********************************
		//Méthode plus précise car il car il faut que le nom du form corresponde à nameToDetermineInForm
		Map<String, List<InputPart>> uploadForm = input.getFormDataMap();
		List<InputPart> inputParts = uploadForm.get("nameToDetermineInForm");
		Date day = new Date();
		String path = "";

		for (InputPart inputPart : inputParts) {
			try {

				MultivaluedMap<String, String> header = inputPart.getHeaders();

				//nom original du fichier à sauvegarder
				String originName = frontService.getFileName(header);
				//convert the uploaded file to inputstream
				InputStream inputStream = inputPart.getBody(InputStream.class,null);
				byte [] bytes = IOUtils.toByteArray(inputStream);

				path = artistServiceLocal.createMyPicturePath(originName);

				frontService.writeFile(bytes,path);
				//délai pour écriture sur serveur
				Thread.sleep(2000);

				MyPicture myPicture = new MyPicture(day, path, originName);
				System.out.println("day : " + day);
				System.out.println("path : " + path);
				System.out.println("originName : " + originName);
				MyPictureDto myPictureDto = artistServiceLocal.saveMyPicture(myPicture, mySpaceId);
				System.out.println("myPictureDto.getId() : " + myPictureDto.getId());
				System.out.println("Done");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return Response.status(200)
				.entity("uploadFile to path : " + path).build();
	}



	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/toConnect/")
	//http://localhost:8080/PromArtisteJEEBack-web/rest/TestController/toConnectPost/
	public MyUserDto toConnectPost(MyUser myUser) throws Exception{
		MyUserDto myUserDto = artistServiceLocal.getConnect(myUser.getEmail(), myUser.getMdp());
		return myUserDto;
	}


	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/toConnectGet/{email}/{mdp}")
	//http://localhost:8080/PromArtisteJEEBack-web/rest/TestController/toConnectGet/jean.jean@gmail.com/1234/
	public MyUserDto toConnectGet(
			@PathParam("email") String email, 
			@PathParam("mdp") String mdp) throws Exception {
		MyUserDto myUserDto = artistServiceLocal.getConnect(email, mdp);
		return myUserDto;

	}


}
