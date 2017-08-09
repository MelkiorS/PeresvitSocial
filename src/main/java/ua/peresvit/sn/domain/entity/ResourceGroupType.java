package ua.peresvit.sn.domain.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name = "resourceGroupType")
public class ResourceGroupType {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long resourceGroupTypeId;	
	private String groupName; // ManyToOne



	private String caption;
	@OneToMany(mappedBy = "resourceGroupType", fetch = FetchType.EAGER)
	private List<ResourceGroupTypeChapter> chapterList;
}