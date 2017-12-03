package ua.peresvit.sn.web;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import ua.peresvit.sn.domain.entity.Event;
import ua.peresvit.sn.domain.entity.EventType;
import ua.peresvit.sn.domain.entity.User;
import ua.peresvit.sn.domain.entity.UserGroup;
import ua.peresvit.sn.service.EventService;
import ua.peresvit.sn.service.MessageService;
import ua.peresvit.sn.service.UserGroupService;
import ua.peresvit.sn.service.UserService;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

class UserClassAdapter<T> extends TypeAdapter<T> {
    @Override
    public void write(JsonWriter out, T value) throws IOException {
        if (value ==null)
            out.nullValue();
        else {
            long id = (value instanceof User) ? ((User)value).getUserId() : ((UserGroup)value).getId();
            out.value(id);
        }
    }

    @Override
    public T read(JsonReader in) throws IOException {
        return null;
    }
}

class EventClassAdapter extends TypeAdapter<Event> {

    private User user;

    public EventClassAdapter(User user) {
        this.user = user;
    }

    private void wrt(JsonWriter out, String name, String value) throws IOException {
        out.name(name);
        out.value(value);
    }

    @Override
    public void write(JsonWriter out, Event value) throws IOException {
        if (value !=null) {
            out.beginObject();
            wrt(out, "userId", new Long(value.getId()).toString());
            wrt(out, "text", value.getName().toString());
            wrt(out, "start_date", new SimpleDateFormat("yyyy-MM-dd hh:mm").format(value.getStart()));
            wrt(out, "end_date", new SimpleDateFormat("yyyy-MM-dd hh:mm").format(value.getFinish()));
            wrt(out, "description", value.getDescription());
            wrt(out, "assignedToMe", value.isAssigned(user) ? "true" : "false");
            wrt(out, "eventtype",  value.getEventType() == null ? EventType.defaultType().name() : value.getEventType().name());
            out.endObject();
        }
    }

    @Override
    public Event read(JsonReader in) throws IOException {
        return null;
    }
}

@Controller
public class MyEventsController {

    @Autowired
    private EventService es;

    @Autowired
    private UserService us;

    @Autowired
    private UserGroupService ugs;

    @Autowired
    private MessageService messageService;

    @RequestMapping(value="/panel/ourevents", method = RequestMethod.GET)
    public String getAllEvents(Model model){
        model.addAttribute("all", true);
        model.addAttribute("unreadMessages", messageService.countUnreadChats());
        return "panel/myevents";
    }

    @RequestMapping(value="/panel/myevents", method = RequestMethod.GET)
    public String getEvents(Model model){
        model.addAttribute("all", false);
        model.addAttribute("unreadMessages", messageService.countUnreadChats());
        return "panel/myevents";
    }

    @RequestMapping(value = "/panel/myeventsdata", method = RequestMethod.GET)
    public String getDateMyEvents(@RequestParam("dt") String date, @RequestParam("qty") int qty, Model model) throws ParseException {
        Date dt = (new SimpleDateFormat("MM/dd/yyyy")).parse(date);
        model.addAttribute("events", es.findClosestByCurrentUser(dt, qty));
        model.addAttribute("me", us.getCurrentUser());
        model.addAttribute("unreadMessages", messageService.countUnreadChats());
        return "panel/events_soon";
    }

    @RequestMapping(value = "/panel/myeventsdatajson", method = RequestMethod.GET, produces = {"application/json; charset=UTF-8"})
    @ResponseBody
    public String getDateMyEventsJson(@RequestParam("dt") String date, @RequestParam("qty") int qty, Model model) throws ParseException {
        Gson g = new GsonBuilder().registerTypeAdapter(Event.class, new EventClassAdapter(us.getCurrentUser()))
                .setDateFormat("MM/dd/yyyy HH:mm").create();
        Date dt = (new SimpleDateFormat("MM/dd/yyyy")).parse(date);
        String res = g.toJson(es.findClosestByCurrentUser(dt, qty));
        return res;
    }

    @RequestMapping(value = "/panel/eventsdatajson", method = RequestMethod.GET, produces = {"application/json; charset=UTF-8"})
    @ResponseBody
    public String getDateEventsJson(@RequestParam("dt") String date, @RequestParam("qty") int qty, Model model) throws ParseException {
        Gson g = new GsonBuilder().registerTypeAdapter(Event.class, new EventClassAdapter(us.getCurrentUser()))
                .setDateFormat("MM/dd/yyyy HH:mm").create();
        Date dt = (new SimpleDateFormat("MM/dd/yyyy")).parse(date);
        String res = g.toJson(es.findClosest(dt, qty));
        return res;
    }

    @RequestMapping(value = "/panel/eventsdata", method = RequestMethod.GET)
    public String getDateEvents(@RequestParam("dt") String date, @RequestParam("qty") int qty, Model model) throws ParseException {
        Date dt = (new SimpleDateFormat("MM/dd/yyyy")).parse(date);
        model.addAttribute("events", es.findClosest(dt, qty));
        model.addAttribute("me", us.getCurrentUser());
        model.addAttribute("unreadMessages", messageService.countUnreadChats());
        return "panel/events_soon";
    }

    @RequestMapping(value = "/panel/myeventsdatanext", method = RequestMethod.GET)
    public String getDateMyEventsNext(@RequestParam("dt") String date, Model model) throws ParseException {
        Date dt = (new SimpleDateFormat("MM/dd/yyyy")).parse(date);
        model.addAttribute("event", es.findNextByCurrentUser(dt));
        model.addAttribute("me", us.getCurrentUser());
        model.addAttribute("unreadMessages", messageService.countUnreadChats());
        return "panel/events_next";
    }

    @RequestMapping(value = "/panel/eventsdatanext", method = RequestMethod.GET)
    public String getDateEventsNext(@RequestParam("dt") String date, Model model) throws ParseException {
        Date dt = (new SimpleDateFormat("MM/dd/yyyy")).parse(date);
        model.addAttribute("event", es.findNext(dt));
        model.addAttribute("me", us.getCurrentUser());
        model.addAttribute("unreadMessages", messageService.countUnreadChats());
        return "panel/events_next";
    }

    @RequestMapping(value="/admin/myeventsperiod", method = RequestMethod.GET, produces = {"application/json; charset=UTF-8"})
    @ResponseBody
    public String getPeriodEvents(@RequestParam("start") String start, @RequestParam("finish") String finish, Model model)  throws ParseException {

        Gson g = new GsonBuilder().registerTypeAdapter(User.class, new UserClassAdapter<User>()).registerTypeAdapter(UserGroup.class, new UserClassAdapter<UserGroup>())
                .setDateFormat("MM/dd/yyyy HH:mm").create();
        Date dtStart = (new SimpleDateFormat("yyyyMMdd")).parse(start);
        Date dtFinish = (new SimpleDateFormat("yyyyMMdd")).parse(finish);

        String res = g.toJson(es.getPeriod(dtStart, dtFinish));
        return res;
    }

    @RequestMapping(value = "/panel/eventjson", method = RequestMethod.GET, produces = {"application/json; charset=UTF-8"})
    @ResponseBody
    public String getEventJson(@RequestParam("dt") String date, Model model) throws ParseException {
        Gson g = new GsonBuilder().registerTypeAdapter(Event.class, new EventClassAdapter(us.getCurrentUser()))
                .setDateFormat("MM/dd/yyyy HH:mm").create();
        Date dt = (new SimpleDateFormat("MM/dd/yyyy")).parse(date);
        String res = g.toJson(es.findNext(dt));
        return res;
    }

    @RequestMapping(value = "/admin/addEvent", method = RequestMethod.POST)
    @ResponseBody
//    public String updEvent(@RequestParam("start_date") String start, @RequestParam("end_date") String end, @RequestParam("text") String text, @RequestParam("!nativeeditor_status") String status) throws URISyntaxException {
    public String addEvent(@RequestParam("start_date") Long start, @RequestParam("end_date") Long end,
                           @RequestParam("place") String place,
                           @RequestParam("title") String title,
                           @RequestParam("description") String description,
                           @RequestParam("connectall") boolean connectall,
                           @RequestParam("eventtype") EventType eventType,
                           @RequestParam(value = "groups[]", required = false) String[] groups,
                           @RequestParam(value = "friends[]", required = false) String[] friends) throws URISyntaxException {
        Event ev = new Event();
        ev.setName(title);
        ev.setStart(new Date(start));
        ev.setFinish(new Date(end));

        ev.setPlace(place);
        ev.setDescription(description);
        ev.setConnectAll(connectall);
        ev.setEventType(eventType);

        ev.setUsers(us.getSetFromStringArray(friends));
        ev.setGroups(ugs.getSetFromStringArray(groups));

        return String.valueOf(es.create(ev));
    }

    @RequestMapping(value = "/admin/updEvent", method = RequestMethod.POST)
    @ResponseBody
    public String updEvent(@RequestParam("userId") long id, @RequestParam("start_date") Long start, @RequestParam("end_date") Long end,
                           @RequestParam("place") String place,
                           @RequestParam("title") String title,
                           @RequestParam("description") String description,
                           @RequestParam("connectall") boolean connectall,
                           @RequestParam("eventtype") EventType eventType,
                           @RequestParam(value = "groups[]", required = false) String[] groups,
                           @RequestParam(value = "friends[]", required = false) String[] friends) {
        Event ev = es.findById(id);
        ev.setName(title);
        ev.setStart(new Date(start));
        ev.setFinish(new Date(end));

        ev.setPlace(place);
        ev.setDescription(description);
        ev.setConnectAll(connectall);
        ev.setEventType(eventType);

        ev.setUsers(us.getSetFromStringArray(friends));
        ev.setGroups(ugs.getSetFromStringArray(groups));

        return String.valueOf(es.update(ev));
    }

    @RequestMapping(value = "/admin/delEvent", method = RequestMethod.POST)
    @ResponseBody
    public String delEvent(@RequestParam("userId") long id) {
        return String.valueOf(es.delete(es.findById(id)));
    }

    @RequestMapping(value = "/admin/event_edit", method = RequestMethod.GET)
    public String editEvents(Model model){
        model.addAttribute("friends", us.findAll());
        model.addAttribute("types", EventType.values());
        model.addAttribute("groups", ugs.findAll());
        model.addAttribute("me", us.getCurrentUser());
        model.addAttribute("unreadMessages", messageService.countUnreadChats());
        return "/admin/event_edit";
    }

}
