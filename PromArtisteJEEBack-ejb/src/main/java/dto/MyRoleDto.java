package dto;

public class MyRoleDto {
	
	private Long id;
	private String name;
	
	private MyUserDto myUserDto;
	
	public MyRoleDto() {
		super();
	}
	
	public MyRoleDto(Long id, String name) {
		super();
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

	@Override
	public String toString() {
		return "MyRoleDto [id=" + id + ", name=" + name + "]";
	}
	
	

}
