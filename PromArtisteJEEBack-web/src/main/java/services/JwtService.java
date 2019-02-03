package services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Path;

import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.consumer.InvalidJwtException;
import org.jose4j.jwt.consumer.JwtConsumer;
import org.jose4j.jwt.consumer.JwtConsumerBuilder;
import org.jose4j.lang.JoseException;

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
import tools.AuthenticationFilter;

//https://avaldes.com/jax-rs-security-using-json-web-tokens-jwt-for-authentication-and-authorization/

//**************a essayer ********************
//https://codinginfinite.com/authentication-java-apis-json-web-token-jwt/


public class JwtService implements JwtServiceInterface{

	private static EjbServiceInterface ejbService = new EjbService();
	private static FrontServiceInterface frontService = new FrontService ();
	private static ArtistServiceLocal artistServiceLocal = ejbService.lookupArtistServiceLocal() ;
	private static FileServiceLocal fileServiceLocal = ejbService.lookupFileServiceLocal() ;
	private static SecurityServiceLocal securityServiceLocal = ejbService.lookupSecurityServiceLocal();

	//Le serveur va vérifier si les clefs existent ici!
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
			RsaJsonWebKey rsaJsonWebKey = (RsaJsonWebKey) JwtServiceInterface.jsonWebKeys.get(0);
			rsaJsonWebKey.setKeyId("1");
			MyConstant.LOGGER.info("rsaJsonWebKey.getKeyId().toString() " + rsaJsonWebKey.getKeyId().toString());
			MyConstant.LOGGER.info("JWK (1) ===> " + rsaJsonWebKey.toJson());

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
	public Map<String,Object> testJwt(String token) {
		JsonWebKeySet jsonWebKeySet = new JsonWebKeySet(JwtServiceInterface.jsonWebKeys); 
		JsonWebKey jsonWebKey = jsonWebKeySet.findJsonWebKey("1", null,  null,  null);
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
			map.put( AuthenticationFilter.AUTHORIZATION_PROPERTY, token );
			return map;
		} catch (InvalidJwtException ex) {
			MyConstant.LOGGER.info("InvalidJwtException :" + ex);
			return null;
		}

	}
}
