package ua.peresvit.sn.service;

import ua.peresvit.sn.domain.entity.ResourceGroupType;
import ua.peresvit.sn.domain.entity.ResourceGroupTypeChapter;

import java.util.List;

public interface ResourceGroupTypeChapterService {
    <S extends ResourceGroupTypeChapter> S save(S arg0);

    ResourceGroupTypeChapter findOne(Long arg0);

    List<ResourceGroupTypeChapter> findAll();

    void delete(ResourceGroupTypeChapter arg0);;

    List<ResourceGroupTypeChapter> findAllByResourceGroupType(ResourceGroupType id);

    boolean equals(Object obj);
}
