package com.mycorp.taskmanagementservice.controller;

import com.mycorp.taskmanagementservice.enums.Priority;
import com.mycorp.taskmanagementservice.enums.Status;
import com.mycorp.taskmanagementservice.model.Task;
import com.mycorp.taskmanagementservice.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/{taskId}")
    public Task getTaskById(@PathVariable("taskId") long id) {
        return taskService.getTaskById(id);
    }

    @GetMapping("/status/{status}")
    public List<Task> findTasksByStatus(@PathVariable("status") Status status) {
        return taskService.findTasksByStatus(status);
    }

    @GetMapping
    public List<Task> findAllTasks(@RequestParam(value="status", required = false) Optional<Status> status,
                                   @RequestParam(value="priority", required = false) Optional<Priority> priority) {
        if(status.isPresent() && priority.isPresent()) {
            return taskService.findAllTasksByStatusAndPriority(status.get(), priority.get());
        }
        else if(priority.isPresent()) {
            return taskService.findAllTasksByPriority(priority.get());
        }
        else {
            return taskService.findAllTasks();
        }
    }

    @PostMapping
    public Task createTask(@RequestBody Task task) {
        return taskService.createTask(task);
    }

    @PutMapping("/{taskId}")
    public Task updateTask(@PathVariable("taskId") long id, @RequestBody Task task) {
        return taskService.updateTask(id, task);
    }

    @DeleteMapping("/{taskId}")
    public void deleteTask(@PathVariable("taskId") long id, Task task){
        taskService.deleteTask(id, task);
    }

}
