package ua.peresvit.sn.domain.entity;


import lombok.Data;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.Collection;

@Data	
@Entity
@Table(name = "resourceGroup")
public class ResourceGroup {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long resourceGroupId;
	// title
	private String resourceGroupName;
	// resource content type
	@ManyToOne
	@JoinColumn(name="resourceGroupTypeId")
	private ResourceGroupType resourceGroupType;
	// role of resource, it means user of which role can see it
	@ManyToOne
	@JoinColumn(name="roleId")
	private Role role;
	/*ResourceGroupId of resourceType (imagine we have Event entity with x resourceGroupId
	to get all concerning resources type = EVENT resourceGroupId = X*/
	private Long typeId; 
	@OneToMany(mappedBy = "resourceGroup")
	@LazyCollection(LazyCollectionOption.FALSE)
	Collection<Resource> resourceCollection;
    /*@ManyToMany(
            mappedBy="resourceGroups",
            targetEntity = User.class
    )
    private Set<User> users = new HashSet<User>();*/
}
