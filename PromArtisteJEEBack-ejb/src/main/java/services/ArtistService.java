package services;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.bouncycastle.jcajce.provider.digest.SHA3;
import org.bouncycastle.util.encoders.Hex;
import org.glassfish.jersey.server.model.internal.ModelProcessorUtil.Method;

import dto.MyPictureDto;
import dto.MySpaceDto;
import dto.MyUserDto;
import dto.MyVideoDto;
import entities.MyPicture;
import entities.MyRole;

//import org.springframework.transaction.annotation.Propagation;
//import org.springframework.transaction.annotation.Transactional;

import entities.MySpace;
import entities.MyUser;
import entities.MyVideo;
import myconstants.MyConstant;

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
		try {
			MyUser myUser01 = new MyUser ("jean.jean@gmail.com", "Elvis King", "jean", "jean");
			MyUser myUser02 = new MyUser ("george.jean@gmail.com", "Yvan King", "george", "Yvan");
			
			MyUser myUser03 = new MyUser ("phou.jeannory@gmail.com", "jeannory", "jeannory", "jeannory");
			
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
			MyPicture myPicture01 = new MyPicture(day, MyConstant.PROP.getProperty("server-location")+"001.pdf", "fichier 01.pdf");
			MyPicture myPicture02 = new MyPicture(day, MyConstant.PROP.getProperty("server-location")+"002.pdf", "fichier 02.pdf");
			MyPicture myPicture03 = new MyPicture(day, MyConstant.PROP.getProperty("server-location")+"003.pdf", "fichier 02.pdf");
			MyPicture myPicture04 = new MyPicture(day, MyConstant.PROP.getProperty("server-location")+"004.pdf", "fichier 02.pdf");
			
			MyRole myRole1 = new MyRole("admin");
			MyRole myRole2 = new MyRole("user");
			MyRole myRole3 = new MyRole("visitor");
						
			my01.setMyUser(myUser01);
			my02.setMyUser(myUser01);

			my03.setMyUser(myUser02);
			my04.setMyUser(myUser02);
			
			my03.setMyUser(myUser03);
			my04.setMyUser(myUser03);

			mv01.setMySpace(my01);
			mv02.setMySpace(my01);
			mv03.setMySpace(my02);
			mv04.setMySpace(my02);
			mv05.setMySpace(my03);
			mv06.setMySpace(my03);
			mv07.setMySpace(my04);
			mv08.setMySpace(my04);

			myPicture01.setMySpace(my01);
			myPicture02.setMySpace(my01);
			myPicture03.setMySpace(my02);
			myPicture04.setMySpace(my02);
			
			String mdpSha31 = getStringSha3("1234");
			String mdpSha32 = getStringSha3("12345678");
			myUser01.setMdp(mdpSha31);
			myUser02.setMdp(mdpSha31);
			myUser03.setMdp(mdpSha32);
			
			myRole1.setMyUser(myUser01);
			myRole2.setMyUser(myUser02);
			myRole3.setMyUser(myUser03);
			
			em.persist(myUser01);
			em.persist(myUser02);
			em.persist(myUser03);
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
			
			em.persist(myRole1);
			em.persist(myRole2);
			em.persist(myRole3);


		}catch(Exception ex) {
			MyConstant.LOGGER.info("Exception : " + ex.getMessage());
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
			MyConstant.LOGGER.info("NullPointerException : " + ex.getMessage());
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
			MyConstant.LOGGER.info("NullPointerException : " + ex.getMessage());
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
			MyConstant.LOGGER.info("NullPointerException : " + ex.getMessage());
		}
		return null;
	}

	@Override
	public MyUser getMyUserById(Long id) {
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
			MyConstant.LOGGER.info("Exception : " + ex.getMessage());
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
			MyConstant.LOGGER.info("NullPointerException : " + ex.getMessage());
		}
	}
	
	@Override
	public MyUserDto getMyUserDtoByEmail(String email) {
		TypedQuery<MyUser> qr = em.createNamedQuery("entities.MyUser.getByEmail",MyUser.class);
		qr.setParameter("paramEmail", email);
		try {
			MyUser myUser = (MyUser) qr.getSingleResult();
			MyUserDto myUserDto = myUser.getMyUserDto();
			return myUserDto;

		} catch (NullPointerException ex) {
			MyConstant.LOGGER.info("NullPointerException : " + ex.getMessage());
		}
		return null;
	}

	@Override
	public void upLoadPicture(Long myUserId, Long mySpaceId) throws InterruptedException {
		MyUser myUser = getMyUserById(myUserId);
		MySpace mySpace = getMySpaceById(mySpaceId);
		System.out.println("mySpace.getName() : " + mySpace.getName());
		Thread.sleep(1000);
		System.out.println("Done !");
	}

	@Override
	public MyPicture getLastMyPictureOfDay() {
		Date day = new Date();
		TypedQuery<MyPicture> qr = em.createNamedQuery("entities.MyPicture.getLastMyPictureOfDay",MyPicture.class);
		qr.setParameter("paramRegisteredDate", day).setMaxResults(1);
		try {
			MyPicture myPicture = (MyPicture) qr.getSingleResult();
			return myPicture;		
		} catch (NullPointerException ex) {
			MyConstant.LOGGER.info("NullPointerException : " + ex.getMessage());
		}
		return null;
	}

	@Override
	public MyPicture getLastPicture() {
		TypedQuery<MyPicture> qr = em.createNamedQuery("entities.MyPicture.getLastMyPicture",MyPicture.class);
		qr.setMaxResults(1);
		try {
			MyPicture myPicture = (MyPicture) qr.getSingleResult();
			return myPicture;
		} catch (NullPointerException ex) {
			MyConstant.LOGGER.info("NullPointerException : " + ex.getMessage());
		}
		return null;
	}

	//a refaire a partir du point
	@Override
	public String createMyPicturePath(String originName) {
		//				path = pathService.getServerLocation() + newId + extension;
		MyPicture myPicture = getLastPicture();

		String newId = String.valueOf(myPicture.getId()+1);

		String [] fileTab = originName.split("");
		int t= fileTab.length;

		String extension = fileTab[t-4]+fileTab[t-3]+fileTab[t-2]+fileTab[t-1];

		String path = MyConstant.PROP.getProperty("server-location")+newId +extension ;
		return path;
	}

	public MyPictureDto saveMyPicture(MyPicture myPicture, Long mySpaceId) {
		MySpace mySpace= em.find(MySpace.class, mySpaceId);
		myPicture.setMySpace(mySpace);
		em.persist(myPicture);
		
		MyPictureDto myPictureDto = myPicture.getMyPictureDto();
		return myPictureDto;
	}
		
	public String getStringSha3(String mdp) throws Exception { 

	    SHA3.DigestSHA3 digestSHA3 = new SHA3.Digest512(); 
	    byte[] digest = digestSHA3.digest(mdp.getBytes()); 
	    return Hex.toHexString(digest);

	} 
	
	public MyUserDto getConnect(String email, String mdp) throws Exception {
		String mdpSha3 = getStringSha3(mdp);
		TypedQuery<MyUser> qr = em.createNamedQuery("entities.MyUser.getByEmail",MyUser.class);
		qr.setParameter("paramEmail", email);
		try {
			MyUser myUser = (MyUser) qr.getSingleResult();
			System.out.println("mdpSha3 : " + mdpSha3);
			System.out.println("myUser.getMdp() : " + myUser.getMdp());
		if(mdpSha3.equals(myUser.getMdp())) {
			MyUserDto myUserDto = myUser.getMyUserDto();
			return myUserDto;
		}
		else {
			System.out.println("le mdp ne correspond pas");
			return null;
		}
		}catch(NullPointerException ex) {
			MyConstant.LOGGER.info("NullPointerException : " + ex.getMessage());
		}
		return null;
	}
	@Override
	public Boolean getConnectBoolean(String email, String mdp) throws Exception {
		String mdpSha3 = getStringSha3(mdp);
		TypedQuery<MyUser> qr = em.createNamedQuery("entities.MyUser.getByEmail",MyUser.class);
		qr.setParameter("paramEmail", email);
		try {
			MyUser myUser = (MyUser) qr.getSingleResult();
		if(mdpSha3.equals(myUser.getMdp())) {
			return true;
		}
		else {
			System.out.println("le mdp ne correspond pas");
			return false;
		}
		}catch(NullPointerException ex) {
			MyConstant.LOGGER.info("NullPointerException : " + ex.getMessage());
		}
		return false;
	}
	
	
	

}
