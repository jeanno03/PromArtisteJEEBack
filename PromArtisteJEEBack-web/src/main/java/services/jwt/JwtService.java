package services.jwt;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import org.jose4j.jwt.JwtClaims;
import org.jose4j.lang.JoseException;

import dto.MyRoleDto;
import dto.MyUserDto;

import org.jose4j.jwk.JsonWebKey;
import org.jose4j.jwk.RsaJsonWebKey;
import org.jose4j.jwk.RsaJwkGenerator;
import org.jose4j.jws.AlgorithmIdentifiers;
import  org.jose4j.jws.JsonWebSignature;

import entities.MyRole;
import entities.MyUser;
import myconstants.MyConstant;
import services.ArtistServiceLocal;
import services.EjbService;
import services.EjbServiceInterface;
import services.FileServiceLocal;
import services.FrontService;
import services.FrontServiceInterface;
import services.SecurityServiceLocal;

//https://avaldes.com/jax-rs-security-using-json-web-tokens-jwt-for-authentication-and-authorization/
public class JwtService implements JwtServiceInterface{
	
	private static EjbServiceInterface ejbService = new EjbService();
	private static FrontServiceInterface frontService = new FrontService ();
	private static ArtistServiceLocal artistServiceLocal = ejbService.lookupArtistServiceLocal() ;
	private static FileServiceLocal fileServiceLocal = ejbService.lookupFileServiceLocal() ;
	private static SecurityServiceLocal securityServiceLocal = ejbService.lookupSecurityServiceLocal();

	public static List<JsonWebKey> jsonWebKeys = null;

	//Le serveur va vérifier si les clefs existent ici!
	static {
		MyConstant.LOGGER.info("Inside static initializer...");
		jsonWebKeys = new LinkedList<>();
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
			jsonWebKeys.add(jsonWebKey);
		}
	}
	
	@Override
	public String getJwt(String myUserEmail) {
		MyConstant.LOGGER.info("test : method getJwt start ");
		
		try {
			
			//if connection sucessfull retrieve the user
			MyUserDto myUserDto = artistServiceLocal.getMyUserDtoByEmail(myUserEmail);

			List<String> rolesString = new ArrayList();	
//			rolesString.add("adm");
//			rolesString.add("user");
			for(MyRoleDto m : myUserDto.getMyRolesDto()) {
				rolesString.add(m.getName());			
				MyConstant.LOGGER.info("id add : " + m.getId());
				MyConstant.LOGGER.info("MyRole add : " + m.getName());
			}
			RsaJsonWebKey rsaJsonWebKey = (RsaJsonWebKey) jsonWebKeys.get(0);
			rsaJsonWebKey.setKeyId("1");
			MyConstant.LOGGER.info("rsaJsonWebKey.getKeyId().toString() " + rsaJsonWebKey.getKeyId().toString());
			MyConstant.LOGGER.info("JWK (1) ===> " + rsaJsonWebKey.toJson());

			JwtClaims jwtClaims = new JwtClaims();
			// Create the Claims, which will be the content of the JWT
			//émetteur
			jwtClaims.setIssuer("test.jeannory.ovh");
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

}
