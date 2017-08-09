package ua.peresvit.sn.service;

import ua.peresvit.sn.domain.entity.ResourceGroupType;

import java.util.List;

public interface ResourceGroupTypeService {

	<S extends ResourceGroupType> S save(S arg0);

	ResourceGroupType findOne(Long arg0);

	List<ResourceGroupType> findAll();

	void delete(ResourceGroupType arg0);

	boolean equals(Object obj);

	List<ResourceGroupType> findByResourceGroupTypeId(Long resourceGroupTypeId);

	ResourceGroupType findResourceGroupTypeByGroupName(String name);
}