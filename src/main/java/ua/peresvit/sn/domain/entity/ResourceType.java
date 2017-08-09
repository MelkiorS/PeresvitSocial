package ua.peresvit.sn.domain.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "resourceType")
public class ResourceType {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long resourceTypeId;	
	private String typeName;
}
