package com.mycorp.taskmanagementservice.service;

import com.mycorp.taskmanagementservice.Exceptions.TaskException;
import com.mycorp.taskmanagementservice.enums.Priority;
import com.mycorp.taskmanagementservice.enums.Status;
import com.mycorp.taskmanagementservice.model.Task;
import com.mycorp.taskmanagementservice.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {

    private TaskRepository taskRepository;

    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public Task getTaskById(long id) {
        return taskRepository.findById(id).orElseThrow(()->new TaskException("Unable to get the task, it does not exist in database!"));
    }

    @Override
    public List<Task> findTasksByStatus(Status status) {
        return taskRepository.findTasksByStatus(status).orElseThrow(()->new TaskException("There are no tasks associated with that status!"));
    }

    @Override
    public List<Task> findAllTasks() {
        return taskRepository.findAll();
    }

    @Override
    public List<Task> findAllTasksByStatusAndPriority(Status status, Priority priority) {
        return taskRepository.findAllTasksByStatusAndPriority(status, priority).orElseThrow(()->new TaskException("No associated tasks with given status and priority."));
    }

    @Override
    public List<Task> findAllTasksByPriority(Priority priority) {
        return taskRepository.findAllTasksByPriority(priority).orElseThrow(()->new TaskException("No tasks found for given priority."));
    }

    @Override
    public Task createTask(Task task) {
        taskRepository.findById(task.getId()).ifPresent(x->{throw new TaskException("task already exists");});
        return taskRepository.save(task);
    }

    @Override
    public Task updateTask(Long id, Task task) {
        taskRepository.findById(id).orElseThrow(()->new TaskException("Unable to update the task bc it does not exist"));
        return taskRepository.save(task);
    }

    @Override
    public void deleteTask(long id, Task task) {
        taskRepository.findById(task.getId()).orElseThrow(()->new TaskException("Unable to delete task bc it does not exist"));
        taskRepository.delete(task);
    }
}
