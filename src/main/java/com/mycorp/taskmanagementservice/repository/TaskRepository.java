package com.mycorp.taskmanagementservice.repository;

import com.mycorp.taskmanagementservice.enums.Priority;
import com.mycorp.taskmanagementservice.enums.Status;
import com.mycorp.taskmanagementservice.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    public Optional<List<Task>> findTasksByStatus(Status status);

    public Optional<List<Task>> findAllTasksByStatusAndPriority(Status status, Priority priority);

    public Optional<List<Task>> findAllTasksByPriority(Priority priority);

}
