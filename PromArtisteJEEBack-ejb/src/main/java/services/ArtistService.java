package services;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import dto.MyPictureDto;
import dto.MySpaceDto;
import dto.MyUserDto;
import dto.MyVideoDto;
import entities.MyPicture;

//import org.springframework.transaction.annotation.Propagation;
//import org.springframework.transaction.annotation.Transactional;

import entities.MySpace;
import entities.MyUser;
import entities.MyVideo;

/**
 * Session Bean implementation class ArtistService
 */
@Stateless
@LocalBean
public class ArtistService implements ArtistServiceLocal {

	@PersistenceContext(unitName = "primaryPromArtist")
	private EntityManager em;
	PathServiceInterface pathService = new PathService();

	
	/**
	 * Default constructor. 
	 */
	public ArtistService() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getMyUserDataTest() {
		// TODO Auto-generated method stub
		try {
			MyUser myUser01 = new MyUser ("jean.jean@gmail.com", "Elvis King", "jean", "jean");
			MyUser myUser02 = new MyUser ("george.jean@gmail.com", "Yvan King", "george", "Yvan");

			MySpace my01 = new MySpace("Espace du Mort");
			MySpace my02 = new MySpace("Espace Rap");
			MySpace my03 = new MySpace("Espace du King");
			MySpace my04 = new MySpace("Espace du R&B");

			MyVideo mv01 = new MyVideo("Albator");
			MyVideo mv02 = new MyVideo("Remy sans famille");
			MyVideo mv03 = new MyVideo("Escroc sans reproche");
			MyVideo mv04 = new MyVideo("Angry Birds");
			MyVideo mv05 = new MyVideo("Cars 1");
			MyVideo mv06 = new MyVideo("Clip R&B");
			MyVideo mv07 = new MyVideo("Kamayakka");
			MyVideo mv08 = new MyVideo("Wakaka");

			Date day = new Date();
			MyPicture myPicture01 = new MyPicture(day, pathService.getServerLocation()+"001.pdf", "fichier 01.pdf");
			MyPicture myPicture02 = new MyPicture(day, pathService.getServerLocation()+"002.pdf", "fichier 02.pdf");

			my01.setMyUser(myUser01);
			my02.setMyUser(myUser01);

			my03.setMyUser(myUser02);
			my04.setMyUser(myUser02);

			mv01.setMySpace(my01);
			mv02.setMySpace(my01);
			mv03.setMySpace(my02);
			mv04.setMySpace(my02);
			mv05.setMySpace(my03);
			mv06.setMySpace(my03);
			mv07.setMySpace(my04);
			mv08.setMySpace(my04);

			myPicture01.setMySpace(my01);
			myPicture02.setMySpace(my02);

			em.persist(myUser01);
			em.persist(myUser02);

			em.persist(my01);
			em.persist(my02);
			em.persist(my03);
			em.persist(my04);

			em.persist(mv01);
			em.persist(mv02);
			em.persist(mv03);
			em.persist(mv04);
			em.persist(mv05);
			em.persist(mv06);
			em.persist(mv07);
			em.persist(mv08);

			em.persist(myPicture01);
			em.persist(myPicture02);


		}catch(Exception ex) {
			ex.printStackTrace();
		}

		return "it works";
	}

	@Override
	public List<MySpace> getAllMySpace() {
		// TODO Auto-generated method stub

		TypedQuery<MySpace> qr = em.createNamedQuery("entities.MySpace.selectAll", MySpace.class);
		try {
			List<MySpace> mySpaces=qr.getResultList();
			return mySpaces;
		}catch(NullPointerException ex) {
			ex.printStackTrace();
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
		}catch(NullPointerException ex) {
			ex.printStackTrace();
		}
		return null;
	}

	@Override
	public List<MyUserDto> getAllMyUserDto() {
		// TODO Auto-generated method stub

		TypedQuery<MyUser> qr = em.createNamedQuery("entities.MyUser.selectAll", MyUser.class);		
		try {
			List<MyUser>  myUsers=qr.getResultList();
			List<MyUserDto> myUsersDto = new ArrayList();
			for(MyUser m : myUsers) {
				myUsersDto.add(m.getMyUserDto());
			}
			return myUsersDto;

		}catch(NullPointerException ex) {
			ex.printStackTrace();
		}
		return null;
	}

	private MyUser getMyUserById(Long id) {
		return em.getReference(MyUser.class, id);
	}

	private MySpace getMySpaceById(Long id) {
		return em.getReference(MySpace.class, id);
	}	


	@Override
	public List<MyVideoDto> getAllMyVideoDto() {
		// TODO Auto-generated method stub

		TypedQuery<MyVideo> qr = em.createNamedQuery("entities.MyVideo.selectAll", MyVideo.class);
		try {

			List<MyVideoDto> myVideosDto = new ArrayList();

			List<MyVideo> myVideos = qr.getResultList();

			for(MyVideo m : myVideos) {
				MyVideoDto myVideoDto = m.getMyVideoDto();
				myVideosDto.add(myVideoDto);
			}
			return myVideosDto;

		}catch(Exception ex) {
			System.out.println("exception : " + ex);
		}


		return null;
	}

	@Override
	public MyUserDto getMyUserDto(Long id) {
		MyUser myUser = getMyUserById(id);
		MyUserDto myUserDto = myUser.getMyUserDto();

		return myUserDto;
	}

	@Override
	public void saveMyUser(MyUser myUser){
		try {
			em.persist(myUser);
		}catch(NullPointerException ex) {
			ex.printStackTrace();
		}
	}

	public MyUserDto getMyUserDtoByEmail(String email) {
		TypedQuery<MyUser> qr = em.createNamedQuery("entities.MyUser.getByEmail",MyUser.class);
		qr.setParameter("paramEmail", email);
		try {
			MyUser myUser = (MyUser) qr.getSingleResult();
			MyUserDto myUserDto = myUser.getMyUserDto();
			return myUserDto;

		} catch (NullPointerException ex) {
			ex.printStackTrace();
		}
		return null;
	}

	public void upLoadPicture(Long myUserId, Long mySpaceId) throws InterruptedException {
		MyUser myUser = getMyUserById(myUserId);
		MySpace mySpace = getMySpaceById(mySpaceId);

		System.out.println("mySpace.getName() : " + mySpace.getName());

		Date day = new Date(); 

		System.out.println("day : " + day);
		//		MyPicture my = new MyPicture(day);
		//		my.setMySpace(mySpace);
		//		em.persist(my);
		Thread.sleep(1000);
		//		MyPicture myPictureReturn = GetLastMyPictureOfDay();
		//		MyPictureDto myPictureDto = myPictureReturn.getMyPictureDto();

		//		System.out.println("myPictureDto.getId() : " + myPictureDto.getId());
		//		return myPictureDto;
	}

	public MyPicture getLastMyPictureOfDay() {
		Date day = new Date();
		TypedQuery<MyPicture> qr = em.createNamedQuery("entities.MyPicture.getLastMyPictureOfDay",MyPicture.class);
		qr.setParameter("paramRegisteredDate", day).setMaxResults(1);
		try {
			MyPicture myPicture = (MyPicture) qr.getSingleResult();
			return myPicture;		
		} catch (NullPointerException ex) {
			ex.printStackTrace();
		}
		return null;
	}

	public MyPicture getLastPicture() {
		TypedQuery<MyPicture> qr = em.createNamedQuery("entities.MyPicture.getLastMyPicture",MyPicture.class);
		qr.setMaxResults(1);
		try {
			MyPicture myPicture = (MyPicture) qr.getSingleResult();
			return myPicture;
		} catch (NullPointerException ex) {
			ex.printStackTrace();
		}
		return null;
	}

	public String createMyPicturePath(String originName) {
		//				path = pathService.getServerLocation() + newId + extension;
		MyPicture myPicture = getLastPicture();

		String newId = String.valueOf(myPicture.getId()+1);

		String [] fileTab = originName.split("");
		int t= fileTab.length;

		String extension = fileTab[t-4]+fileTab[t-3]+fileTab[t-2]+fileTab[t-1];

		String path = pathService.getServerLocation()+newId +extension ;
		return path;
	}

	public MyPictureDto saveMyPicture(MyPicture myPicture) {
		em.persist(myPicture);
		MyPictureDto myPictureDto = myPicture.getMyPictureDto();
		return myPictureDto;
	}

}
