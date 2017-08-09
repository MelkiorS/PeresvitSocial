package ua.peresvit.sn.domain.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Data
@Table(name = "resourceGroupTypeChapter")
public class ResourceGroupTypeChapter {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long chapterId;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="resourceGroupTypeId")
    private ResourceGroupType resourceGroupType;
    private String chapterName;
    @OneToMany(mappedBy = "chapterId", fetch = FetchType.EAGER)
    Collection<Article> articleCollection;
}
