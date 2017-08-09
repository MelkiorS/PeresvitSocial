package ua.peresvit.sn.service;

import ua.peresvit.sn.domain.entity.Role;

import java.util.List;

public interface RoleService {
	<S extends Role> S save(S arg0);

	Role findOne(Long arg0);

	List<Role> findAll();

	void delete(Role arg0);

	boolean equals(Object obj);

	List<Role> findByRoleId(Long roleId);
}
