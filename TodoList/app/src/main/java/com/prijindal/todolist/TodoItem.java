package com.prijindal.todolist;

/**
 * Created by Priyanshu on 12/08/15.
 */
public class TodoItem {
    private String task;
    private Boolean status;// true if completed;

    public TodoItem(String task) {
        this(task, false);
    }

    public TodoItem(String task, Boolean status) {
        this.task = task;
        this.status = status;
    }

    public void setTask(String task) {
        this.task = task;
    }
    public String getTask() {
        return task;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
    public Boolean getStatus() {
        return status;
    }
}
