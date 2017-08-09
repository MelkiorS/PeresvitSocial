package ua.peresvit.sn.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.peresvit.sn.domain.dao.CombatArtReppository;
import ua.peresvit.sn.domain.entity.CombatArt;
import ua.peresvit.sn.service.CombatArtService;

@Service
public class CombatArtServiceImpl implements CombatArtService {

    @Autowired
    private CombatArtReppository combatArtReppository;

    @Override
    public <S extends CombatArt> S save(S arg0) { return combatArtReppository.save(arg0); }
    @Override
    public CombatArt findOne(Long arg0) {
        return combatArtReppository.findOne(arg0);
    }
    @Override
    public java.util.List<CombatArt> findAll() {return combatArtReppository.findAll(); }
}
