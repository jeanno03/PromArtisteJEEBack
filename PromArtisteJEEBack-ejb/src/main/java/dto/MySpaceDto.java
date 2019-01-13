package dto;

import java.util.Collection;
import java.util.HashSet;

import entities.MyPicture;

public class MySpaceDto {
	
	private Long id;
    private String name;
    private MyUserDto myUserDto;
    private Collection <MyVideoDto> myVideosDto;   
    private Collection <MyPictureDto> myPicturesDto;
    private Collection <MySoundDto> mySoundsDto;
    
	public MySpaceDto() {
		super();
		myVideosDto = new HashSet();
		myPicturesDto = new HashSet();
		mySoundsDto = new HashSet();
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
	
	public Collection<MySoundDto> getMySoundsDto() {
		return mySoundsDto;
	}

	public void setMySoundsDto(Collection<MySoundDto> mySoundsDto) {
		this.mySoundsDto = mySoundsDto;
	}

	@Override
	public String toString() {
		return "MySpaceDto [id=" + id + ", name=" + name + "]";
	}
    	    
}

