package dto;

public class MySpaceDto {
	
	private Long id;
    private String name;
    
	public MySpaceDto() {
		super();
	}

	public MySpaceDto(Long id, String name) {
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

	@Override
	public String toString() {
		return "MySpaceDto [id=" + id + ", name=" + name + "]";
	}
    	    
}

