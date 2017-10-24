package com.rizz;

public class Task {
    private String name;
    private int severity;

    public Task(String name, int severity) {
        this.name = name;
        if (severity >= 1 && severity <=5) {
            this.severity = severity;
        }else {
            System.out.println("Invalid Severity\nValid Entries : 1 to 5");
        }
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSeverity() {
        return severity;
    }

    public void setSeverity(int severity) {
        this.severity = severity;
        if (severity >= 1 && severity <=5) {
            this.severity = severity;
        }else {
            System.out.println("Invalid Severity\nValid Entries : 1 to 5");
        }
    }
}