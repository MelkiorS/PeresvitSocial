package ua.peresvit.sn.service;



import ua.peresvit.sn.domain.entity.Event;
import ua.peresvit.sn.domain.entity.User;

import java.util.Date;
import java.util.List;


public interface EventService {

    List<Event> findAll();
    Event create(Event e);
    Event delete(Event e);
    Event update(Event e);
    Event findById(long id);

    List<Event> findClosest(Date date, int count);
    List<Event> findClosestByUser(Date date, User u, int count);
    List<Event> findClosestByCurrentUser(Date date, int count);
    Event findNext(Date date);
    Event findNextByUser(Date date, User u);
    Event findNextByCurrentUser(Date date);
    List<Event> getPeriod(Date start, Date finish);

    boolean isAssignedToMe(Event e);
    boolean assignToMe(Event e);

}
