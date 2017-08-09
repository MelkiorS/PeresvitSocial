package ua.peresvit.sn.domain.entity;

import lombok.Data;
import ua.peresvit.sn.config.Constant;

import javax.persistence.*;
import java.io.IOException;

@Entity
@Data
@Table(name = "mark")
public class Mark {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long markId;
    private String markName;
    private String about;
    private String imageURL;

//    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "marks")
//    private Set<User> users = new HashSet<>();

    public String encodeFileToBase64Binary(){
        try {
            return Constant.encodeFileToBase64Binary(getImageURL());
        } catch (IOException ex){return "";}
    }
}
