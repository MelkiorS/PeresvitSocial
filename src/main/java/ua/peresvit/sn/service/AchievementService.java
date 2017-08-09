package ua.peresvit.sn.service;

import org.springframework.web.multipart.MultipartFile;
import ua.peresvit.sn.domain.entity.Achievement;
import ua.peresvit.sn.domain.entity.User;

import java.util.List;

public interface AchievementService {

    String saveFile(Achievement achievement, MultipartFile file);

    <S extends Achievement> S save(S arg0);

    Achievement findOne(Long markId);

    List<Achievement> findAll();

    List<Achievement> findByUser(User user);

}
