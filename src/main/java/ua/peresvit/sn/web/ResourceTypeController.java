package ua.peresvit.sn.web;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import ua.peresvit.sn.domain.entity.ResourceType;
import ua.peresvit.sn.service.ResourceTypeService;

import java.util.List;


@RestController
@CrossOrigin
public class ResourceTypeController {
	@Autowired
	private ResourceTypeService resourceTypeService;
	
	    // create resourceType
		@RequestMapping(value = "/resourceType/", method = RequestMethod.POST)
		public ResponseEntity<Void> createResourceType(@RequestBody ResourceType resourceType, UriComponentsBuilder ucBuilder) {
			resourceTypeService.save(resourceType);
			HttpHeaders headers = new HttpHeaders();
			headers.setLocation(ucBuilder.path("/resourceType/{resourceTypeId}").buildAndExpand(resourceType.getResourceTypeId()).toUri());
			return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
		}

		// edit resourceType
		@RequestMapping(value = "/resourceType/{resourceTypeId}", method = RequestMethod.PUT)
		public ResponseEntity<ResourceType> updateResourceType(@PathVariable("resourceTypeId") long resourceTypeId, @RequestBody ResourceType resourceType) {
			ResourceType currentResourceType = resourceTypeService.findOne(resourceTypeId);
			if (currentResourceType == null) {
				return new ResponseEntity<ResourceType>(HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<ResourceType>(resourceTypeService.save(resourceType), HttpStatus.OK);
		}

		// delete resourceType
		@RequestMapping(value = "/resourceType/{resourceTypeId}", method = RequestMethod.DELETE)
	    public ResponseEntity<ResourceType> deleteResourceType(@PathVariable("resourceTypeId") long resourceTypeId) {
	        ResourceType resourceType = resourceTypeService.findOne(resourceTypeId);
	        if (resourceType == null) {
	            return new ResponseEntity<ResourceType>(HttpStatus.NOT_FOUND);
	        }

	        resourceTypeService.delete(resourceTypeService.findOne(resourceTypeId));
	        return new ResponseEntity<ResourceType>(HttpStatus.NO_CONTENT);
	    }

		// show resourceType by userId
		@RequestMapping(value = "/resourceType/{resourceTypeId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
		public ResponseEntity<ResourceType> getResourceType(@PathVariable("resourceTypeId") long resourceTypeId) {
			ResourceType resourceType = resourceTypeService.findOne(resourceTypeId);
			if (resourceType == null) {
				return new ResponseEntity<ResourceType>(HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<ResourceType>(resourceType, HttpStatus.OK);
		}

		// show all resourceTypes
		@RequestMapping(value = "/resourceType/", method = RequestMethod.GET)
		public ResponseEntity<List<ResourceType>> listAllResourceTypes() {
			List<ResourceType> resourceType = resourceTypeService.findAll();
			if (resourceType.isEmpty()) {
				return new ResponseEntity<List<ResourceType>>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<List<ResourceType>>(resourceType, HttpStatus.OK);
		}
}
