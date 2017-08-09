package ua.peresvit.sn.domain.entity;


import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.NotBlank;
import ua.peresvit.sn.config.Constant;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.IOException;
import java.util.Date;

@Entity
@Data
@Table(name = "post")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;

    @NotBlank(message = "Title can't be empty.")
    @Size(min = 3, message = "A title must be at least 3 characters.")
    @Column(nullable = false)
    private String title;

    @NotBlank(message = "Body can't be empty")
    @Column(nullable = false, length = 3000)
    private String body;
    private String url;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_date")
    private Date createDate;

    @ManyToOne
    @JoinColumn(name="userId")
    private User user;

    public String encodeFileToBase64Binary(){
        try {
            return Constant.encodeFileToBase64Binary(getUrl());
        } catch (IOException ex){return "";}
    }

    @Override
    public String toString() {
        return "Post{" +
                "postId=" + postId +
                ", title='" + title + '\'' +
                ", body='" + body + '\'' +
                ", url='" + url + '\'' +
                ", createDate=" + createDate +
                ", user=" + user +
                '}';
    }
}
