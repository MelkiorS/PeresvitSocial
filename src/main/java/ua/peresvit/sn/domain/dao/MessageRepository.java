package ua.peresvit.sn.domain.dao;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ua.peresvit.sn.domain.entity.Message;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    <S extends Message> S save(S arg0);

    Message findOne(Long arg0);

    @Query("select m from Message m where m.chat.chatId = :chatId order by m.createdAt")
    List<Message> findByChatOrderByCreatedAt(@Param("chatId") Long chatId);
}
