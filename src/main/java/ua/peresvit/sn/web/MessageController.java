package ua.peresvit.sn.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ua.peresvit.sn.domain.dto.ChatWithLastMessage;
import ua.peresvit.sn.domain.entity.Chat;
import ua.peresvit.sn.domain.entity.Message;
import ua.peresvit.sn.domain.entity.User;
import ua.peresvit.sn.domain.entity.UserGroup;
import ua.peresvit.sn.security.RoleEnum;
import ua.peresvit.sn.service.MessageService;
import ua.peresvit.sn.service.UserService;

import java.util.*;

@Controller
@RequestMapping(value = "/home/messages")
public class MessageController {
    final MessageService messageService;
    final UserService userService;
//    final MessageSource messages;

    @Autowired
    public MessageController(MessageService messageService, UserService userService/*, MessageSource messages*/) {
        this.messageService = messageService;
        this.userService = userService;
//        this.messages = messages;
    }

    //  after entering main messages' page we get list of all chats, we can create new chat or send new message
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String getAllChats(Model model) {
        User currentUser = userService.getCurrentUser();
        Set<ChatWithLastMessage> chats = messageService.findCustomChatsOfUser(currentUser);
        Set<ChatWithLastMessage> readChats = new LinkedHashSet<>();
        Set<ChatWithLastMessage> unreadChats = new LinkedHashSet<>();
        for (ChatWithLastMessage chat : chats) {
            if (chat.getChatTitle().equals("dialog")) {
                for (User u : messageService.findOneChat(chat.getChatId()).getMembers()) {
                    if (!u.equals(currentUser)) {
                        chat.setChatTitle(u.getFirstName() + " " + u.getLastName());
                    }
                }
            }
            if (!chat.getReadStatus().contains(","+currentUser.getUserId()+",")) {
                unreadChats.add(chat);
            } else {
                readChats.add(chat);
            }
        }
        unreadChats.addAll(readChats);
        model.addAttribute("chatList", unreadChats);
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("unreadMessages", messageService.countUnreadChats());
        return "home/messages/chats";
    }

    //  creating new chat from main messages' page
    @RequestMapping(value = "/newChat", method = RequestMethod.GET)
    public String createFormForNewChat(Model model) {
        model.addAttribute(new Chat());
        List<UserGroup> ug = userService.getUserGroups(userService.getCurrentUser());
        UserGroup[] uga;
        uga = new UserGroup[ug.size()];
        uga = ug.toArray(uga);
        if (userService.getCurrentUser().getRoles().get(0).getRoleName().equals(RoleEnum.ADMIN.getCode())) { // TODO refactor To Multi roles
            model.addAttribute("userList", userService.findAllWithoutCurrent());
        } else {
            model.addAttribute("userList", uga.length==0 ? new ArrayList<User>() : userService.getGroupsUsersWithoutCurrent(ug.toArray(uga)));
        }
        model.addAttribute("unreadMessages", messageService.countUnreadChats());
        return "home/messages/newChat";
    }

    //  creating new chat from main messages' page
    @RequestMapping(value = "/newChat", method = RequestMethod.POST)
    public String createNewChatFromMainPage(Chat chat, Model model, Locale locale) {
        if (!Objects.equals(chat.getChatTitle(), "") && !chat.getMembers().isEmpty()) {
            Set<User> members = chat.getMembers();
            members.add(userService.getCurrentUser());   // adding chat's creator as a member of the chat
            chat.setMembers(members);
            chat = messageService.createNewChat(chat, locale);
            model.addAttribute("chatId", chat.getChatId());
            return "redirect:/home/messages/{chatId}";
        } else {
//            model.addAttribute("message", messages.getMessage("message.chatCreationError", null, locale));
            return getAllChats(model);
        }
    }

    //  here we enter certain chat with it's messages
    @RequestMapping(value = "/{chatId}", method = RequestMethod.GET)
    public String getSingleChat(@PathVariable("chatId") Long chatId, Model model) {
        Chat chat = messageService.findOneChat(chatId);
        User currentUser = userService.getCurrentUser();
        if (!chat.getMembers().contains(currentUser)) {
            return "redirect:/home/messages";
        }
        List<Message> messages = messageService.findMessagesByChatOrderByCreatedAt(chatId);
        if (!messages.isEmpty()) {
            Message lastMessage = messages.get(messages.size()-1);
            if (!lastMessage.getReadStatus().contains(","+currentUser.getUserId()+",")){
                lastMessage.setReadStatus(lastMessage.getReadStatus()+currentUser.getUserId()+",");
                messageService.saveMessage(lastMessage);
            }
            messages = messageService.findMessagesByChatOrderByCreatedAt(chatId);
            model.addAttribute("messagesList", messages);
        }
        if (chat.getChatTitle().equals("dialog")) {
            for (User u : chat.getMembers()) {
                if (!u.equals(currentUser)) {
                    chat.setChatTitle(u.getFirstName() + " " + u.getLastName());
                }
            }
            model.addAttribute("dialog", true);
        }
//      adding owner permissions to the chat view
        if (currentUser.equals(chat.getOwner()) || currentUser.getRoles().get(0).getRoleName().equals(RoleEnum.ADMIN.getCode())) { // TODO refactor To Multi roles
            model.addAttribute("ownerPermission", true);
        } else {
            model.addAttribute("ownerPermission", false);
        }
        model.addAttribute("currentUser", currentUser);
        model.addAttribute(chat);
        model.addAttribute(new Message());
        model.addAttribute("unreadMessages", messageService.countUnreadChats());
        return "home/messages/messages";
    }

    // post new message to certain chat and refresh chat
    @RequestMapping(value = "/{chatId}", method = RequestMethod.POST)
    public String sendMessageFromChat(@PathVariable("chatId") Long chatId, Model model, Message message) {
        messageService.sendMessage(userService.getCurrentUser(), message);
        model.addAttribute("chatId", chatId);
        return "redirect:/home/messages/{chatId}";
    }

    //  getting the list of members to add to the certain chat
    @RequestMapping(value = "/{chatId}/addMembers", method = RequestMethod.GET)
    public String getListOfNewMembersToChat(@PathVariable("chatId") Long chatId, Model model) {
        Chat currentChat = messageService.findOneChat(chatId);
        Set<User> chatMembers = currentChat.getMembers();
        User currentUser = userService.getCurrentUser();
        List<UserGroup> ug = userService.getUserGroups(currentUser);
        List<User> membersToAdd = new LinkedList<>();
        List<User> users;
        if (currentUser.getRoles().get(0).getRoleName().equals(RoleEnum.ADMIN.getCode())) { // TODO refactor To Multi roles
            users =  userService.findAll();
        } else {
            users = userService.getGroupsUsersWithoutCurrent( ug.toArray(new UserGroup[ug.size()]));
        }
        for (User u : users) {
            if (!chatMembers.contains(u)) {
                membersToAdd.add(u);
            }
        }
        model.addAttribute("chat", currentChat);
        model.addAttribute("membersToAdd", membersToAdd);
        model.addAttribute("newMembers", new HashSet<User>());
        model.addAttribute("status", "add");
        model.addAttribute("unreadMessages", messageService.countUnreadChats());
        return "home/messages/editChat";
    }

    //  adding new members to certain chat
    @RequestMapping(value = "/{chatId}/addMembers", method = RequestMethod.POST)
    public String addNewMembersToChat(@PathVariable("chatId") Long chatId,
                                      Model model,
                                      @RequestParam(value = "users" , required = false) long[] users,
                                      Locale locale) {
        if (users == null) {
            return "redirect:/home/messages/{chatId}";
        }
        Chat chat = messageService.findOneChat(chatId);
        HashSet<User> newMembers = new HashSet<>();
        for (Long userId : users) {
            newMembers.add(userService.findOne(userId));
        }
        if (!newMembers.isEmpty()) {
            User inviter = userService.getCurrentUser();
            messageService.addNewMembersToChat(newMembers, chat);
            messageService.sendMessage(inviter, messageService.getAddingNewMemberMessage(inviter, newMembers, chat, locale));
            model.addAttribute("chatId", chatId);
            return "redirect:/home/messages/{chatId}";
        } else {
//            model.addAttribute("message", messages.getMessage("message.addingNewMemberToChatError", null, locale));
            return "redirect:/home/messages/{chatId}";
        }
    }

    //   get page to edit chat
    @RequestMapping(value = "/{chatId}/edit", method = RequestMethod.GET)
    public String getChatEditPage(@PathVariable("chatId") Long chatId, Model model) {
        Chat chat = messageService.findOneChat(chatId);
        User currentUser = userService.getCurrentUser();
        model.addAttribute("chat", chat);
        model.addAttribute("chatId", chatId);
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("status", "edit");
        model.addAttribute("unreadMessages", messageService.countUnreadChats());
        return "home/messages/editChat";
    }

    //  change chat title
    @RequestMapping(value = "/{chatId}/changeTitle", method = RequestMethod.POST)
    public String changeTitleOfChat(@PathVariable("chatId") Long chatId, Chat chat, Model model) {
        if (!chat.getChatTitle().equals("") && !messageService.findOneChat(chatId).getChatTitle().equals(chat.getChatTitle())) {
            User changer = userService.getCurrentUser();
            messageService.sendMessage(changer, messageService.getChangingTitleMessage(changer, chat, new Locale("UK")));
            messageService.saveChat(chat);
        }
        model.addAttribute("chatId", chatId);
        return "redirect:/home/messages/{chatId}";
    }

//    delete member from chat
    @RequestMapping(value = "/{chatId}/deleteMember/{userId}", method = RequestMethod.GET)
    public String deleteMembersFromChat(@PathVariable("chatId") Long chatId, @PathVariable("userId") Long userId) {
        messageService.deleteMemberFromChat(userService.findOne(userId), messageService.findOneChat(chatId));
        return "redirect:/home/messages/{chatId}/edit";
    }

//    delete chat
    @RequestMapping(value = "/{chatId}/deleteChat", method = RequestMethod.GET)
    public String deleteChat(@PathVariable("chatId") Long chatId, Model model) {
        Chat chat = messageService.findOneChat(chatId);
        User currentUser = userService.getCurrentUser();
        if (chat.getOwner().equals(currentUser) || currentUser.getRoles().get(0).getRoleName().equals(RoleEnum.ADMIN.getCode())) { // TODO refactor To Multi roles
            messageService.deleteChat(chatId);
            model.addAttribute("message", "Бесіду видалено");
            return "redirect:/home/messages";
        }
        model.addAttribute("message", "permission denied");
        return "redirect:/home/messages";
    }

    //    leave chat
    @RequestMapping(value = "/{chatId}/leaveChat", method = RequestMethod.GET)
    public String leaveChat(@PathVariable("chatId") Long chatId) {
        messageService.deleteMemberFromChat(userService.getCurrentUser(), messageService.findOneChat(chatId));
        return "redirect:/home/messages";
    }

    //   get to the chat to post message to one user
    @RequestMapping(value = "/postMessage/{userId}", method = RequestMethod.GET)
    public String getFormForNewMessage(@PathVariable("userId") Long userId, Model model) {
        User currentUser = userService.getCurrentUser();
        if (currentUser.getUserId().equals(userId)) {
            return "redirect:/home/messages";
        }
        Chat currentChat = messageService.findDialog(userId);
        if (currentChat == null) {
            currentChat = messageService.saveDialog(new User[]{currentUser, userService.findOne(userId)});
        }
        model.addAttribute("chatId", currentChat.getChatId());
        return "redirect:/home/messages/{chatId}";
    }
}
