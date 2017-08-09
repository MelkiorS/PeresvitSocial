package ua.peresvit.sn.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.peresvit.sn.domain.dao.ArticleRepository;
import ua.peresvit.sn.domain.entity.Article;
import ua.peresvit.sn.domain.entity.ResourceGroupType;
import ua.peresvit.sn.domain.entity.Role;
import ua.peresvit.sn.service.ArticleService;

import java.util.Collection;


@Service
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    ArticleRepository articleRepository;

    public <S extends Article> S save(S arg0) {
        return articleRepository.save(arg0);
    }


    public Article findOne(Long arg0) {
        return articleRepository.findOne(arg0);
    }

    public java.util.List<Article> findAll() {
        return articleRepository.findAll();
    }

    public void delete(Article arg0) {
        articleRepository.delete(arg0);
    }

    public Collection<Article> findAllByResourceGroupTypeAndRang(ResourceGroupType type, Role role){
        return articleRepository.findAllByResourceGroupTypeAndRole(type, role);
    }

    public Article findByChapterId(long chapterId) {
        return articleRepository.findByChapterId(chapterId);
    }

    public Collection<Article> findAllByChapterIdAndResourceGroupTypeAndRang(long chapterId, ResourceGroupType type, Role role) {
        return articleRepository.findAllByChapterIdAndResourceGroupTypeAndRole(chapterId,type,role);
    }

    public Collection<Article> findAllByChapterIdAndResourceGroupType(long chapterId, ResourceGroupType type) {
        return articleRepository.findAllByChapterIdAndResourceGroupType(chapterId,type);
    }
}
