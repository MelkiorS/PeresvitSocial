package ua.peresvit.sn.domain.dao;


import org.springframework.data.jpa.repository.JpaRepository;
import ua.peresvit.sn.domain.entity.Club;

public interface ClubRepository extends JpaRepository<Club, Long> {

    <S extends Club> S save(S arg0);

    Club findOne(Long arg0);

    java.util.List<Club> findAll();
}
