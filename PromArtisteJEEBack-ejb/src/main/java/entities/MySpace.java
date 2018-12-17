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
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@NamedQueries({
	@NamedQuery(name="entities.MySpace.selectAll",
			query = "select m from MySpace m"),
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
//    @JsonManagedReference   
    private MyUser myUser;
    
	public MySpace() {
		super();
	}

	public MySpace(Long id, String name) {
		super();
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

	@Override
	public String toString() {
		return "MySpace [id=" + id + ", name=" + name + "]";
	}       

}
