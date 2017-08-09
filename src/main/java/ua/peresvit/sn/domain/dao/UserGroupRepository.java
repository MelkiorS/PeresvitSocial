package ua.peresvit.sn.domain.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.peresvit.sn.domain.entity.UserGroup;

public interface UserGroupRepository extends JpaRepository<UserGroup, Long> {

}
