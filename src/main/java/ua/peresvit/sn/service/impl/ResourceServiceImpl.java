package ua.peresvit.sn.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ua.peresvit.sn.config.Constant;
import ua.peresvit.sn.domain.dao.ResourceRepository;
import ua.peresvit.sn.domain.entity.EnumResourceType;
import ua.peresvit.sn.domain.entity.Resource;
import ua.peresvit.sn.domain.entity.ResourceType;
import ua.peresvit.sn.service.ResourceService;
import ua.peresvit.sn.service.ResourceTypeService;

import javax.servlet.ServletContext;
import java.util.List;

@Service
public class ResourceServiceImpl implements ResourceService {
	@Autowired
	private ResourceRepository resourceRepository;
	
	@Autowired
	private ResourceTypeService resourceTypeService;
	
	@Autowired
    ServletContext context; 

	@Override
	public <S extends Resource> S save(S arg0) {
		return resourceRepository.save(arg0);
	}

	@Override
	public Resource findOne(Long arg0) {
		return resourceRepository.findOne(arg0);
	}

	@Override
	public List<Resource> findAll() {
		return resourceRepository.findAll();
	}

	@Override
	public void delete(Resource resource) {
		String pathFile = resource.getUrl();
		resourceRepository.delete(resource);
		Constant.deleteFile(pathFile);
	}

	@Override
	public boolean equals(Object obj) {
		return resourceRepository.equals(obj);
	}

	@Override
	public List<Resource> findByResourceGroupId(Long resourceId) {
		return resourceRepository.findByResourceId(resourceId);
	}

	@Override
	public Resource update(Resource resource, Long resourceId) {

		Resource updatedResource = resourceRepository.findOne(resourceId);

		// delete old picture
		String oldFile = updatedResource.getUrl();
		if (oldFile != null && !oldFile.equals(resource.getUrl()))
			Constant.deleteFile(oldFile);

		org.springframework.beans.BeanUtils.copyProperties(resource, updatedResource);
		return resourceRepository.save(updatedResource);

	}

	@Override
	public String saveFile(Resource resource, MultipartFile inputFile) {
		
		String pathFile = "";
		String fileContentType = inputFile.getContentType();
		if (resource.getUser() != null)
			pathFile = Constant.UPLOAD_PATH + "/" + Constant.USR_FOLDER + resource.getUser().getUserId() + "/" + fileContentType;
		else
			pathFile = Constant.UPLOAD_PATH + "/" + Constant.USER_UNKNOWN + "/" + fileContentType;
		
		String fileURL = Constant.uploadingFile(inputFile, pathFile);
		
		// Split the mimeType into primary and sub types
		String primaryType, subType;
		try {
			primaryType = fileContentType.split("/")[0];
			subType = fileContentType.split("/")[1];
			resource.setResourceType(defineResourceTypeByMIME(primaryType, subType));
		} catch (IndexOutOfBoundsException | NullPointerException ex) {
			resource.setResourceType(resourceTypeService.findOne(EnumResourceType.OTHER.getValue()));
		}
		
		// delete old file
		if(resource.getResourceId() != null){
			String oldPath = findOne(resource.getResourceId()).getUrl();
			if(!fileURL.equals(oldPath))
				Constant.deleteFile(oldPath);		
		}

		return fileURL;
	}
	
	public ResourceType defineResourceTypeByMIME(String primaryMimeType, String subType){
		
		if(primaryMimeType.equals("video"))
			return resourceTypeService.findOne(EnumResourceType.VIDEO.getValue()); 
		if(primaryMimeType.equals("text") || subType.contains("pdf") || subType.contains("word"))
			return resourceTypeService.findOne(EnumResourceType.TEXT.getValue());
		if(primaryMimeType.equals("image"))
			return resourceTypeService.findOne(EnumResourceType.IMAGE.getValue());
		
		return resourceTypeService.findOne(EnumResourceType.OTHER.getValue());
	}	
}
