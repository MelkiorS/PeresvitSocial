package ua.peresvit.sn.domain.dao;


import org.springframework.data.jpa.repository.JpaRepository;
import ua.peresvit.sn.domain.entity.ResourceGroupType;

import java.util.List;

public interface ResourceGroupTypeRepository extends JpaRepository<ResourceGroupType, Long> {
	<S extends ResourceGroupType> S save(S arg0);

	ResourceGroupType findOne(Long arg0);

	List<ResourceGroupType> findAll();

	void delete(ResourceGroupType arg0);

	boolean equals(Object obj);

	List<ResourceGroupType> findByResourceGroupTypeId(Long resourceGroupTypeId);

	ResourceGroupType findResourceGroupTypeByGroupName(String name);
}
