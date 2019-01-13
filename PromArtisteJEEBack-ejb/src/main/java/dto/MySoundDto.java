package dto;

public class MySoundDto {
	
	private Long id;
	private String name;
	
	private MySpaceDto mySpaceDto;

	public MySoundDto() {
		super();
	}

	public MySoundDto(Long id, String name) {
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

	public MySpaceDto getMySpaceDto() {
		return mySpaceDto;
	}

	public void setMySpaceDto(MySpaceDto mySpaceDto) {
		this.mySpaceDto = mySpaceDto;
	}

	@Override
	public String toString() {
		return "MySoundDto [id=" + id + ", name=" + name + ", mySpaceDto=" + mySpaceDto + "]";
	}
	
	

}
