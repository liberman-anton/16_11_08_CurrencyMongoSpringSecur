package tel_ran.security.accounts;

import org.springframework.data.annotation.Id;

public class Account {
	@Id
	String username;
	String password;
	String[] role;
	
	public Account(String username, String password, String[] role) {
		super();
		this.username = username;
		this.password = password;
		this.role = role;
	}
	public Account() {
		super();
	}
	public String getUsername() {
		return username;
	}
	public String getPassword() {
		return password;
	}
	public String[] getRole() {
		return role;
	}
}