package ua.peresvit.sn.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ua.peresvit.sn.config.Constant;
import ua.peresvit.sn.domain.dao.AchievementRepository;
import ua.peresvit.sn.domain.entity.Achievement;
import ua.peresvit.sn.domain.entity.User;
import ua.peresvit.sn.service.AchievementService;

import java.util.List;

@Service
public class AchievementServiceImpl implements AchievementService{

    @Autowired
    private AchievementRepository achievementRepository;

    @Override
    public <S extends Achievement> S save(S arg0) { return achievementRepository.save(arg0); }
    @Override
    public Achievement findOne(Long arg0) {
        return achievementRepository.findOne(arg0);
    }
    @Override
    public List<Achievement> findAll() {return achievementRepository.findAll(); }
    @Override
    public List<Achievement> findByUser(User user) {return achievementRepository.findByUser(user); }

    @Override
    public String saveFile(Achievement achievement, MultipartFile inputFile) {

        String pathFile = "";
        String fileContentType = inputFile.getContentType();
        if (achievement.getUser() != null)
            pathFile = Constant.UPLOAD_PATH + "/" + Constant.USR_FOLDER + achievement.getUser().getUserId() +
                    "/" + Constant.ACHIEVE + "/" + fileContentType;
        else
            pathFile = Constant.UPLOAD_PATH + "/" + Constant.USER_UNKNOWN + "/" + Constant.ACHIEVE +
                    "/" + fileContentType;

        String fileURL = Constant.uploadingFile(inputFile, pathFile);

//        // delete old file
//        if(achievement != null){
//            String oldPath = achievement.getImageURL();
//            if(!fileURL.equals(oldPath))
//                Constant.deleteFile(oldPath);
//        }

        return fileURL;
    }
}
