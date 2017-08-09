package ua.peresvit.sn.service;

import ua.peresvit.sn.domain.entity.Club;

public interface ClubService {

    <S extends Club> S save(S arg0);

    Club findOne(Long clubId);

    java.util.List<Club> findAll();
}
