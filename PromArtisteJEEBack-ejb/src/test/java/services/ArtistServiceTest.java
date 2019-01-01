package services;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.NonUniqueResultException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import dto.MyUserDto;
import entities.MySpace;
import entities.MyUser;

public class ArtistServiceTest {

	ArtistServiceLocal artistService = new ArtistService ();

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		System.out.println("ArtistServiceTest test start ");
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		System.out.println("ArtistServiceTest test end ");
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testArtistService() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetMyUserDataTest() {
		String str = artistService.getMyUserDataTest();
		assertTrue("result equals it works",str.equals("it works"));
	}

	@Test
	public void testGetAllMySpace() {	
		Throwable exception = assertThrows(NullPointerException.class, () -> artistService.getAllMySpace());
	}

	@Test
	public void testGetAllMyUser() {
		Throwable exception = assertThrows(NullPointerException.class, () -> artistService.getAllMyUser());
	}

	@Test
	public void testGetAllMyUserDto() {
		Throwable exception = assertThrows(NullPointerException.class, () -> artistService.getAllMyUserDto());
	}

	@Test
	public void testGetAllMyVideoDto() {
		Throwable exception = assertThrows(NullPointerException.class,()-> artistService.getAllMyVideoDto());
	}

	@Test
	public void testGetMyUserDto() {
		Throwable exception = assertThrows(NullPointerException.class,()->artistService.getAllMyUserDto());
	}

	@Test
	public void testSaveMyUser() {
		MyUser myUser1 = new MyUser("test", "test", "test", "test");
		artistService.saveMyUser(myUser1);
		fail("Not yet implemented");

	}

	@Test
	public void testGetMyUserDtoByEmail() {
		String email = "test";
		Throwable exception = assertThrows(NullPointerException.class,() -> artistService.getMyUserDtoByEmail(email));
	}

	@Test
	public void testUpLoadPicture() {
		fail("Not yet implemented");
	}

}
