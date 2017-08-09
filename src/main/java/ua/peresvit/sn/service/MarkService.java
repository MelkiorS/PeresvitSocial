package ua.peresvit.sn.service;

import org.springframework.web.multipart.MultipartFile;
import ua.peresvit.sn.domain.entity.Mark;

import java.util.List;

public interface MarkService {

    <S extends Mark> S save(S arg0);

    String saveFile(Mark mark, MultipartFile file);

    Mark findOne(Long markId);

    List<Mark> findAll();

}
