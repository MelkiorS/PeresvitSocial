package ua.peresvit.sn.domain.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "userGroup")
@Data
public class UserGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_groups", joinColumns = {
            @JoinColumn(name = "usergroup_id", nullable = false, updatable = false)},
            inverseJoinColumns = { @JoinColumn(name = "user_id", nullable = false, updatable = false) }
    )
    private Set<User> users;

    @Override
    public String toString() {
        return "" + name;
    }
}
