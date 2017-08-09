package ua.peresvit.sn.domain.dao;


import org.springframework.data.jpa.repository.JpaRepository;
import ua.peresvit.sn.domain.entity.ResourceType;

import java.util.List;

public interface ResourceTypeRepository extends JpaRepository<ResourceType, Long> {
	<S extends ResourceType> S save(S arg0);

	ResourceType findOne(Long arg0);

	List<ResourceType> findAll();

	void delete(ResourceType arg0);

	boolean equals(Object obj);

	List<ResourceType> findByResourceTypeId(Long resourceTypeId);

}
