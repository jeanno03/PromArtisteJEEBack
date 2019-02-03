package entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import dto.MyRoleDto;
import dto.MyUserDto;

@Entity
public class MyRole implements Serializable{
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private String name;
	
	@ManyToOne
	@JoinColumn(name="myuser_id")
	private MyUser myUser;
	
	public MyRole() {
		super();
	}

	public MyRole(String name) {
		super();
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

	public MyUser getMyUser() {
		return myUser;
	}

	public void setMyUser(MyUser myUser) {
		this.myUser = myUser;
	}

	@Override
	public String toString() {
		return "MyRole [id=" + id + ", name=" + name + "]";
	}
	
	public MyRoleDto getMyRoleDto() {
		MyRoleDto myRoleDto = new MyRoleDto(this.id, this.name);
		MyUserDto myUserDto = new MyUserDto (this.myUser.getId(), this.myUser.getEmail(), this.myUser.getArtistName(), this.myUser.getLastName(), this.myUser.getFirstName());
		myRoleDto.setMyUserDto(myUserDto);
		return myRoleDto;
		
	}

}
