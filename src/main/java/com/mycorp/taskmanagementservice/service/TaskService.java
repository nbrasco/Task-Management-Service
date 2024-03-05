package com.mycorp.taskmanagementservice.service;

import com.mycorp.taskmanagementservice.enums.Priority;
import com.mycorp.taskmanagementservice.enums.Status;
import com.mycorp.taskmanagementservice.model.Task;

import java.util.List;

public interface TaskService {

    public Task getTaskById(long id);

    public List<Task> findTasksByStatus(Status status);

    public List<Task> findAllTasks();

    public List<Task> findAllTasksByStatusAndPriority(Status status, Priority priority);

    public List<Task> findAllTasksByPriority(Priority priority);

    public Task createTask(Task task);

    public Task updateTask(Long id, Task task);

    public void deleteTask(long id, Task task);

}
