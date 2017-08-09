package ua.peresvit.sn.service;


import ua.peresvit.sn.domain.entity.CombatArt;

public interface CombatArtService {

    <S extends CombatArt> S save(S arg0);

    CombatArt findOne(Long artId);

    java.util.List<CombatArt> findAll();
}
