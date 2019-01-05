package entities;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import java.util.function.Function;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import dto.MyPictureDto;
import dto.MyUserDto;
import mocks.EntitiesMock;

class EntitiesTest {
	MyUser myUser = new MyUser();
	MySpace mySpace = new MySpace();
	MyPicture myPicture= new MyPicture();
	EntitiesMock entitiesMock = new EntitiesMock();

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		System.out.println("test start");
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
		System.out.println("test end");
	}

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testGetMyPictureDto() {
		Throwable exception = assertThrows(NullPointerException.class,()->myPicture.getMyPictureDto());
	}

	@Test
	void testGetMySpaceDto() {
		Throwable exception = assertThrows(NullPointerException.class,()->mySpace.getMySpaceDto());
	}

	@Test
	void testGetMyUserDto() {
		MyUserDto myUserDto = entitiesMock.getMyUserDtoMock();
		assertFalse("myUser equals myUserDto",myUser.getMyUserDto() == myUserDto );
		assertFalse("getMyUserDto est null",myUser.getMyUserDto() == null );
		assertTrue("getMyUserDto n'est pas null",myUser.getMyUserDto() != null );
		Throwable exception = assertThrows(NullPointerException.class,()->myUser.getMyUserDto());
		

	}


}
