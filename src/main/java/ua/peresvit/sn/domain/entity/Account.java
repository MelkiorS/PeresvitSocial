package ua.peresvit.sn.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.Instant;

@SuppressWarnings("serial")
@Entity
@Table(name = "account")
public class Account implements java.io.Serializable {

	@Id
	@GeneratedValue
	@Getter @Setter private Long accountId;

	@Column(unique = true)
	@Getter @Setter private String email;
	
	@JsonIgnore
	@Getter @Setter private String password;

	@Getter @Setter private String role = "ROLE_USER";

	@Getter @Setter private Instant created;

    protected Account() {}
	
	public Account(String email, String password, String role) {
		this.email = email;
		this.password = password;
		this.role = role;
		this.created = Instant.now();
	}
}
