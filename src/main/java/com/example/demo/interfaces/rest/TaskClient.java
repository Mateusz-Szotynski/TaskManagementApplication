package com.example.demo.interfaces.rest;

import com.example.demo.domain.Priority;
import com.example.demo.domain.Task;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient()
public interface TaskClient {
    @GetMapping("/findTasks")
    List<Task> getTasks(@RequestParam(required = false) String title,
                        @RequestParam(required = false) Priority priority);
}
