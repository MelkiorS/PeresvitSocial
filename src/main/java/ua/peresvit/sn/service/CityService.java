package ua.peresvit.sn.service;


import ua.peresvit.sn.domain.entity.City;

public interface CityService {

    <S extends City> S save(S arg0);

    City findOne(Long cityId);

    java.util.List<City> findAll();

}
