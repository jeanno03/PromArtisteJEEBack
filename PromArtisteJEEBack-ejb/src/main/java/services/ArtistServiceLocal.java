package services;

import java.util.List;

import javax.ejb.Local;

import entities.MySpace;
import entities.MyUser;

@Local
public interface ArtistServiceLocal {
	
	public String getMyUserDataTest();
	public List<MySpace> getAllMySpace();
	public List<MyUser> getAllMyUser();
	public MyUser getMyUser(Long id);
	
	

}
