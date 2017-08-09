package ua.peresvit.sn.domain.entity;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Data
@Table(name = "message")
public class Message {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long messageId;
    private String content;
    private Timestamp createdAt;
//    String of users, who read message
    @Column(length = 21578)
    private String readStatus;
//    Functional message like about creating chat or inviting new members;
    private boolean functional;

    @ManyToOne
    @JoinColumn(name="senderId")
    private User sender;
    @ManyToOne
    @JoinColumn(name="chatId")
    private Chat chat;

    public Message() {
        readStatus = ",";
    }
}
