package ua.peresvit.sn.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.peresvit.sn.domain.dao.EventRepository;
import ua.peresvit.sn.domain.entity.Event;
import ua.peresvit.sn.domain.entity.User;
import ua.peresvit.sn.service.EventService;
import ua.peresvit.sn.service.UserService;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


class AttendMessage extends SimpleMailMessage{
    @Autowired
    private Environment env;

    public AttendMessage(Event ev, User u, String from) {
        super();
        this.setTo(u.getEmail());
        this.setSubject("Інформація о події");
        this.setText("Шановний(а) " + u.toString() +"!\n" +
                "Запрошуємо Вас о " + (new SimpleDateFormat("dd.MM.yyyy HH:mm")).format(ev.getStart()) + " на " + ev.getName() + " \"\n");
        this.setFrom(from);
    }
}

class CancelMessage extends SimpleMailMessage {
    @Autowired
    private Environment env;

    public CancelMessage(Event ev, User u, String from) {
        super();
        this.setTo(u.getEmail());
        this.setSubject("Подія відмінена");
        this.setText("Шановний(а) " + u.toString() +"!\n" +
                "Інформуємо Вас о відміні " + ev.getName() + " \"\n");
        this.setFrom(from);
    }
}

@Service
public class EventServiceImpl implements EventService {

    @Autowired
    private EventRepository dao;

    @Autowired
    private UserService us;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private Environment env;

    @Override
    public List<Event> findAll() {
        return dao.findAll();
    }

    private void inform(Event e) {

        if (e == null) return;

        Set<User> newUsers = e.getUserSet();
        Event persisted = findById(e.getId());
        Set<User> oldUsers = new HashSet<>();
        if (persisted!=null) oldUsers = persisted.getUserSet();

        Set<User> attendUsers = (HashSet)((HashSet)newUsers).clone();
        attendUsers.removeAll(oldUsers);
        Set<User> cancelUsers = (HashSet)((HashSet)oldUsers).clone();
        cancelUsers.removeAll(newUsers);

        for (User u: attendUsers) mailSender.send(new AttendMessage(e, u, env.getRequiredProperty("support.email")));
        for (User u: cancelUsers) mailSender.send(new CancelMessage(e, u, env.getRequiredProperty("support.email")));
    }

    @Override
    @Transactional
    public Event create(Event e) {
        inform(e);
        return dao.save(e);
    }

    @Override
    @Transactional
    public Event delete(Event e) {
        inform(e);
        dao.delete(e);
        return e;
    }

    @Override
    @Transactional
    public Event update(Event e) {
        inform(e);
        return dao.save(e);
    }

    @Override
    public Event findById(long id) {
        return dao.findOne(id);
    }

    @Override
    public List<Event> findClosest(Date date, int count) {
        Pageable pg = new PageRequest(0,count);
        return dao.getClosest(date, pg);
    }

    @Override
    public List<Event> findClosestByUser(Date date, User u, int count) {
        Pageable pg = new PageRequest(0,count);
        return dao.getClosestByUser(date, u.getUserId(), pg);
    }

    @Override
    public List<Event> findClosestByCurrentUser(Date date, int count) {
        return findClosestByUser(date, us.getCurrentUser(), count);
    }

    @Override
    public Event findNext(Date date) {
        List<Event> lst = findClosest(date, 1);
        return (lst.size()==0) ? null : lst.get(0);
    }

    @Override
    public Event findNextByUser(Date date, User u) {
        List<Event> lst = findClosestByUser(date, u, 1);
        return (lst.size()==0) ? null : lst.get(0);
    }

    @Override
    public Event findNextByCurrentUser(Date date) {
        return findNextByUser(date, us.getCurrentUser());
    }

    @Override
    public List<Event> getPeriod(Date start, Date finish) {
        LocalDateTime localFinish = finish.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        localFinish.plusDays(1);
        Date dateFinish = Date.from(localFinish.atZone(ZoneId.systemDefault()).toInstant());
        return dao.getPeriod(start, dateFinish);
       // return dao.getPeriod(start, DateUtils.addDays(finish, 1) );
    }

    @Override
    @Transactional
    public boolean isAssignedToMe(Event e) {
        Event ev = dao.findOne(e.getId());
        return ev.getUsers().contains(us.getCurrentUser());
    }

    @Override
    @Transactional
    public boolean assignToMe(Event e) {
        if (!isAssignedToMe(e)) {
            Event ev = dao.findOne(e.getId());
            ev.getUsers().add(us.getCurrentUser());
            try {
                dao.save(ev);
                return true;
            }
            catch (Exception excp) {
                return false;
            }
        }
        else {
            return true;
        }
    }
}
