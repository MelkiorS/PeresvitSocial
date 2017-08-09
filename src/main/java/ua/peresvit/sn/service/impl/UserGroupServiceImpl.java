package ua.peresvit.sn.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.peresvit.sn.domain.dao.UserGroupRepository;
import ua.peresvit.sn.domain.entity.UserGroup;
import ua.peresvit.sn.service.UserGroupService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserGroupServiceImpl implements UserGroupService {

    @Autowired
    private UserGroupRepository dao;

    @Override
    public List<UserGroup> findAll() {
        return dao.findAll();
    }

    @Override
    public UserGroup create(UserGroup e) {
        return dao.save(e);
    }

    @Override
    public UserGroup delete(UserGroup e) {
        dao.delete(e);
        return e;
    }

    @Override
    public UserGroup update(UserGroup e) {
        return dao.save(e);
    }

    @Override
    public Set<UserGroup> getSetFromStringArray(String[] groups) {
        Set<UserGroup> res = new HashSet<>();
        if (groups != null && groups.length > 0)
            for(int i=0; i < groups.length; i++)
                res.add(dao.findOne(Long.parseLong(groups[i])));
        return res;
    }

    @Override
    public UserGroup findById(long id) {
        return dao.findOne(id);
    }
}
