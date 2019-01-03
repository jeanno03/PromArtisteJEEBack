package mocks;

import java.util.Arrays;
import java.util.List;

import dto.MyUserDto;
import entities.MyUser;

public class EntitiesMock {
	
	public List<MyUserDto> getMyUsersDtoMock(){
		List<MyUserDto> myUsersDtoMock = Arrays.asList(
				new MyUserDto(1L,"email1","artistName1","firstName1","lastName1"),
				new MyUserDto(2L,"email2","artistName2","firstName2","lastName2"),
				new MyUserDto(3L,"email3","artistName3","firstName3","lastName3")
				);
		return myUsersDtoMock;
	}
	
	public MyUserDto getMyUserDtoMock() {
		return new MyUserDto(4L,"email4","artistName4","firstName4","lastName4");
	}

}
