package entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import dto.MyPictureDto;
import dto.MySpaceDto;
import dto.MyUserDto;
import dto.MyVideoDto;

@Entity
@NamedQueries({
	@NamedQuery(name="entities.MySpace.selectAll",
			query = "select m from MySpace m"),
	@NamedQuery(name="entities.MySpace.getMySpaceByMyUserId",
	query="select m from MySpace m where m.myUser.id = :paramUserId")
})
@JsonInclude
//@XmlRootElement(name = "myspace")
public class MySpace implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;

	@ManyToOne
	@JoinColumn(name="myuser_id")
	private MyUser myUser;

	@OneToMany(mappedBy="mySpace", cascade= {CascadeType.ALL})
	private Collection<MyVideo> myVideos;

	@OneToMany(mappedBy="mySpace", cascade= {CascadeType.ALL})  
	private Collection<MyPicture> myPictures;

	public MySpace() {
		super();
		myVideos = new HashSet();
		myPictures = new HashSet();
	}

	public MySpace(Long id, String name) {
		this();
		this.id = id;
		this.name = name;
	}

	public MySpace(String name) {
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

	public MyUser getMyUser() {
		return myUser;
	}

	public void setMyUser(MyUser myUser) {
		this.myUser = myUser;
	}

	public Collection<MyVideo> getMyVideos() {
		return myVideos;
	}

	public void setMyVideos(Collection<MyVideo> myVideos) {
		this.myVideos = myVideos;
	}	

	public Collection<MyPicture> getMyPictures() {
		return myPictures;
	}

	public void setMyPictures(Collection<MyPicture> myPictures) {
		this.myPictures = myPictures;
	}

	@Override
	public String toString() {
		return "MySpace [id=" + id + ", name=" + name + "]";
	}    

	public MySpaceDto getMySpaceDto() {
		try {
			MySpaceDto mySpaceDto = new MySpaceDto(this.id,this.name);

			MyUserDto myUserDto = new MyUserDto(this.myUser.getId(),this.myUser.getEmail(), this.myUser.getArtistName(), this.myUser.getFirstName(), this.myUser.getLastName());

			mySpaceDto.setMyUserDto(myUserDto);

			try {
				HashSet<MyVideoDto> myVideosDto = new HashSet();
				for(MyVideo m : this.myVideos) {
					MyVideoDto myVideoDto = new MyVideoDto (m.getId(), m.getName());
					myVideosDto.add(myVideoDto);
				}
				mySpaceDto.setMyVideosDto(myVideosDto);

			}catch(NullPointerException ex) {
				ex.printStackTrace();
			}

			try {
				HashSet<MyPictureDto> myPicturesDto = new HashSet();
				for(MyPicture m : this.myPictures) {
					MyPictureDto myPictureDto = new MyPictureDto(m.getId(), m.getRegisteredDate());
					myPicturesDto.add(myPictureDto);
				}

				mySpaceDto.setMyPicturesDto(myPicturesDto);

			}catch(NullPointerException ex) {
				ex.printStackTrace();
			}
			return mySpaceDto;
			
		}catch(NullPointerException ex) {
			ex.printStackTrace();

		}
		return null;
	}

}
