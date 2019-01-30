package services;

import javax.ejb.Local;

import org.jose4j.jwt.consumer.InvalidJwtException;
import org.jose4j.lang.JoseException;

@Local
public interface SecurityServiceLocal {

	public String generateJwtToken(Long id) throws JoseException;

	public String validateJwtToken(String jwt) throws InvalidJwtException;

}
