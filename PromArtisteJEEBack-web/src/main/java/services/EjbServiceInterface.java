package services;

import javax.naming.NoInitialContextException;

public interface EjbServiceInterface {
	
	public ArtistServiceLocal lookupArtistServiceLocal() ;
	public FileServiceLocal lookupFileServiceLocal();
}
