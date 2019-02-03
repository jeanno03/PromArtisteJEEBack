package services;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.jose4j.jwk.JsonWebKey;

import entities.MyUser;

public interface JwtServiceInterface {
	
	public static List<JsonWebKey> jsonWebKeys = new LinkedList<>();
	public String getJwt(String myUserEmail) ;
	public Map<String, Object> testJwt(String token);

}
