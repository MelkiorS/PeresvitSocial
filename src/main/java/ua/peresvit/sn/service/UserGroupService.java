package ua.peresvit.sn.service;

import ua.peresvit.sn.domain.entity.UserGroup;

import java.util.List;
import java.util.Set;

public interface UserGroupService {

    List<UserGroup> findAll();
    UserGroup create(UserGroup e);
    UserGroup delete(UserGroup e);
    UserGroup update(UserGroup e);
    UserGroup findById(long id);

    Set<UserGroup> getSetFromStringArray(String[] groups);

}
