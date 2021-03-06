package controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.server.ResourceConfig;
import org.jose4j.jwk.JsonWebKey;
import org.jose4j.jwk.JsonWebKeySet;
import org.jose4j.jwk.RsaJsonWebKey;
import org.jose4j.jwk.RsaJwkGenerator;
import org.jose4j.jws.AlgorithmIdentifiers;
import org.jose4j.jws.JsonWebSignature;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.consumer.InvalidJwtException;
import org.jose4j.jwt.consumer.JwtConsumer;
import org.jose4j.jwt.consumer.JwtConsumerBuilder;
import org.jose4j.lang.JoseException;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;

import dto.MyUserDto;
import entities.MyPicture;
import entities.MyRole;
import entities.MyUser;
import filters.CorsFilter;
import myconstants.MyConstant;
import services.ArtistServiceLocal;
import services.EjbService;
import services.EjbServiceInterface;
import services.FileServiceLocal;
import services.FrontService;
import services.FrontServiceInterface;
import services.JwtService;
import services.JwtServiceInterface;
import services.SecurityServiceLocal;

@Path("/JwtController")
public class JwtController {

	private static EjbServiceInterface ejbService = new EjbService();
	private static FrontServiceInterface frontService = new FrontService ();
	private static ArtistServiceLocal artistServiceLocal = ejbService.lookupArtistServiceLocal() ;
	private static FileServiceLocal fileServiceLocal = ejbService.lookupFileServiceLocal() ;
	private static JwtServiceInterface jwtService = new JwtService() ;

	//	final ResourceConfig resourceConfig = new ResourceConfig();

	public JwtController() {
		super();
		//		resourceConfig.register(new CORSFilter());
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
	@Path("/toConnectJwtPost/")
	//http://localhost:8080/PromArtisteJEEBack-web/rest/JwtController/toConnectJwtPost
	//body email:jean.jean@gmail.com et mdp:1234
	//phou.jeannory@gmail.com 12345678
	public Response toConnectJwtPost(MyUser myUser)throws Exception{
		try {

			Boolean test = artistServiceLocal.getConnectBoolean(myUser.getEmail(),myUser.getMdp());

			if(test) {
				String jwt = jwtService.getJwt(myUser.getEmail());	
				String str = "{\"token\":\""+jwt+"\"}";
				MyConstant.LOGGER.info("str : " + str);
				return Response.status(200)
						.header("Access-Control-Allow-Origin", "*")
						.header("Access-Control-Allow-Credentials", "true")
						.header("Access-Control-Allow-Headers","origin, content-type, accept, authorization")
						.header("Access-Control-Allow-Methods","GET, POST, PUT, DELETE, OPTIONS, HEAD")
						.entity(str).build();
			}

		}catch(Exception ex) {
			MyConstant.LOGGER.info("Exception : " + ex);
		}
		String errorMess = "error bad authentification";
		String str = "{\"error message\":\""+errorMess+"\"}";
		MyConstant.LOGGER.info("str : " + str);
		return Response.status(401).entity(str).build();



	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/toConnectJwtHeader/")
	//http://localhost:8080/PromArtisteJEEBack-web/rest/JwtController/toConnectJwtHeader
	//body email:phou.jeannory@gmail.com et mdp:12345678
	public Response toConnectJwtHeader(
			@HeaderParam("email") String email,
			@HeaderParam("mdp") String mdp)
					throws Exception{

		MyConstant.LOGGER.info("email : " + email);
		MyConstant.LOGGER.info("mdp : " + mdp);
		try {
			Boolean test = artistServiceLocal.getConnectBoolean(email,mdp);
			if (test) {

				String jwt = jwtService.getJwt(email);		 
				String str = "{\"token\":\""+jwt+"\"}";
				MyConstant.LOGGER.info("str : " + str);
				return Response.status(Response.Status.OK).entity(str).build();
			}
		}catch(Exception ex) {
			MyConstant.LOGGER.info("Exception : " + ex);
		}
		String errorMess = "error bad authentification";
		String str = "{\"token\":\""+errorMess+"\"}";
		MyConstant.LOGGER.info("str : " + str);
		return Response.status(Response.Status.FORBIDDEN).entity(str).build();
	}
	//https://stackoverflow.com/questions/23450494/how-to-enable-cross-domain-requests-on-jax-rs-web-services
	@GET
	@Produces({MediaType.TEXT_PLAIN})
	@Path("/toConnectJwtHeaderV2/")
	//http://localhost:8080/PromArtisteJEEBack-web/rest/JwtController/toConnectJwtHeaderV2
	//body email:phou.jeannory@gmail.com et mdp:12345678
	public Response toConnectJwtHeaderV2(
			@HeaderParam("email") String email,
			@HeaderParam("mdp") String mdp)
					throws Exception{

		MyConstant.LOGGER.info("email : " + email);
		MyConstant.LOGGER.info("mdp : " + mdp);

		byte[] decodeBytesEmail = Base64.getUrlDecoder().decode(email);
		byte[] decodeBytesMdp = Base64.getUrlDecoder().decode(mdp);

		String decodeUrlEmail = new String(decodeBytesEmail);
		String decodeUrlMdp = new String(decodeBytesMdp);

		MyConstant.LOGGER.info("decodeUrlEmail : " + decodeUrlEmail);
		MyConstant.LOGGER.info("decodeUrlMdp : " + decodeUrlMdp);

		try {
			Boolean test = artistServiceLocal.getConnectBoolean(decodeUrlEmail,decodeUrlMdp);
			if (test) {

				String jwt = jwtService.getJwt(decodeUrlEmail);		 

				String str = "{\"token\":\""+jwt+"\"}";
				MyConstant.LOGGER.info("str : " + str);
				return Response
						.status(200)
						.header("Access-Control-Allow-Origin", "*")
						.header("Access-Control-Allow-Credentials", "true")
						.header("Access-Control-Allow-Headers","origin, content-type, accept, authorization, email, mdp")
						.header("Access-Control-Allow-Methods","GET, POST, PUT, DELETE, OPTIONS, HEAD")
						.entity(str).build();
			}
		}catch(Exception ex) {
			MyConstant.LOGGER.info("Exception : " + ex);
		}
		String errorMess = "error bad authentification";
		String str = "{\"token\":\""+errorMess+"\"}";
		MyConstant.LOGGER.info("str : " + str);
		return Response.status(401).entity(str).build();
	}

	@GET
	@Path("/getMyUserDto/{id}")
	@Produces({MediaType.APPLICATION_JSON}) 
	//http://localhost:8080/PromArtisteJEEBack-web/rest/JwtController/getMyUserDto/2
	public Response getMyUserDto(@HeaderParam("token") String token, @PathParam("id") Long id)
			throws JsonGenerationException, JsonMappingException, IOException {

		try {
			//if map not null ==> token is valide
			Map<String,Object> map = jwtService.testJwt(token);
			MyUserDto myUserDto = artistServiceLocal.getMyUserDto(id);
			map.put( "myUserDto", myUserDto );

			return Response.status(Response.Status.OK).entity(map).build();
		}catch(Exception ex) {
			MyConstant.LOGGER.info("Exception : " + ex);
		}

		String errorMess = "error bad authentification";
		String str = "{\"token\":\""+errorMess+"\"}";
		MyConstant.LOGGER.info("str : " + str);
		return Response.status(Response.Status.FORBIDDEN).entity(str).build();

	}


}
