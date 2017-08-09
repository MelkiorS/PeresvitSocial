package ua.peresvit.sn.domain.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "resource")
public class Resource {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long resourceId;
	private String title;
	private String description;
	private String url;
	// resource format type
	@ManyToOne
	@JoinColumn(name="resourceTypeId")
	private ResourceType resourceType;
	// resource group 
	@ManyToOne
	@JoinColumn(name="resourceGroupId")
	private ResourceGroup resourceGroup;
	// If personal information (info concerning user)
	@ManyToOne
	@JoinColumn(name="userId")
	private User user;
	
	public Resource(){};
	
}
