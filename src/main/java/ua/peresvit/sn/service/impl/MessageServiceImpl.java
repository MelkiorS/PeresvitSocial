package ua.peresvit.sn.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ua.peresvit.sn.domain.dao.ChatRepository;
import ua.peresvit.sn.domain.dao.MessageRepository;
import ua.peresvit.sn.domain.dto.ChatWithLastMessage;
import ua.peresvit.sn.domain.entity.Chat;
import ua.peresvit.sn.domain.entity.Message;
import ua.peresvit.sn.domain.entity.User;
import ua.peresvit.sn.service.MessageService;
import ua.peresvit.sn.service.UserService;

import java.sql.Timestamp;
import java.util.*;

@Service
public class MessageServiceImpl implements MessageService{

    final MessageRepository messageRepository;
    final ChatRepository chatRepository;
    final MessageSource messages;
    final UserService userService;

    @Autowired
    public MessageServiceImpl(MessageRepository messageRepository, ChatRepository chatRepository, MessageSource messages, UserService userService) {
        this.messageRepository = messageRepository;
        this.chatRepository = chatRepository;
        this.messages = messages;
        this.userService = userService;
    }

    @Override
    public Message saveMessage(Message message) {
        return messageRepository.save(message);
    }

    @Override
    public Chat saveChat(Chat chat) {
        return chatRepository.save(chat);
    }

    @Override
    public Chat saveDialog(User[] users) {
        Chat newChat = new Chat();
        Set<User> members = new HashSet<>(Arrays.asList(users));
        newChat.setMembers(members);
//        As default we post no title and Admin as owner
        newChat.setChatTitle("dialog");
        newChat.setOwner(userService.findOne(1L));
        return saveChat(newChat);
    }

    @Override
    public void deleteChat(Long chatId) {
        if (chatRepository.exists(chatId)){
            chatRepository.delete(chatId);
        }
    }

    @Override
    public Chat findOneChat(Long arg0) {
        return chatRepository.findOne(arg0);
    }

    @Override
    public Chat findDialog(Long userId) {
        Set<Chat> dialogs = chatRepository.findDialogsOfUser(userService.getCurrentUser().getUserId());
        User user = userService.findOne(userId);
        for (Chat c : dialogs) {
            if (c.getMembers().contains(user)) {
                return c;
            }
        }
        return null;
    }

    @Override
    public Chat createNewChat(Chat chat, Locale locale) {
        User creator = userService.getCurrentUser();
        chat.setOwner(creator);
        chat = saveChat(chat);
//            Adding message about chat creation
        sendMessage(creator, getNewChatCreatingMessage(creator, chat, locale));
        return chat;
    }

    @Override
    public Message getNewChatCreatingMessage(User creator, Chat chat, Locale locale) {
        Message message = new Message();
        message.setSender(creator);
        message.setChat(chat);
        message.setFunctional(true);
        message.setReadStatus(message.getReadStatus()+creator.getUserId()+",");
        message.setContent(creator.getFirstName() + " " + creator.getLastName() + " "
                + messages.getMessage("message.newChatCreationEvent", null, locale) + " "
                + "<" + chat.getChatTitle() + ">");
        return message;
    }

    @Override
    public Chat addNewMembersToChat(HashSet<User> membersToAdd, Chat chat) {
        Set<User> members = chat.getMembers();
        for (User u : membersToAdd) {
            members.add(u);
        }
        chat.setMembers(members);
        return saveChat(chat);
    }

    @Override
    public Chat deleteMemberFromChat(User user, Chat chat) {
        User currentUser = userService.getCurrentUser();
        if (currentUser.equals(user) || !chat.getMembers().contains(user) || !chat.getMembers().contains(currentUser)) {
            return chat;
        }
        Set<User> members = chat.getMembers();
        members.remove(user);
        if (members.isEmpty()) {
            deleteChat(chat.getChatId());
            return null;
        }
        chat.setMembers(members);
        return chatRepository.save(chat);
    }

    @Override
    public Message getAddingNewMemberMessage(User adder, HashSet<User> listOfNewMembers, Chat chat, Locale locale) {
        Message message = new Message();
        message.setSender(adder);
        message.setChat(chat);
        message.setFunctional(true);
        message.setReadStatus(adder.getUserId()+",");
//      adding names of added members
        Iterator<User> it = listOfNewMembers.iterator();
        StringBuilder newMembers = new StringBuilder();
        for (int i = 0; i != 1;) {
            User u = it.next();
            newMembers.append(u.getFirstName()).append(" ").append(u.getLastName());
            if (! it.hasNext()){
                i = 1;
            } else {
                newMembers.append(',').append(' ');
            }
        }
        message.setContent(adder.getFirstName() + " " + adder.getLastName() + " "
                + messages.getMessage("message.addingNewMembersEvent", null, locale) + " "
                + newMembers);
        return message;
    }

    @Override
    public Message getChangingTitleMessage(User changer, Chat chat, Locale locale) {
        Message message = new Message();
        message.setSender(changer);
        message.setChat(chat);
        message.setFunctional(true);
        message.setReadStatus(changer.getUserId()+",");
        message.setContent(changer.getFirstName() + " " + changer.getLastName() + " "
                + messages.getMessage("message.changeChatTitle", null, locale) + " "
                + "<" + chat.getChatTitle() + ">");
        return message;
    }

    @Override
    public Message sendMessage(User from, Message message) {
        message.setSender(from);
        message.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        return saveMessage(message);
    }

    @Override
    public List<Message> findMessagesByChatOrderByCreatedAt(Long chatId) {
        return messageRepository.findByChatOrderByCreatedAt(chatId);
    }

    @Override
    public Set<ChatWithLastMessage> findCustomChatsOfUser(User user) {
        return chatRepository.getChatsWithLastMessage(user);
    }

    @Override
    public long countUnreadChats() {
        User currentUser = userService.getCurrentUser();
//        Add to repo method to count
        Set<ChatWithLastMessage> chats = chatRepository.getChatsWithLastMessage(currentUser);
        Long count = 0L;
        if (chats.isEmpty()) {
            return count;
        }
        for (ChatWithLastMessage c: chats) {
            if (!(c.getReadStatus() == null)) {
                if (!c.getReadStatus().contains("," + currentUser.getUserId() + ",")) {
                    count++;
                }
            }
        }
        return count;
    }
}
