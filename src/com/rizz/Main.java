package com.rizz;

import java.util.InputMismatchException;
import java.util.LinkedList;
import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws InputMismatchException {
        LinkedList<Task> tasksList = new LinkedList<>();
        boolean flag = true;
        welcomeScreen();
        while (flag) {
            System.out.print("\nEnter choice: ");
            int input = scanner.nextInt();
            scanner.nextLine();
            switch (input) {
                case 1: {
                    if (tasksList.size() == 0) {
                        System.out.println("Well... There's not a single task in your agenda.\nPretty cool -_- ");
                    } else {
                        System.out.println("Following tasks are due: ");
                        printAllTasks(tasksList);
                    }
                    break;
                }
                case 2: {
                    System.out.println("Adding new task to your list...");
                    Task newT = addNewTask();
                    tasksList.add(newT);
                    break;
                }
                case 4: {
                    System.out.println("Removing task from your list...\nChoose the task no. from the below mentioned list:");
                    printAllTasks(tasksList);
                    int task2remove = scanner.nextInt();
                    tasksList.remove(task2remove - 1);
                    System.out.println("Task removed successfully from your list");
                    break;
                }
                case 5: {
                    flag = false;
                    System.out.println("BYE-BYE");
                    break;
                }
                case 6: {
                    welcomeScreen();
                }
            }
        }
    }

    private static void welcomeScreen() {
        System.out.println("***** WELCOME TO TasKy *****");
        System.out.println("Please enter your choice:- ");
        System.out.println("\t1. To view your current tasks.");
        System.out.println("\t2. To add new task.");
        System.out.println("\t3. To mark task as complete.");
        System.out.println("\t4. To delete a task.");
        System.out.println("\t5. To quit.");
        System.out.println("\t6. To view menu.");
    }

    private static void printAllTasks(LinkedList<Task> tasks) {
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + ". " + tasks.get(i).getName() + " [" + tasks.get(i).getSeverity() + "]");
        }
    }

    private static Task addNewTask() {
        System.out.print("What's the task?\n--> ");
        String taskName = scanner.nextLine();
        System.out.print("How important is it? [ Rate from 1 to 5 ]\n--> ");
        int important = scanner.nextInt();
        return new Task(taskName, important);
    }

}