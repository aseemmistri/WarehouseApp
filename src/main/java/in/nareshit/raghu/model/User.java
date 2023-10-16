package in.nareshit.raghu.model;

import java.util.List;

import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "usertab")
public class User {

	@Id
	@GeneratedValue(generator = "user")
	@GenericGenerator(name = "user", strategy = "in.nareshit.raghu.UserIdGenerator")
	@Column(name = "uid")
	private String id;

	@Column(name = "uname")
	private String name;

	@Column(name = "email")
	private String email;

	@Column(name = "pwd")
	private String pwd;

	@ElementCollection (fetch = FetchType.EAGER)
	@CollectionTable(name="rolestab",joinColumns = @JoinColumn(name="uid"))
	@Column(name="role")
	private List<String> roles;

	@Column(name = "uactive")
	private boolean active;
	
	@Column(name = "uotp")
	private String otp;
}
