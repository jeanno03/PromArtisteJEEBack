package services;

import java.util.List;
import javax.ejb.Local;

import dto.MyPictureDto;
import dto.MyUserDto;
import dto.MyVideoDto;
import entities.MyPicture;
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
	public MyUser getMyUserByEmail(String email) ;
	public void upLoadPicture(Long myUserId, Long mySpaceId)throws InterruptedException ;
	public MyPicture getLastMyPictureOfDay();
	public MyPicture getLastPicture();
	public String createMyPicturePath(String originName) ;
	public MyPictureDto saveMyPicture(MyPicture myPicture, Long MySpaceId);
	public String getStringSha3(String mdp) throws Exception;
	public MyUserDto getConnect(String email, String mdp) throws Exception;
	public Boolean getConnectBoolean(String email, String mdp) throws Exception;
	public MyUser getMyUserById(Long id);
}
