package rest;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import controllers.ArtistController;
import services.ArtistService;

@ApplicationPath("/rest")
public class MyRestApplication extends Application{
	
    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> classes = new HashSet<Class<?>>();
        classes.add(ArtistController.class);
        return classes;
    }

}
