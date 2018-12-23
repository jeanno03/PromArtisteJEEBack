package entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import dto.MySpaceDto;
import dto.MyUserDto;
import dto.MyVideoDto;

@Entity
@NamedQueries({
	@NamedQuery(name="entities.MyUser.selectAll",
			query = "select m from MyUser m"),
})
@JsonInclude
//@XmlRootElement(name = "myuser")
public class MyUser implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	private String email;
	private String artistName;
	private String firstName;
	private String lastName;
	
//	@OneToMany(fetch = FetchType.EAGER, mappedBy="myUser")
	@OneToMany(mappedBy="myUser", cascade={CascadeType.ALL})
	private Collection<MySpace> mySpaces;
	
	public MyUser() {
		super();
		mySpaces = new ArrayList();
	}
	
	public MyUser(String email, String artistName, String firstName, String lastName) {
		super();
		this.email = email;
		this.artistName = artistName;
		this.firstName = firstName;
		this.lastName = lastName;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getArtistName() {
		return artistName;
	}
	public void setArtistName(String artistName) {
		this.artistName = artistName;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}		
	
	public Collection<MySpace> getMySpaces() {
		return mySpaces;
	}

	public void setMySpaces(Collection<MySpace> mySpaces) {
		this.mySpaces = mySpaces;
	}

	@Override
	public String toString() {
		return "MyUser [id=" + id + ", email=" + email + ", artistName=" + artistName + ", firstName=" + firstName
				+ ", lastName=" + lastName + "]";
	}
	
	public MyUserDto getMyUserDto() {
		MyUserDto myUserDto = new MyUserDto(this.id,this.email,this.artistName,this.firstName,this.lastName);
		List<MySpaceDto> mySpacesDto = new ArrayList();
		
		for(MySpace m : this.mySpaces) {
			
			MySpaceDto mySpaceDto = new MySpaceDto (m.getId(), m.getName());
			List<MyVideoDto> myVideosDto = new ArrayList();
			
			for(MyVideo m2 : m.getMyVideos()) {
				
				MyVideoDto myVideoDto = new MyVideoDto (m2.getId(), m2.getName());
				myVideosDto.add(myVideoDto);
			}
			
			mySpaceDto.setMyVideosDto(myVideosDto);
			
			mySpacesDto.add(mySpaceDto);
			
			myUserDto.setMySpacesDto(mySpacesDto);

		}
		return myUserDto;		
	}
		
}
