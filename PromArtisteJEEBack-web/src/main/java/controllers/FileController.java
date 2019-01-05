package controllers;

import java.util.HashSet;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import dto.MyPictureDto;
import dto.MySpaceDto;
import services.ArtistServiceLocal;
import services.EjbService;
import services.EjbServiceInterface;
import services.FileServiceLocal;
import services.FrontService;
import services.FrontServiceInterface;

@Path("/FileController")
public class FileController {

	private EjbServiceInterface ejbService = new EjbService();
	private FrontServiceInterface frontService = new FrontService ();
	private ArtistServiceLocal artistServiceLocal = ejbService.lookupArtistServiceLocal() ;
	private FileServiceLocal fileServiceLocal = ejbService.lookupFileServiceLocal() ;

	public FileController() {
		super();
	}

	@GET
	@Produces("text/plain")
	//http://localhost:8080/PromArtisteJEEBack-web/rest/FileController
	public String helloWorld() {
		return "Hello World";
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getMySpaceDtoByMyUserId/{id}")
	//http://localhost:8080/PromArtisteJEEBack-web/rest/FileController/getMySpaceDtoByMyUserId/1
	public HashSet<MySpaceDto> getMySpaceDtoByMyUserId(@PathParam("id") Long myUserId) {
		HashSet<MySpaceDto> mySpacesDto = fileServiceLocal.getAllMySpacesDto(myUserId);
		return mySpacesDto;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("getAllMyPicturesDtoByMySpaceId/{id}")
	//http://localhost:8080/PromArtisteJEEBack-web/rest/FileController/getAllMyPicturesDtoByMySpaceId/1
	public HashSet<MyPictureDto> getAllMyPicturesDtoByMySpaceId(@PathParam("id") Long mySpaceId){
		HashSet<MyPictureDto> myPicturesDto = fileServiceLocal.getAllMyPicturesDtoByMySpaceId(mySpaceId);
		return myPicturesDto;
	}

}
