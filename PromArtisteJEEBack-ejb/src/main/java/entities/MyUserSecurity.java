package entities;

public class MyUserSecurity extends MyUser{

	private String password = null;
	private String token = null;
	private String role = null;
	
	public MyUserSecurity() {
		super();
	}

	public MyUserSecurity(String password, String token, String role) {
		super();
		this.password = password;
		this.token = token;
		this.role = role;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return "MyUserSecurity [password=" + password + ", token=" + token + ", role=" + role + "]";
	}

}
