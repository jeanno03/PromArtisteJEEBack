package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import dto.MySpaceDto;
import dto.MyUserDto;

//import org.springframework.transaction.annotation.Propagation;
//import org.springframework.transaction.annotation.Transactional;

import entities.MySpace;
import entities.MyUser;

/**
 * Session Bean implementation class ArtistService
 */
@Stateless
@LocalBean
public class ArtistService implements ArtistServiceLocal {

	@PersistenceContext(unitName = "primaryPromArtist")
	private EntityManager em;

	/**
	 * Default constructor. 
	 */
	public ArtistService() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getMyUserDataTest() {
		// TODO Auto-generated method stub

		MyUser myUser01 = new MyUser ("jean.jean@gmail.com", "Elvis King", "jean", "jean");
		MyUser myUser02 = new MyUser ("george.jean@gmail.com", "Yvan King", "george", "Yvan");

		MySpace my01 = new MySpace("Espace du Mort");
		MySpace my02 = new MySpace("Espace Rap");
		MySpace my03 = new MySpace("Espace du King");
		MySpace my04 = new MySpace("Espace du R&B");

		my01.setMyUser(myUser01);
		my02.setMyUser(myUser01);

		my03.setMyUser(myUser02);
		my04.setMyUser(myUser02);

		em.persist(myUser01);
		em.persist(myUser02);

		em.persist(my01);
		em.persist(my02);
		em.persist(my03);
		em.persist(my04);


		return "it works";

	}

	@Override
	public List<MySpace> getAllMySpace() {
		// TODO Auto-generated method stub

		TypedQuery<MySpace> qr = em.createNamedQuery("entities.MySpace.selectAll", MySpace.class);
		try {
			List<MySpace> mySpaces=qr.getResultList();
			return mySpaces;
		}catch(Exception ex) {
			System.out.println("exception : " + ex);
		}
		return null;
	}

	@Override
	public List<MyUser> getAllMyUser() {
		// TODO Auto-generated method stub

		TypedQuery<MyUser> qr = em.createNamedQuery("entities.MyUser.selectAll", MyUser.class);		
		try {
			List<MyUser>  myUsers=qr.getResultList();
			return myUsers;
		}catch(Exception ex) {
			System.out.println("exception : " + ex);
		}
		return null;
	}
	
	@Override
	public List<MyUserDto> getAllMyUserDto() {
		// TODO Auto-generated method stub

		TypedQuery<MyUser> qr = em.createNamedQuery("entities.MyUser.selectAll", MyUser.class);		
		try {
			List<MyUser>  myUsers=qr.getResultList();
			List<MyUserDto> myUserDtoReturn = returnListMyUserDto(myUsers);
			return myUserDtoReturn;
			
		}catch(Exception ex) {
			System.out.println("exception : " + ex);
		}
		return null;
	}
	
	private MyUser getUserByEmail(Long id) {
		return em.getReference(MyUser.class, id);
	}
	
	private List<MyUserDto> returnListMyUserDto(List<MyUser> myUserList){
		List<MyUserDto> myUserDtoList = new ArrayList();
		for(MyUser m : myUserList) {
			
			MyUserDto myUserDto = new MyUserDto(m.getId(), m.getEmail(), m.getArtistName(),	m.getFirstName(), m.getLastName());
			
			List<MySpaceDto> mySpaceDtos = new ArrayList();
			Collection<MySpace> mySpaces = mySpaces = m.getMySpaces();
			
			
			for(MySpace m2 : mySpaces) {
				MySpaceDto mySpaceDto = new MySpaceDto(m2.getId(),m2.getName());
				mySpaceDtos.add(mySpaceDto);
			}
			
			myUserDto.setMySpacesDto(mySpaceDtos);
			myUserDtoList.add(myUserDto);
		}
		return myUserDtoList;
	}

	@Override
	public MyUserDto getMyUserDto(Long id) {
		MyUser myUser = getUserByEmail(id);
		MyUserDto myUserDto = new MyUserDto (myUser.getId(), myUser.getArtistName(), myUser.getEmail(), myUser.getFirstName(), myUser.getLastName());
		List<MySpaceDto> mySpaceDtos = new ArrayList();
		
		Collection<MySpace> mySpaces = myUser.getMySpaces();
		
		for(MySpace m : mySpaces) {
			MySpaceDto mySpaceDto = new MySpaceDto(m.getId(),m.getName());
			mySpaceDtos.add(mySpaceDto);
		}
		myUserDto.setMySpacesDto(mySpaceDtos);
		return myUserDto;
	}
}
