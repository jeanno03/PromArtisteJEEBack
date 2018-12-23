package entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import dto.MySpaceDto;
import dto.MyUserDto;
import dto.MyVideoDto;

@Entity
@NamedQueries({
	@NamedQuery(name="entities.MySpace.selectAll",
			query = "select m from MySpace m"),
})
@JsonInclude
//@XmlRootElement(name = "myspace")
public class MySpace implements Serializable{
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    
    @ManyToOne
    @JoinColumn(name="myuser_id")
    private MyUser myUser;
    
    @OneToMany(mappedBy="mySpace", cascade= {CascadeType.ALL})
    private Collection<MyVideo> myVideos;
    
	public MySpace() {
		super();
		myVideos = new ArrayList();
	}

	public MySpace(Long id, String name) {
		this();
		this.id = id;
		this.name = name;
	}

	public MySpace(String name) {
		this();
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public MyUser getMyUser() {
		return myUser;
	}

	public void setMyUser(MyUser myUser) {
		this.myUser = myUser;
	}

	public Collection<MyVideo> getMyVideos() {
		return myVideos;
	}

	public void setMyVideos(Collection<MyVideo> myVideos) {
		this.myVideos = myVideos;
	}

	@Override
	public String toString() {
		return "MySpace [id=" + id + ", name=" + name + "]";
	}    
	
	public MySpaceDto getMySpaceDto() {
		
		MySpaceDto mySpaceDto = new MySpaceDto(this.id,this.name);
		
		MyUserDto myUserDto = new MyUserDto(this.myUser.getId(),this.myUser.getEmail(), this.myUser.getArtistName(), this.myUser.getFirstName(), this.myUser.getLastName());
		
		mySpaceDto.setMyUserDto(myUserDto);
		
		List<MyVideoDto> myVideosDto = new ArrayList();
		for(MyVideo m : myVideos) {
			MyVideoDto myVideoDto = new MyVideoDto (m.getId(), m.getName());
			myVideosDto.add(myVideoDto);
		}
		
		mySpaceDto.setMyVideosDto(myVideosDto);
		return mySpaceDto;
	}

}
