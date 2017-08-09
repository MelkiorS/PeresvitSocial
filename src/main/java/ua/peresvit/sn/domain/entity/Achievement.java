package ua.peresvit.sn.domain.entity;

import lombok.Data;
import ua.peresvit.sn.config.Constant;

import javax.persistence.*;
import java.io.IOException;

@Entity
@Data
@Table(name = "achievement")
public class Achievement {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long achievementId;
    private String achievementName;
    private String description;
    private String imageURL;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    public String encodeFileToBase64Binary(){
        try {
            return Constant.encodeFileToBase64Binary(getImageURL());
        } catch (IOException ex){return "";}
    }

}
