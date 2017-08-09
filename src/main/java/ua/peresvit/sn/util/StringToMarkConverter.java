package ua.peresvit.sn.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ua.peresvit.sn.domain.entity.Mark;
import ua.peresvit.sn.service.MarkService;

@Component
public class StringToMarkConverter implements Converter<String, Mark>{

    @Autowired
    MarkService markService;

    @Override
    public Mark convert(String source) {

        Long id = Long.parseLong((String)source);
        Mark mark = markService.findOne(id);

        return mark;
    }
}
