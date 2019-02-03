package controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jose4j.jwk.JsonWebKey;
import org.jose4j.jwk.RsaJsonWebKey;
import org.jose4j.jwk.RsaJwkGenerator;
import org.jose4j.jws.AlgorithmIdentifiers;
import org.jose4j.jws.JsonWebSignature;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.lang.JoseException;

import entities.MyPicture;
import entities.MyRole;
import entities.MyUser;
import myconstants.MyConstant;
import services.ArtistServiceLocal;
import services.AuthenticationFilter;
import services.EjbService;
import services.EjbServiceInterface;
import services.FileServiceLocal;
import services.FrontService;
import services.FrontServiceInterface;
import services.ResponseBuilder;
import services.SecurityServiceLocal;
import services.jwt.JwtService;
import services.jwt.JwtServiceInterface;

@Path("/JwtController")
public class JwtController {
	
	private static EjbServiceInterface ejbService = new EjbService();
	private static FrontServiceInterface frontService = new FrontService ();
	private static ArtistServiceLocal artistServiceLocal = ejbService.lookupArtistServiceLocal() ;
	private static FileServiceLocal fileServiceLocal = ejbService.lookupFileServiceLocal() ;
	
	static List<JsonWebKey> jsonWebKeys = JwtService.jsonWebKeys;

	
	public JwtController() {
		super();
	}
	
	//http://localhost:8080/PromArtisteJEEBack-web/rest/TestController/testMyUsers
	//insertion jeu d essai dans bdd
	
	
	
	@GET
	@Produces("text/plain")
	//http://localhost:8080/PromArtisteJEEBack-web/rest/JwtController
	public String helloWorld() {
		return "Hello World";
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/toConnectJwt/")
	//http://localhost:8080/PromArtisteJEEBack-web/rest/JwtController/toConnectJwt
	//body email:jean.jean@gmail.com et mdp:1234
	public Response toConnectJwt(MyUser myUser)throws Exception{
		
		//test
		for (int i=0;i<jsonWebKeys.size();i++) {
				MyConstant.LOGGER.info("jsonWebKeys.get(i).getKeyId() " + jsonWebKeys.get(i).getKeyId());
				MyConstant.LOGGER.info("jsonWebKeys.get(i).toJson() " + jsonWebKeys.get(i).toJson() );
		}
		
		
		JwtService jwtService = new JwtService();
		if (artistServiceLocal.getConnectBoolean(myUser.getEmail(),myUser.getMdp())) {

			 String jwt = jwtService.getJwt(myUser.getEmail());
			return Response.status(200).entity(jwt).build();
		}
		return ResponseBuilder.createResponse( Response.Status.FORBIDDEN );
	}


}
