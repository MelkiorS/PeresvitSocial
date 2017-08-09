package ua.peresvit.sn.domain.dao;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ua.peresvit.sn.domain.entity.Post;
import ua.peresvit.sn.domain.entity.User;

import java.util.List;

@Repository
@Transactional
public interface PostRepository  extends JpaRepository<Post, Long> {

    <S extends Post> S save(S arg0);

    Post findOne(Long arg0);

    void delete(Long arg0);

    List<Post> findAll();

    Post findByUser(User user);

    Page<Post> findAll(Pageable pageable);

}
