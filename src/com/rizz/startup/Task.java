package com.rizz.startup;

import com.rizz.util.ConsoleColors;

public class Task {
    private String name;
    private int priority;
    private boolean isDone;


    public Task(String name, int priority) {
        this.name = name;
        if (priority >= 1 && priority <= 5) {
            this.priority = priority;
        } else {
            colorPrintln("Invalid Priority\nValid Entries : 1 to 5", ConsoleColors.RED);
        }
        this.isDone = false;

    }

    public Task(String name, int priority, boolean isDone) {
        this.name = name;
        if (priority >= 1 && priority <= 5) {
            this.priority = priority;
        } else {
            colorPrintln("Invalid Priority\nValid Entries : 1 to 5", ConsoleColors.RED);
        }
        this.isDone = isDone;
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
        if (priority >= 1 && priority <= 5) {
            this.priority = priority;
        } else {
            colorPrintln("Invalid Severity\nValid Entries : 1 to 5", ConsoleColors.RED);
        }
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }

    private void colorPrintln(Object msg, String color) {
        System.out.println(color + msg + ConsoleColors.RESET);
    }
}