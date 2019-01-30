package services;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.naming.NoInitialContextException;

/**
 * @author jeanno
 *Cette class contient toutes les méthodes permettant d'accéder aux ejb
 */
public class EjbService implements EjbServiceInterface{

	@Override
	public ArtistServiceLocal lookupArtistServiceLocal(){
		try {
			Context c = new InitialContext();
			return (ArtistServiceLocal) c.lookup(
					"java:global/PromArtisteJEEBack-ear/PromArtisteJEEBack-ejb/ArtistService!services.ArtistServiceLocal");
		}catch(NamingException ne) {
			Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
			throw new RuntimeException(ne);
		}

	}

	@Override
	public FileServiceLocal lookupFileServiceLocal(){
		try {
			Context c = new InitialContext();
			return (FileServiceLocal) c.lookup(
					"java:global/PromArtisteJEEBack-ear/PromArtisteJEEBack-ejb/FileService!services.FileServiceLocal");
		}catch(NamingException ne) {
			Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
			throw new RuntimeException(ne);
		}

	}
	
	@Override
	public SecurityServiceLocal lookupSecurityServiceLocal(){
		try {
			Context c = new InitialContext();
			return (SecurityServiceLocal) c.lookup(					
					"java:global/PromArtisteJEEBack-ear/PromArtisteJEEBack-ejb/SecurityService!services.SecurityServiceLocal");
		}catch(NamingException ne) {
			Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
			throw new RuntimeException(ne);
		}

	}

//	@Override
//	public LazySingletonLocal lookupLazySingletonLocal(){
//		try {
//			Context c = new InitialContext();
//			return (LazySingletonLocal) c.lookup(
//					"java:global/PromArtisteJEEBack-ear/PromArtisteJEEBack-ejb/FileService!services.LazySingletonLocal");
//		}catch(NamingException ne) {
//			Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
//			throw new RuntimeException(ne);
//		}
//
//	}

}
