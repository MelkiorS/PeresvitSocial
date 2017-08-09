package ua.peresvit.sn.domain.entity;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "club")
@Data
public class Club {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long clubId;
    private String clubName;
    private String address;
}
