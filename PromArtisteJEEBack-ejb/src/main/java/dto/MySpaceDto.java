package dto;

import java.util.ArrayList;
import java.util.Collection;

import entities.MyPicture;

public class MySpaceDto {
	
	private Long id;
    private String name;
    private MyUserDto myUserDto;
    private Collection <MyVideoDto> myVideosDto;   
    private Collection <MyPictureDto> myPicturesDto;
    
	public MySpaceDto() {
		super();
		myVideosDto = new ArrayList();
		myPicturesDto = new ArrayList();
	}

	public MySpaceDto(Long id, String name) {
		this();
		this.id = id;
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
	
	public MyUserDto getMyUserDto() {
		return myUserDto;
	}

	public void setMyUserDto(MyUserDto myUserDto) {
		this.myUserDto = myUserDto;
	}

	public Collection<MyVideoDto> getMyVideosDto() {
		return myVideosDto;
	}

	public void setMyVideosDto(Collection<MyVideoDto> myVideosDto) {
		this.myVideosDto = myVideosDto;
	}

	public Collection<MyPictureDto> getMyPicturesDto() {
		return myPicturesDto;
	}

	public void setMyPicturesDto(Collection<MyPictureDto> myPicturesDto) {
		this.myPicturesDto = myPicturesDto;
	}

	@Override
	public String toString() {
		return "MySpaceDto [id=" + id + ", name=" + name + "]";
	}
    	    
}

