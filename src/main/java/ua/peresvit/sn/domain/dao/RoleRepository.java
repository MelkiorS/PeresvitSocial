package ua.peresvit.sn.domain.dao;


import org.springframework.data.jpa.repository.JpaRepository;
import ua.peresvit.sn.domain.entity.Role;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role, Long> {
	<S extends Role> S save(S arg0);

	Role findOne(Long arg0);

	Role findByRoleName(String roleName);

	List<Role> findAll();

	void delete(Role arg0);

	boolean equals(Object obj);

	List<Role> findByRoleId(Long roleId);
}
