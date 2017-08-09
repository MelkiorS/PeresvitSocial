package ua.peresvit.sn.domain.dao;


import org.springframework.data.jpa.repository.JpaRepository;
import ua.peresvit.sn.domain.entity.Article;
import ua.peresvit.sn.domain.entity.ResourceGroupType;
import ua.peresvit.sn.domain.entity.Role;

import java.util.Collection;

public interface ArticleRepository extends JpaRepository<Article, Long> {
    <S extends Article> S save(S arg0);

    Article findOne(Long arg0);

    java.util.List<Article> findAll();

    void delete(Article arg0);

    boolean equals(Object obj);

    Article findByArticleId(String resourceGroupId);

    Collection<Article> findAllByResourceGroupTypeAndRole(ResourceGroupType type, Role role);

    Collection<Article> findAllByChapterIdAndResourceGroupTypeAndRole(long chapterId, ResourceGroupType type, Role role);

    Collection<Article> findAllByChapterIdAndResourceGroupType(long chapterId, ResourceGroupType type);

    Article findByChapterId(long chapterId);
}