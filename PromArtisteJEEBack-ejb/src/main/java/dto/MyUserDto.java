package dto;

import java.util.ArrayList;
import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import entities.MySpace;

public class MyUserDto {
	
    private Long id;
	private String email;
	private String artistName;
	private String firstName;
	private String lastName;
	private Collection<MySpaceDto> mySpacesDto;
	
	public MyUserDto() {
		super();
		mySpacesDto = new ArrayList();
	}
	
	public MyUserDto(Long id, String email, String artistName, String firstName, String lastName) {
		this();
		this.id = id;
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
	
	public Collection<MySpaceDto> getMySpacesDto() {
		return mySpacesDto;
	}

	public void setMySpacesDto(Collection<MySpaceDto> mySpacesDto) {
		this.mySpacesDto = mySpacesDto;
	}

	@Override
	public String toString() {
		return "MyUserDto [id=" + id + ", email=" + email + ", artistName=" + artistName + ", firstName=" + firstName
				+ ", lastName=" + lastName +"]";
	}

	
}
