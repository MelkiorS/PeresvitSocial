package ua.peresvit.sn.service;


import ua.peresvit.sn.domain.entity.ResourceGroup;
import ua.peresvit.sn.domain.entity.ResourceGroupType;
import ua.peresvit.sn.domain.entity.Role;

public interface ResourceGroupService {

	<S extends ResourceGroup> S save(S arg0);

	ResourceGroup findOne(Long arg0);

	java.util.List<ResourceGroup> findAll();

	void delete(ResourceGroup arg0);

	boolean equals(Object obj);

	ResourceGroup findResourceGroupByResourceGroupTypeAndRole(ResourceGroupType type, Role role);
}
