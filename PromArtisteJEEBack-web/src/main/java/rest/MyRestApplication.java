package rest;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import controllers.FileController;
import controllers.JwtController;
import controllers.TestController;
import services.ArtistService;
import services.EjbService;
import services.EjbServiceInterface;

@ApplicationPath("/rest")
public class MyRestApplication extends Application{
	
	//http://www.codingpedia.org/ama/how-to-enable-cors-filter-in-resteasy

	
    @Override
    public Set<Class<?>> getClasses() {
   	
        Set<Class<?>> classes = new HashSet<Class<?>>();
        classes.add(TestController.class);
        classes.add(FileController.class);
        classes.add(JwtController.class);
        return classes;
    }

}
