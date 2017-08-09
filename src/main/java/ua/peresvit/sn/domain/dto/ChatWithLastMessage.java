package ua.peresvit.sn.domain.dto;

import lombok.Data;
import ua.peresvit.sn.domain.entity.User;

import java.util.Date;


@Data
public class ChatWithLastMessage {
    private long chatId;
    private String chatTitle;
    private long messageId;
    private String content;
    private Date createdAt;
    private String readStatus;
    private User sender;

    public ChatWithLastMessage(long chatId, String chatTitle, long messageId, String content, Date createdAt, String readStatus, User sender) {
        this.chatId = chatId;
        this.chatTitle = chatTitle;
        this.messageId = messageId;
        this.content = content;
        this.createdAt = createdAt;
        this.readStatus = readStatus;
        this.sender = sender;
    }
}
