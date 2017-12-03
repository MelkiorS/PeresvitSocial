package ua.peresvit.sn.domain.dao;


import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ua.peresvit.sn.domain.entity.Event;

import java.util.Date;
import java.util.List;


public interface EventRepository extends JpaRepository<Event, Long>  {

    @Query("Select e from Event e where start >= :start order by start asc")
    List<Event> getClosest(@Param("start") Date start, Pageable pageable);

    @Query("Select e from Event e JOIN e.users ue where e.start >= :start and ue.id = :userId order by e.start asc")
    List<Event> getClosestByUser(@Param("start") Date start, @Param("userId") long userId, Pageable pageable);

    @Query("Select e from Event e where start >= :start and start < :finish order by start asc")
    List<Event> getPeriod(@Param("start") Date start, @Param("finish") Date finish);

}