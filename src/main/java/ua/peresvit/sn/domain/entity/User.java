package ua.peresvit.sn.domain.entity;

import lombok.Data;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import ua.peresvit.sn.config.Constant;
import ua.peresvit.sn.security.RoleEnum;
import ua.peresvit.sn.util.helper.SocialMediaService;
import javax.validation.constraints.Size;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Entity
@Table(name = "users")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    @Size(max = 25, message = "Кількість символів не має перевущувати 255")
    private String firstName;
    @Size(max = 25, message = "Кількість символів не має перевущувати 255")
    private String lastName;
    @Size(max = 25, message = "Кількість символів не має перевущувати 255")
    private String middleName;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private String password;

    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(
            name = "user_role",
            joinColumns = {@JoinColumn(name = "user_id",
                    referencedColumnName = "userId")},
            inverseJoinColumns = {@JoinColumn(name = "role_id",
                    referencedColumnName = "roleId")}
    )
    private List<Role> roles;


    private String avatarURL;

    private String profileVK;
    private String profileFB;
    private String profileGoogle;
    private String profileInstagram;
    private Character sex;

    @ManyToOne
    @JoinColumn(name = "cityId")
    private City city;

    @ManyToOne
    @JoinColumn(name = "clubId")
    private Club club;

    @ManyToOne
    @JoinColumn(name = "combatArtId")
    private CombatArt combatArt;

    @ManyToOne
    private User mentor;


    //	@OneToMany(mappedBy = "author")
//	private Collection<Message> receivedMessages;
//	@OneToMany(mappedBy = "receiver")
//	private Collection<Message> sentMessages;
    @ManyToMany(mappedBy = "members", fetch = FetchType.LAZY)
    private Set<Chat> chats = new HashSet<>();

    private String aboutMe;

    // Account verification status
    @Column(columnDefinition = "boolean default true")
    private boolean enabled;

    @ManyToMany(
            fetch = FetchType.EAGER,
            targetEntity = Mark.class
    )
    @JoinTable(
            name = "user_mark",
            joinColumns = {@JoinColumn(name = "userId")},
            inverseJoinColumns = {@JoinColumn(name = "markId")}
    )
    private Set<Mark> marks = new HashSet<>();

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    private Set<Achievement> achievements = new HashSet<>();

    @Override
    public String toString() {
        return "" + firstName + " " + lastName;
    }

    public User() {
        this.setEnabled(false);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        return userId.equals(user.userId);
    }

    @Override
    public int hashCode() {
        return userId == null ? 0 : userId.hashCode();
    }

    public boolean isAdmin() { // TODO use stream instead of this BS
//        getRole().stream().filter(e->e.getRoleName().equals(RoleEnum.ADMIN.getCode())
        for (Role r:getRoles()) {
            if (r.getRoleName().equals(RoleEnum.ADMIN.getCode())){
                return true;
            }
        }
//        return role.getRoleName().equals(RoleEnum.ADMIN.getCode();
        return false;
    }

    public String encodeFileToBase64Binary() {
        try {
            return Constant.encodeFileToBase64Binary(getAvatarURL());
        } catch (IOException ex) {
            return "";
        }
    }

    public List<SocialMediaService> getSocialMediaServices() {
        List<SocialMediaService> list = new ArrayList<>();
        if (profileFB != null) list.add(SocialMediaService.FACEBOOK);
        if (profileGoogle != null) list.add(SocialMediaService.GOOGLE);
        if (profileVK != null) list.add(SocialMediaService.VKONTAKTE);
        return list;
    }
}
