package ua.peresvit.sn.domain.entity;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @SerializedName("text")
    private String name;
    @SerializedName("start_date")
    private Date start;
    @SerializedName("end_date")
    private Date finish;
    private Date created;
    private String eventUrl;

    @SerializedName("description")
    private String description;
    @SerializedName("place")
    private String place;
    @SerializedName("connectall")
    @Column(columnDefinition = "boolean default true")
    private boolean connectAll = true;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinTable(name = "events_users", joinColumns = {
            @JoinColumn(name = "event_id", nullable = false, updatable = false) },
            inverseJoinColumns = { @JoinColumn(name = "user_id",
                    nullable = false, updatable = false) })
    private Set<User> users = new HashSet<User>();

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinTable(name = "events_groups", joinColumns = {
            @JoinColumn(name = "event_id", nullable = false, updatable = false) },
            inverseJoinColumns = { @JoinColumn(name = "group_id",
                    nullable = false, updatable = false) })
    private Set<UserGroup> groups = new HashSet<UserGroup>();

    @SerializedName("eventtype")
    private EventType eventType;

    @Override
    public String toString() {
        String startDate = (new SimpleDateFormat("dd.MM.yyyy")).format(start);
        String finishDate = (new SimpleDateFormat("dd.MM.yyyy")).format(finish);
        return (startDate.equals(finishDate)) ? "" + startDate + " " + name : "" + startDate + " - " + finishDate + " "  + name;
    }

    public boolean isAssigned(User u){
        return users.contains(u);
    }

    public Set<User> getUserSet() {
        Set<User> res = new HashSet<User>();

        res.addAll(users);
        for (UserGroup ug: groups) {
            res.addAll(ug.getUsers());
        }
        return res;
    }
}