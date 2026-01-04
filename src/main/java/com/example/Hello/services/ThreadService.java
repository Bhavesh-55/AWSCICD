package com.example.Hello.services;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.CompletableFuture;

@Service
public class ThreadService {


    @Async
    public CompletableFuture<String> processTask(String taskId) throws InterruptedException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");

        String startTime = LocalDateTime.now().format(formatter);
        System.out.println("Started task " + taskId + " | Thread: " + Thread.currentThread().getName() +
                " | Start Time: " + startTime);

        Thread.sleep(60000); // simulate heavy work

        String endTime = LocalDateTime.now().format(formatter);
        System.out.println("Finished task " + taskId + " | Thread: " + Thread.currentThread().getName() +
                " | End Time: " + endTime);

        return CompletableFuture.completedFuture("Task " + taskId + " completed (Start: " + startTime + ", End: " + endTime + ")");
    }


}

