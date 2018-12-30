package services;

import java.util.List;

import javax.ejb.Local;

import dto.MyUserDto;
import dto.MyVideoDto;
import entities.MySpace;
import entities.MyUser;

@Local
public interface ArtistServiceLocal {
	
	public String getMyUserDataTest();
	public List<MySpace> getAllMySpace();
	public List<MyUser> getAllMyUser();
	public List<MyUserDto> getAllMyUserDto() ;
	public MyUserDto getMyUserDto(Long id);
	public List<MyVideoDto> getAllMyVideoDto();
	public void saveMyUser(MyUser myUser);
	public MyUserDto getMyUserDtoByEmail(String email);

}
