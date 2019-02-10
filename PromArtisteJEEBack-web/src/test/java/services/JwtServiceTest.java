package services;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class JwtServiceTest {
	
	static JwtServiceInterface jwtService = new JwtService ();

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		System.out.println("JwtService test start ");
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
		System.out.println("JwtService test end ");
	}

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testGetJwt() {
		fail("Not yet implemented");
	}

	@Test
	void testTestJwt() {
		fail("Not yet implemented");
	}

	@Test
	void testGenerateRandmoKid() {
		int kidRandom1 = jwtService.generateRandmoKid();
		System.out.println("kidRandom1 : " + kidRandom1);
		assertTrue("kidRandom1 compris entre 0 et 2", kidRandom1>=0||kidRandom1<=2);
		
		int kidRandom2 = jwtService.generateRandmoKid();
		System.out.println("kidRandom2 : " + kidRandom2);
		assertTrue("kidRandom2 compris entre 0 et 2", kidRandom1>=0||kidRandom1<=2);
		
		int kidRandom3 = jwtService.generateRandmoKid();
		System.out.println("kidRandom3 : " + kidRandom3);
		assertTrue("kidRandom3 compris entre 0 et 2", kidRandom1>=0||kidRandom1<=2);
	}

}
