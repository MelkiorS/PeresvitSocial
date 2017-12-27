package ua.peresvit.sn.domain.entity;

import lombok.Data;


import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roleId;
    @Column(nullable = false, unique = true)
    private String roleName;
   /* @ManyToMany(mappedBy = "roles", cascade = CascadeType.REMOVE)  // investigate this
    private List<User> userCollection;*/
}