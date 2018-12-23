package dto;

import entities.MySpace;

public class MyVideoDto{
	
	private Long id;
	private String name;
	private MySpaceDto mySpaceDto;
	
	public MyVideoDto() {
		super();
	}
	
	public MyVideoDto(Long id, String name) {
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
	
	public MySpaceDto getMySpaceDto() {
		return mySpaceDto;
	}
	public void setMySpaceDto(MySpaceDto mySpaceDto) {
		this.mySpaceDto = mySpaceDto;
	}
	@Override
	public String toString() {
		return "MyVideoDto [id=" + id + ", name=" + name + "]";
	}
}