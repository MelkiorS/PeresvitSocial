package ua.peresvit.sn.domain.dao;


import org.springframework.data.jpa.repository.JpaRepository;
import ua.peresvit.sn.domain.entity.Resource;

import java.util.List;


public interface ResourceRepository extends JpaRepository<Resource, Long> {
	<S extends Resource> S save(S arg0);

	Resource findOne(Long arg0);

	List<Resource> findAll();

	void delete(Resource arg0);

	boolean equals(Object obj);

	List<Resource> findByResourceId(Long resourceId);
}
