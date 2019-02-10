package services;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;

import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import org.junit.Test;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class FrontServiceTest {


	static FrontServiceInterface frontService = new FrontService ();



	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		System.out.println("FrontServiceTest test start ");
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		System.out.println("FrontServiceTest test end ");
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testTestExtension() {
		String str1 = "test.pdf";
		Boolean bo1 = frontService.testExtension(str1);
		assertTrue("return true",bo1 == true );

		String str2 = "tes.pd";
		Boolean bo2 = frontService.testExtension(str2);
		assertTrue("return false",bo2 == false );

		String str3 = null;
		Boolean bo3 = frontService.testExtension(str3);
		assertTrue("return false",bo3 == false );

		String str4 = "";
		Boolean bo4 = frontService.testExtension(str4);
		assertTrue("return false",bo4 == false );

		String str5 = "test";
		Boolean bo5 = frontService.testExtension(str5);
		assertTrue("return false",bo5 == false );
	}

	@Test
	public void testGetFileName() throws IOException {
		//fail to test
		//	    MultipartFormDataInput newForm = mock(MultipartFormDataInput.class);
		//	    InputPart token = mock(InputPart.class);
		//
		//	    Map<String, List<InputPart>> paramsMap = new HashMap<>();
		//	    paramsMap.put("Token", Arrays.asList(token));        
		//
		//	    when(newForm.getFormDataMap()).thenReturn(paramsMap);
		//	    when(token.getBodyAsString()).thenReturn("expected token param body");
		//	    
		//	    MultivaluedMap<String, String> header = token.getHeaders();
		//
		//	    frontService.getFileName(header);
		//	    String fileName="";
		//	    fileName = frontService.getFileName(header);
		//	    
		//	    assertTrue("appel méthod classic", fileName!=null);

		fail("Not yet implemented");
	}

	@Test
	public void testWriteFile() throws IOException {
		byte[] content = new byte[9];
		for(int i=0;i<content.length;i++) {
			System.out.println(i+")content : "+content[i]);
		}

		String filename = "test.pdf";
		frontService.writeFile(content, filename);
		assertTrue("appel méthod classic", filename!=null);
	}


}