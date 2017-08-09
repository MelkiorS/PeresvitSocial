package ua.peresvit.sn.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.peresvit.sn.domain.dao.RoleRepository;
import ua.peresvit.sn.domain.entity.Role;
import ua.peresvit.sn.service.RoleService;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
	@Autowired
	private RoleRepository roleRepository;
	
	@Override
	public
	<S extends Role> S save(S arg0){
		return roleRepository.save(arg0);
	}
	
	@Override
	public
	Role findOne(Long arg0){
		return roleRepository.findOne(arg0);
	}
	
	@Override
	public
	List<Role> findAll(){
		return roleRepository.findAll();
	}
	
	@Override
	public
	void delete(Role arg0){
		roleRepository.delete(arg0);
	}
	
	@Override
	public boolean equals(Object obj){
		return roleRepository.equals(obj);
	}

	@Override
	public List<Role> findByRoleId(Long roleId) {
		return roleRepository.findByRoleId(roleId);
	}
}
