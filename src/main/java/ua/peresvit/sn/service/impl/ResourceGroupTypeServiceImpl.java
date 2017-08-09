package ua.peresvit.sn.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.peresvit.sn.domain.dao.ResourceGroupTypeRepository;
import ua.peresvit.sn.domain.entity.ResourceGroupType;
import ua.peresvit.sn.service.ResourceGroupTypeService;

import java.util.List;

@Service
public class ResourceGroupTypeServiceImpl implements ResourceGroupTypeService {
	@Autowired
	private ResourceGroupTypeRepository resourceGroupTypeRepository;
	
	@Override
	public
	<S extends ResourceGroupType> S save(S arg0){
		return resourceGroupTypeRepository.save(arg0);
	}
	
	@Override
	public
	ResourceGroupType findOne(Long arg0){
		return resourceGroupTypeRepository.findOne(arg0);
	}
	
	@Override
	public
	List<ResourceGroupType> findAll(){
		return resourceGroupTypeRepository.findAll();
	}
	
	@Override
	public
	void delete(ResourceGroupType arg0){
		resourceGroupTypeRepository.delete(arg0);
	}
	
	@Override
	public
	boolean equals(Object obj){
		return resourceGroupTypeRepository.equals(obj);
	}

	@Override
	public List<ResourceGroupType> findByResourceGroupTypeId(Long resourceGroupTypeId) {
		return resourceGroupTypeRepository.findByResourceGroupTypeId(resourceGroupTypeId);
	}
	@Override
	public ResourceGroupType findResourceGroupTypeByGroupName(String name){
		return resourceGroupTypeRepository.findResourceGroupTypeByGroupName(name);
	}
}