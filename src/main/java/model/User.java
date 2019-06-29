package model;
import javax.persistence.*;

@Entity
@Table(name = "users")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long users_id;
	@Column(name = "name", length = 45)
    private String name;
	@Column(name = "login", length = 45)
    private String login;
	@Column(name = "password", length = 45)
    private String password;
	@Column(name = "role", length = 45)
	private ROLE role;

    public User() {}

	public User(Long users_id, String name, String login, String password, ROLE role) {
		this.users_id = users_id;
		this.name	  = name;
		this.login	  = login;
		this.password = password;
		this.role	  = role;
	}

	public Long getUsers_id() {
		return users_id;
	}

	public void setUsers_id(Long users_id) {
		this.users_id = users_id;
	}

//	@NotBlank(message = "Name cannot be null")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}


	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public ROLE getRole() {return role;}

	public void setRole(ROLE role) {this.role = role;}

	public enum ROLE {
    	USER,
		ADMIN
    }

	@Override
	public String toString() {
		return "User{" +
				"users_id=" + users_id +
				", name='" + name + '\'' +
				", login='" + login + '\'' +
				", password='" + password + '\'' +
				", role=" + role +
				'}';
	}
}
