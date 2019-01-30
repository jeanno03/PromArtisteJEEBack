package services;

import javax.naming.NoInitialContextException;

public interface EjbServiceInterface {
	
	public ArtistServiceLocal lookupArtistServiceLocal() ;
	public FileServiceLocal lookupFileServiceLocal();
//	public LazySingletonLocal lookupLazySingletonLocal();
	public SecurityServiceLocal lookupSecurityServiceLocal();
}
