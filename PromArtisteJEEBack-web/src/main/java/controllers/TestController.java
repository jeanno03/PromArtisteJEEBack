package controllers;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import dto.MyUserDto;
import dto.MyVideoDto;
import entities.MySpace;
import entities.MyUser;
import services.ArtistServiceLocal;

@Path("/TestController")
public class TestController {

	ArtistServiceLocal artistServiceLocal = lookupArtistServiceLocal();

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
	@Path("/getTestMyUser")
	@Produces({MediaType.APPLICATION_JSON}) 
	//http://localhost:8080/PromArtisteJEEBack-web/rest/TestController/getTestMyUser
	//Object sans base ==> fonctionne nickel
	public MyUser getTestMyUser() {
		
		MyUser my = new MyUser("go@go.com","Gia","Gio","Ga");
		
		return my;
	}

	@GET
	@Path("/getTestMySpace")
	@Produces({MediaType.APPLICATION_JSON}) 
	//http://localhost:8080/PromArtisteJEEBack-web/rest/TestController/getTestMySpace
	//Object sans base ==> fonctionne nickel
	public MySpace getTestMySpace() {
		
		MyUser my = new MyUser("go@go.com","Gia","Gio","Ga");
		MySpace ma = new MySpace(10L,"roi");
		ma.setMyUser(my);
		
		return ma;
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
