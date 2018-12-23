package entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import com.fasterxml.jackson.annotation.JsonInclude;

import dto.MySpaceDto;
import dto.MyVideoDto;

@Entity
@JsonInclude
@NamedQueries({
	@NamedQuery(name="entities.MyVideo.selectAll",
			query = "select m from MyVideo m")
})
public class MyVideo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	
	@ManyToOne
	@JoinColumn(name="myspace_id")
	private MySpace mySpace;
	
	public MyVideo() {
		super();
	}

	public MyVideo(String name) {
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
		return "MyVideo [id=" + id + ", name=" + name + "]";
	}
	
	public MyVideoDto getMyVideoDto() {
		MyVideoDto myVideoDto = new MyVideoDto(this.id, this.name);
		MySpaceDto mySpaceDto = new MySpaceDto(this.mySpace.getId(), this.mySpace.getName());
		myVideoDto.setMySpaceDto(mySpaceDto);
		return myVideoDto;	
	}

}
