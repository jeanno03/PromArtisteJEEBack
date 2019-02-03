package services.jwt;

import entities.MyUser;

public interface JwtServiceInterface {
	
	public String getJwt(String myUserEmail) ;

}
