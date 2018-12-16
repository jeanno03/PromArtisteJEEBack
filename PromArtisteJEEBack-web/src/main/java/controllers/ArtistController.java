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

import entities.MySpace;
import entities.MyUser;
import services.ArtistServiceLocal;

@Path("/ArtistController")
public class ArtistController {

	ArtistServiceLocal artistServiceLocal = lookupArtistServiceLocal();

	public ArtistController() {
		super();
	}

	@GET
	@Produces("text/plain")
	public String helloWorld() {
		return "Hello World";
	}

	@GET
	@Path("/testMyUsers")
	public String dataTestestMyUsers(){
		String myUsersString = artistServiceLocal.getMyUserDataTest();
		return myUsersString;
	}

	@GET
	@Path("/getMySpaceList")
	//http://localhost:8080/PromArtisteJEEBack-web/rest/ArtistController/getMySpaceList
	public String getMySpaceList(){
		List<MySpace> mySpaces = artistServiceLocal.getAllMySpace();

		for(MySpace m : mySpaces) {
			System.out.println("myspace : " + m.getName());
		}


		return "List mySpace Ok";
	}

	@GET
	@Path("/getMySpaceList02")
	//http://localhost:8080/PromArtisteJEEBack-web/rest/ArtistController/getMySpaceList02
	//Cela générera un fichier XML d'une profondeur infinie ==> A revoir
	public List<MySpace> getMySpaceList02(){
		List<MySpace> mySpaces = artistServiceLocal.getAllMySpace();

		for(MySpace m : mySpaces) {
			System.out.println("myspace : " + m.getName());
		}


		return mySpaces;
	}

	@GET
	@Path("/getMyUserList")
	//http://localhost:8080/PromArtisteJEEBack-web/rest/ArtistController/getMyUserList
	public String getMyUserList(){
		List<MyUser> myUsers = artistServiceLocal.getAllMyUser();

		for(MyUser m : myUsers) {
			System.out.println("getId : " + m.getId());
			System.out.println("getArtistName : " + m.getArtistName());
		}



		return "List myUsers Ok";
	}

	@GET
	@Path("/getMyUserList02")
	//http://localhost:8080/PromArtisteJEEBack-web/rest/ArtistController/getMyUserList02
	//Cela générera un fichier XML d'une profondeur infinie ==> A revoir
	public List<MyUser> getMyUserList02(){
		List<MyUser> myUsers = artistServiceLocal.getAllMyUser();

		for(MyUser m : myUsers) {
			System.out.println("getId : " + m.getId());
			System.out.println("getArtistName : " + m.getArtistName());
		}


		return myUsers;
	}
	
	@GET
	@Path("/getMyUser/{id}")
	//http://localhost:8080/PromArtisteJEEBack-web/rest/ArtistController/getMyUser/2
	//could not initialize proxy [entities.MyUser#2] - no Session ==> A revoir
	public MyUser getMyUser(@PathParam("id") Long id) {
		MyUser myUser = artistServiceLocal.getMyUser(id);
		System.out.println("myUser.getArtistName() : " + myUser.getArtistName());
		return null;
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
