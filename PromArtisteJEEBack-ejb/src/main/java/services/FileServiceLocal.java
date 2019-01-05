package services;

import java.util.HashSet;

import javax.ejb.Local;

import dto.MyPictureDto;
import dto.MySpaceDto;

@Local
public interface FileServiceLocal {
	
	public HashSet<MySpaceDto>  getAllMySpacesDto(Long myUserId);
	public HashSet<MyPictureDto> getAllMyPicturesDtoByMySpaceId(Long mySpaceId);
}
