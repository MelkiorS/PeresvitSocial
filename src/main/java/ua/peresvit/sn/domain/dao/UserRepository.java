package ua.peresvit.sn.domain.dao;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ua.peresvit.sn.domain.entity.Role;
import ua.peresvit.sn.domain.entity.User;
import ua.peresvit.sn.domain.entity.UserGroup;

import java.util.List;
import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Long> {

    <S extends User> S save(S arg0);

    Optional<User> findByEmail(String email);

    User findByFirstNameAndLastName(String firstName, String lastName);

    User findOne(Long userId);

    List<User> findAll();

    List<User> findByRoles(Role role);

    void delete(User arg0);

    boolean equals(Object obj);

//    @Query("select distinct u from UserGroup ug INNER JOIN ug.users u where ug in :uglist order by u.roles.userId desc") // TODO edit ordering
    @Query("select distinct u from UserGroup ug INNER JOIN ug.users u where ug in :uglist")
    List<User> getGroupsUsers(@Param("uglist") UserGroup[] ug);

    @Query("select distinct ug from UserGroup ug INNER JOIN ug.users u where u = :user")
    List<UserGroup> getUserGroups(@Param("user") User user);
}
