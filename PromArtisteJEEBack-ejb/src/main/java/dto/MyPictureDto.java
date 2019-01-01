package dto;

import java.util.Date;

public class MyPictureDto {
	
	private Long id;
	private Date registeredDate;
	private MySpaceDto mySpaceDto;
	
	public MyPictureDto() {
		super();
	}

	public MyPictureDto(Long id, Date registeredDate) {
		this();
		this.id = id;
		this.registeredDate = registeredDate;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getRegisteredDate() {
		return registeredDate;
	}

	public void setRegisteredDate(Date registeredDate) {
		this.registeredDate = registeredDate;
	}

	public MySpaceDto getMySpaceDto() {
		return mySpaceDto;
	}

	public void setMySpaceDto(MySpaceDto mySpaceDto) {
		this.mySpaceDto = mySpaceDto;
	}

	@Override
	public String toString() {
		return "MyPictureDto [id=" + id + ", registeredDate=" + registeredDate + "]";
	}
	
	
}
