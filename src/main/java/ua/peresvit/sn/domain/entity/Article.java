package ua.peresvit.sn.domain.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Data
@Table(name = "article")
public class Article {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long articleId;
    private String articleName;
    @Lob
    private String context;
    private long chapterId;
    // resource content type
    @ManyToOne
    @JoinColumn(name="resourceGroupTypeId")
    private ResourceGroupType resourceGroupType;
    // resource group type block

    // role of resource, it means user of which role can see it
    @ManyToOne
    @JoinColumn(name="roleId")
    @NotNull(message = "Не заповнена роль користувача")
    private Role role;
    // @OneToMany(mappedBy = "role")
  //  @LazyCollection(LazyCollectionOption.FALSE)
   // private Collection<User> userCollection;
}
