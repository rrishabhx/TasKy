package com.rizz;

public class Task {
    private String name;
    private int priority;
    private boolean isDone;



    public Task(String name, int priority) {
        this.name = name;
        if (priority >= 1 && priority <=5) {
            this.priority = priority;
        }else {
            System.out.println("Invalid Priority\nValid Entries : 1 to 5");
        }
        this.isDone = false;

    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
        if (priority >= 1 && priority <=5) {
            this.priority = priority;
        }else {
            System.out.println("Invalid Severity\nValid Entries : 1 to 5");
        }
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }
}