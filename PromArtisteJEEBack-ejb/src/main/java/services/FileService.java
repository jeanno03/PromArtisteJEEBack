package services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.function.*;

import dto.MyPictureDto;
import dto.MySpaceDto;
import entities.MyPicture;
import entities.MySpace;



/**
 * Session Bean implementation class FileService
 */
@Stateless
@LocalBean
public class FileService implements FileServiceLocal {

	@PersistenceContext(unitName="primaryPromArtist")
	private EntityManager em;

	/**
	 * Default constructor. 
	 */
	public FileService() {
		// TODO Auto-generated constructor stub
	}

	//Méthod plus lourd voir en dessous méthode plus adapté
	//gardé en exemple pour lambda + function
	@Override
	public HashSet<MySpaceDto> getAllMySpacesDto(Long myUserId) {

		TypedQuery<MySpace> qr = em.createNamedQuery("entities.MySpace.getMySpaceByMyUserId", MySpace.class);
		qr.setParameter("paramUserId", myUserId);
		try {
			List<MySpace> mySpaces = qr.getResultList();

			Function <MySpace, MySpaceDto> func = (MySpace m)-> m.getMySpaceDto();
			HashSet<MySpaceDto> mySpacesDto = transformMySpacesToMySpacesDto(mySpaces, func);
			return mySpacesDto;

		}catch(NullPointerException ex) {
			ex.printStackTrace();
			return null;
		}
	}
	
	//Méthod plus adapté car on récupère les HashSet via navigabilité et sans query
	@Override
	public HashSet<MyPictureDto> getAllMyPicturesDtoByMySpaceId(Long mySpaceId){
		MySpace mySpace = em.find(MySpace.class, mySpaceId);
		MySpaceDto mySpaceDto = mySpace.getMySpaceDto();
		HashSet<MyPictureDto> mySpacesDto = (HashSet<MyPictureDto>) mySpaceDto.getMyPicturesDto();
		return mySpacesDto;
	}

	private HashSet<MySpaceDto> transformMySpacesToMySpacesDto(List<MySpace> mySpaces, Function <MySpace, MySpaceDto> func){
		HashSet<MySpaceDto> mySpacesDto = new HashSet<>();
		for(MySpace m : mySpaces) {
			mySpacesDto.add(func.apply(m));
		}
		return mySpacesDto;
	}

}
