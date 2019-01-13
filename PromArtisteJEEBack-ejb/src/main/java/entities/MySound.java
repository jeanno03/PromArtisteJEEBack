package entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonInclude;

import dto.MySoundDto;
import dto.MySpaceDto;

@Entity
@JsonInclude
public class MySound implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	
	@ManyToOne
	@JoinColumn(name="my_space_id")
	private MySpace mySpace;
	
	public MySound() {
		super();
	}

	public MySound(String name) {
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

	public MySpace getMySpace() {
		return mySpace;
	}

	public void setMySpace(MySpace mySpace) {
		this.mySpace = mySpace;
	}

	@Override
	public String toString() {
		return "MySound [id=" + id + ", name=" + name + "]";
	}
	
	public MySoundDto getMySoundDto() {
		MySoundDto mySoundDto = new MySoundDto(this.id,this.name);
		try {
			MySpaceDto mySpaceDto = new MySpaceDto (this.getMySpace().getId(),this.getMySpace().getName());
			mySoundDto.setMySpaceDto(mySpaceDto);
		}catch(NullPointerException ex) {
			ex.printStackTrace();
		}
		return mySoundDto;
	}
	

}
