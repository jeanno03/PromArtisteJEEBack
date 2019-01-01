package entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import dto.MyPictureDto;
import dto.MySpaceDto;

@Entity
@NamedQueries({
	@NamedQuery(name="entities.MyPicture.getLastMyPictureOfDay",
			query="select m from MyPicture m where m.registeredDate = :paramRegisteredDate order by m.registeredDate desc" ),
	@NamedQuery(name="entities.MyPicture.getLastMyPicture",
	query="select m from MyPicture m order by m.id desc")
})
public class MyPicture implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Date registeredDate;
	//chemin de sauvegarde
	private String path;
	//nom du fichier d'origine
	private String originName;

	@ManyToOne
	@JoinColumn(name="myspace_id")
	private MySpace mySpace;

	public MyPicture() {
		super();
	}

	public MyPicture(Date registeredDate, String path, String originName) {
		super();
		this.registeredDate = registeredDate;
		this.path = path;
		this.originName = originName;
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

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getOriginName() {
		return originName;
	}

	public void setOriginName(String originName) {
		this.originName = originName;
	}

	public MySpace getMySpace() {
		return mySpace;
	}

	public void setMySpace(MySpace mySpace) {
		this.mySpace = mySpace;
	}

	@Override
	public String toString() {
		return "MyPicture [id=" + id + ", registeredDate=" + registeredDate + "]";
	}

	public MyPictureDto getMyPictureDto() {
		MyPictureDto myPictureDto = new MyPictureDto(this.id,this.registeredDate);
		try {
			MySpaceDto mySpaceDto = new MySpaceDto(this.mySpace.getId(), this.mySpace.getName());
			myPictureDto.setMySpaceDto(mySpaceDto);
		}catch(NullPointerException ex) {
			ex.printStackTrace();
		}
		return myPictureDto;
	}


}
