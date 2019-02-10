package services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.ws.rs.Path;

import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.MalformedClaimException;
import org.jose4j.jwt.consumer.InvalidJwtException;
import org.jose4j.jwt.consumer.JwtConsumer;
import org.jose4j.jwt.consumer.JwtConsumerBuilder;
import org.jose4j.lang.JoseException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import dto.MyRoleDto;
import dto.MyUserDto;

import org.jose4j.jwk.JsonWebKey;
import org.jose4j.jwk.JsonWebKeySet;
import org.jose4j.jwk.RsaJsonWebKey;
import org.jose4j.jwk.RsaJwkGenerator;
import org.jose4j.jws.AlgorithmIdentifiers;
import  org.jose4j.jws.JsonWebSignature;

import entities.MyRole;
import entities.MyUser;
import myconstants.MyConstant;
import services.ArtistServiceLocal;
import services.FileServiceLocal;
import services.SecurityServiceLocal;


//https://avaldes.com/jax-rs-security-using-json-web-tokens-jwt-for-authentication-and-authorization/

//**************a essayer ********************
//https://codinginfinite.com/authentication-java-apis-json-web-token-jwt/


public class JwtService implements JwtServiceInterface{

	private EjbServiceInterface ejbService = new EjbService();
	private FrontServiceInterface frontService = new FrontService ();
	private ArtistServiceLocal artistServiceLocal = ejbService.lookupArtistServiceLocal() ;
	private FileServiceLocal fileServiceLocal = ejbService.lookupFileServiceLocal() ;
	private SecurityServiceLocal securityServiceLocal = ejbService.lookupSecurityServiceLocal();

	public final static String AUTHORIZATION_PROPERTY = "token";

	//keys au niveau du serveur
	static	{
		MyConstant.LOGGER.info("Inside static initializer...");

		for (int kid = 1; kid <=3; kid ++) {
			JsonWebKey jsonWebKey = null;
			try {
				jsonWebKey = RsaJwkGenerator.generateJwk(2048);
				MyConstant.LOGGER.info("PUBLIC KEY (" + kid + "): "
						+jsonWebKey.toJson(JsonWebKey.OutputControlLevel.PUBLIC_ONLY));
			}catch (JoseException ex) {
				MyConstant.LOGGER.info("JoseException : " + ex);			
			}
			jsonWebKey.setKeyId(String.valueOf(kid));
			JwtServiceInterface.jsonWebKeys.add(jsonWebKey);
		}
	}

	@Override
	public String getJwt(String myUserEmail) {
		MyConstant.LOGGER.info("test : method getJwt start ");

		try {

			//if connection sucessfull retrieve the user
			MyUserDto myUserDto = artistServiceLocal.getMyUserDtoByEmail(myUserEmail);

			List<String> rolesString = new ArrayList();	

			for(MyRoleDto m : myUserDto.getMyRolesDto()) {
				rolesString.add(m.getName());			
				MyConstant.LOGGER.info("id add : " + m.getId());
				MyConstant.LOGGER.info("MyRole add : " + m.getName());
			}

			
			int kidRandom = generateRandmoKid();
			System.out.println("kidRandom : " + kidRandom);

			RsaJsonWebKey rsaJsonWebKey = (RsaJsonWebKey) JwtServiceInterface.jsonWebKeys.get(kidRandom);
			rsaJsonWebKey.setKeyId(String.valueOf(kidRandom));
			MyConstant.LOGGER.info("rsaJsonWebKey.getKeyId().toString() " + rsaJsonWebKey.getKeyId().toString());
			MyConstant.LOGGER.info("JWK (kidRandom) ===> " + rsaJsonWebKey.toJson());

			JwtClaims jwtClaims = new JwtClaims();
			// Create the Claims, which will be the content of the JWT
			//émetteur
			jwtClaims.setIssuer("http://test.jeannory.ovh");
			jwtClaims.setExpirationTimeMinutesInTheFuture(10);
			jwtClaims.setGeneratedJwtId();
			jwtClaims.setIssuedAtToNow();
			jwtClaims.setNotBeforeMinutesInThePast(2);
			jwtClaims.setSubject(myUserDto.getEmail());
			jwtClaims.setStringListClaim("myRoles", rolesString);

			JsonWebSignature jsonWebSignature = new JsonWebSignature();
			jsonWebSignature.setPayload(jwtClaims.toJson());

			jsonWebSignature.setKeyIdHeaderValue(rsaJsonWebKey.getKeyId());
			jsonWebSignature.setKey(rsaJsonWebKey.getPrivateKey());

			jsonWebSignature.setAlgorithmHeaderValue(AlgorithmIdentifiers.RSA_USING_SHA256);

			String jwt = null;
			try {
				jwt=jsonWebSignature.getCompactSerialization();
			}catch(JoseException ex) {
				MyConstant.LOGGER.info("JoseException : " + ex);	
			}catch(Exception ex) {
				MyConstant.LOGGER.info("Exception" + ex);	
			}
			MyConstant.LOGGER.info("jwt : " + jwt);	
			return jwt;
		}catch(Exception ex) {
			MyConstant.LOGGER.info("Exception" + ex);	
		}
		return null;
	}

	@Override
	public Map<String,Object> testJwt(String token) throws MalformedClaimException {


		//		je recherche le kid
		String[] tokenTab = decodeToken(token);
		String headerEncoded = tokenTab[0];
		System.out.println("headerDecoded : " + headerEncoded);
		byte[] decodeBytesHeader = Base64.getUrlDecoder().decode(headerEncoded);
		String decodeHeader = new String(decodeBytesHeader);
		System.out.println("decodeHeader : " + decodeHeader);

		//parcours du dom
		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode rootNode;
		JsonNode idNode;
		String kidV1=null;
		try {

			rootNode = objectMapper.readValue(decodeHeader, JsonNode.class);
			idNode = rootNode.path("kid");
			kidV1 = idNode.asText();

		} catch (JsonParseException ex) {
			MyConstant.LOGGER.info("JsonParseException : " + ex);
		} catch (JsonMappingException ex) {
			MyConstant.LOGGER.info("JsonMappingException : " + ex);
		} catch (IOException ex) {
			MyConstant.LOGGER.info("IOException : " + ex);
		}catch(Exception ex) {
			MyConstant.LOGGER.info("Exception : " + ex);		
		}

		JsonWebKeySet jsonWebKeySet = new JsonWebKeySet(JwtServiceInterface.jsonWebKeys); 
		JsonWebKey jsonWebKey = jsonWebKeySet.findJsonWebKey(kidV1, null,  null,  null);
		MyConstant.LOGGER.info("JWK (1) ===> " + jsonWebKey.toJson());
		// Validate Token's authenticity and check claims
		JwtConsumer jwtConsumer = new JwtConsumerBuilder()
				.setRequireExpirationTime() 
				.setAllowedClockSkewInSeconds(30)
				.setRequireSubject()
				.setExpectedIssuer("http://test.jeannory.ovh")
				.setVerificationKey(jsonWebKey.getKey())
				.build();

		//  Validate the JWT and process it to the Claims
		JwtClaims jwtClaims;
		try {
			jwtClaims = jwtConsumer.processToClaims(token);
			MyConstant.LOGGER.info("JWT validation succeeded! " + jwtClaims);
			//			MyUserDto myUserDto = artistServiceLocal.getMyUserDto(id);
			Map<String,Object> map = new HashMap<String,Object>();
			map.put( AUTHORIZATION_PROPERTY, token );

			System.out.println("myRoles : ");
			List<String> myRoles = jwtClaims.getStringListClaimValue("myRoles");
			myRoles.forEach(System.out::println);

			return map;
		} catch (InvalidJwtException ex) {
			MyConstant.LOGGER.info("InvalidJwtException :" + ex);
			return null;
		}

	}

	
	private int generateRandmoKid() {
		Random rand = new Random();
		int randomKid = rand.nextInt(3);
		return randomKid;

	}


	private String[] decodeToken(String token) {
		String[] tokenTab= token.split("\\.");
		return tokenTab;


	}
}
