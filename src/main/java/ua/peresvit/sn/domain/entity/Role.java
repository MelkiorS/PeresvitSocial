package ua.peresvit.sn.domain.entity;

import lombok.Data;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Data
@Table(name = "role")
public class Role {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long roleId;
	private String roleName;
	@OneToMany(mappedBy = "role")
	@LazyCollection(LazyCollectionOption.FALSE)
	@Cascade(CascadeType.DELETE)
	private Collection<User> userCollection;
}