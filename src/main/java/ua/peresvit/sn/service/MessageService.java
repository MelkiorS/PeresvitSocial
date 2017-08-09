package ua.peresvit.sn.service;

import ua.peresvit.sn.domain.dto.ChatWithLastMessage;
import ua.peresvit.sn.domain.entity.Chat;
import ua.peresvit.sn.domain.entity.Message;
import ua.peresvit.sn.domain.entity.User;

import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public interface MessageService {
    Message saveMessage(Message message);

    Chat saveChat(Chat chat);

    Chat saveDialog(User[] users);

    void deleteChat(Long chatId);

    Chat findOneChat(Long arg0);

    Chat findDialog(Long userId);

    Chat createNewChat(Chat chat, Locale locale);

    Message getNewChatCreatingMessage(User creator, Chat chat, Locale locale);

    Chat addNewMembersToChat(HashSet<User> membersToAdd, Chat chat);

    Chat deleteMemberFromChat(User user, Chat chat);

    Message getAddingNewMemberMessage(User adder, HashSet<User> listOfNewMembers, Chat chat, Locale locale);

    Message getChangingTitleMessage(User changer, Chat chat, Locale locale);

    Message sendMessage(User from, Message message);

    List<Message> findMessagesByChatOrderByCreatedAt(Long chatId);

    Set<ChatWithLastMessage> findCustomChatsOfUser(User user);

    long countUnreadChats();
}
