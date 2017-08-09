package ua.peresvit.sn.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.peresvit.sn.domain.dao.ResourceTypeRepository;
import ua.peresvit.sn.domain.entity.ResourceType;
import ua.peresvit.sn.service.ResourceTypeService;

import java.util.List;

@Service
public class ResourceTypeServiceImpl implements ResourceTypeService {
	@Autowired
	private ResourceTypeRepository resourceTypeRepository;
	
	@Override
	public
	<S extends ResourceType> S save(S arg0){
		return resourceTypeRepository.save(arg0);
	}
	
	@Override
	public
	ResourceType findOne(Long arg0){
		return resourceTypeRepository.findOne(arg0);
	}
	
	@Override
	public
	List<ResourceType> findAll(){
		return resourceTypeRepository.findAll();
	}
	
	@Override
	public
	void delete(ResourceType arg0){
		resourceTypeRepository.delete(arg0);
	}
	
	@Override
	public
	boolean equals(Object obj){
		return resourceTypeRepository.equals(obj);
	}

	@Override
	public List<ResourceType> findByResourceTypeId(Long resourceTypeId) {
		return resourceTypeRepository.findByResourceTypeId(resourceTypeId);
	}
}
