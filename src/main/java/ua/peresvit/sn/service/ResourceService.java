package ua.peresvit.sn.service;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ua.peresvit.sn.domain.entity.Resource;

import java.util.List;

public interface ResourceService{
	
	@Transactional
	<S extends Resource> S save(S arg0);
	
	String saveFile(Resource resource, MultipartFile file);
	 
	Resource findOne(Long arg0);

	List<Resource> findAll();

	@Transactional
	void delete(Resource arg0);

	boolean equals(Object obj);

	List<Resource> findByResourceGroupId(Long resourceGroupId);
	
	@Transactional
	Resource update(Resource resource, Long resourceId);
}
