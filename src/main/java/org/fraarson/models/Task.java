package org.fraarson.models;

import java.time.LocalDate;

public class Task {

    private long id;

    private String taskName;

    private String content;

    private LocalDate creationDate;

    private LocalDate deadlineDate;

    private LocalDate executedDate;

    private String executor_name;

    private String taskgiver_name;

    public void setId(long id) {
        this.id = id;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public void setDeadlineDate(LocalDate deadlineDate) {
        this.deadlineDate = deadlineDate;
    }

    public void setExecutedDate(LocalDate executedDate) {
        this.executedDate = executedDate;
    }

    public void setExecutor_name(String executor_name) {
        this.executor_name = executor_name;
    }

    public void setTaskgiver_name(String taskgiver_name) {
        this.taskgiver_name = taskgiver_name;
    }

    public long getId() {
        return id;
    }

    public String getTaskName() {
        return taskName;
    }

    public String getContent() {
        return content;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public LocalDate getDeadlineDate() {
        return deadlineDate;
    }

    public LocalDate getExecutedDate() {
        return executedDate;
    }

    public String getExecutor_name() {
        return executor_name;
    }

    public String getTaskgiver_name() {
        return taskgiver_name;
    }
}
