package com.springdemo.mvc.controller;

import com.springdemo.mvc.model.ToDoList;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.atomic.AtomicLong;

@Controller
public class TodoController {

    private static final AtomicLong ToDoListIdGenerator = new AtomicLong(0);
    private static final ConcurrentSkipListMap<Long, ToDoList> ToDoListRepository = new ConcurrentSkipListMap<Long, ToDoList>();

    @RequestMapping(value = "/ToDoList", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody
    List<ToDoList> list() {
        return new ArrayList<ToDoList>(ToDoListRepository.values());
    }

    @RequestMapping(value = "/ToDoList/{id}", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody
    ToDoList getById(@PathVariable long id) {
        return ToDoListRepository.get(id);
    }

    @RequestMapping(value = "/ToDoList", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void create(@RequestBody ToDoList ToDoList) {
        long id = ToDoListIdGenerator.incrementAndGet();
        ToDoList.setId(id);
        ToDoListRepository.put(id, ToDoList);
    }

    @RequestMapping(value = "/ToDoList/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long id) {
        ToDoListRepository.remove(id);
    }

}