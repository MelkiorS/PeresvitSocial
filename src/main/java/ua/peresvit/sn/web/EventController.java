package ua.peresvit.sn.web;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.peresvit.sn.domain.entity.Event;
import ua.peresvit.sn.service.EventService;

import java.util.List;


@RestController
@CrossOrigin
@RequestMapping("/event")
public class EventController {

    @Autowired
    private EventService es;

    @RequestMapping(value="/", method = RequestMethod.POST)
    public ResponseEntity<Event> create(@RequestBody Event e) {
        Event newEvent = es.create(e);
        return new ResponseEntity<>(newEvent, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Event> delete(@PathVariable("id") long id){
        Event e = es.findById(id);
        if (e==null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        else
            return new ResponseEntity<>(es.delete(e), HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Event> update(@PathVariable("id") long id, @RequestBody Event e) {
        return new ResponseEntity<>(es.update(e), HttpStatus.OK);
    }

    @RequestMapping(value="/", method = RequestMethod.GET)
    public ResponseEntity<List<Event>> findAll() {
        return new ResponseEntity<>(es.findAll(), HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Event> get(@PathVariable("id") long id){
        Event ev = es.findById(id);
        if (ev==null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        else
            return new ResponseEntity<>(ev, HttpStatus.OK);
    }

}
