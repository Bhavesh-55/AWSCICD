package com.example.Hello.controllers;

import com.example.Hello.services.ThreadService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/thread/tasks")
public class ThreadController {

    private final ThreadService threadService;

    public ThreadController(ThreadService threadService) {
        this.threadService = threadService;
    }

    @GetMapping("/process/{id}")
    public String processTask(@PathVariable String id) throws InterruptedException {
        threadService.processTask(id);
        return "Task " + id + " is being processed asynchronously";
    }
}
