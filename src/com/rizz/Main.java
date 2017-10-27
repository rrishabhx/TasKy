package com.rizz;

import java.io.*;
import java.util.InputMismatchException;
import java.util.LinkedList;
import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        System.out.println("*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*");
        System.out.println("*-*-*-*-* WELCOME TO TasKy *-*-*-*-*");
        System.out.println("*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*\n");

        LinkedList<Task> tasksList = new LinkedList<>();

        //Reading Data from stored task file
        try (BufferedReader taskFile = new BufferedReader(new FileReader(".storedTasks.txt"))) {
            String input;
            while ((input = taskFile.readLine()) != null) {
                String[] data = input.split("::::");
                int imp = Integer.parseInt(data[1]);
                boolean taskStatus = Boolean.parseBoolean(data[2]);
                tasksList.add(new Task(data[0], imp, taskStatus));
            }
        } catch (IOException e) {
            //Creating new file to store Tasks
            new File(".storedTasks.txt");
        }

        boolean flag = true;
        welcomeScreen();
        while (flag) {
            System.out.print("\nEnter choice: ");
            int input;
            try {
                input = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Sorry... Invalid input\n");
                input = 6;
            }
            scanner.nextLine();
            System.out.println();
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
                case 3: {
                    printAllTasks(tasksList);
                    System.out.print("\nChoose the task you completed from the above list: ");
                    int task2update = scanner.nextInt();
                    tasksList.get(task2update - 1).setDone(true);
                    break;
                }
                case 4: {
                    printAllTasks(tasksList);
                    System.out.print("\nRemoving task from your list...\nChoose the task no. from the above mentioned list: ");
                    int task2remove = scanner.nextInt();
                    tasksList.remove(task2remove - 1);
                    System.out.println("Task removed successfully from your list");
                    break;
                }
                case 5: {
                    welcomeScreen();
                    break;
                }
                case 0: {
                    flag = false;
                    System.out.println("\n*---------------------*");
                    System.out.println("*------ GoodBye ------*");
                    System.out.println("*---------------------*\n");
                    updateFile(tasksList);
                }

            }
        }
    }

    private static void welcomeScreen() {
        System.out.println("Please enter your choice:- ");
        System.out.println("\t1. To view your current tasks.");
        System.out.println("\t2. To add new task.");
        System.out.println("\t3. To mark task as complete.");
        System.out.println("\t4. To delete a task.");
        System.out.println("\t5. To view menu.");
        System.out.println("\t0. To quit.");
    }

    private static void printAllTasks(LinkedList<Task> tasks) {
        for (int i = 0; i < tasks.size(); i++) {
            System.out.printf("[%-5s] {%-5s} ==> %3d. %s\n"
                    , printStars(tasks.get(i).getPriority()), tasks.get(i).isDone(), (i + 1), tasks.get(i).getName());
        }
    }

    private static Task addNewTask() throws IOException {
        System.out.print("What's the task?\n--> ");
        String taskName = scanner.nextLine();
        while (true) {
            try {
                System.out.print("How important is it? \nRate from 1(Not that important) to 5(Highly critical job)\n--> ");
                int important = scanner.nextInt();
                addToFile(new Task(taskName, important));
                return new Task(taskName, important);
            } catch (InputMismatchException e) {
                System.out.println("\nSorry!! Invalid input... \nNumbers from 1 to 5 are valid only\n");
                scanner.nextLine();
            }
        }

    }

    private static void addToFile(Task task) throws IOException {
        //Appending old tasks in the stored file
        try (BufferedWriter taskFile = new BufferedWriter(new FileWriter(".storedTasks.txt", true))) {
            taskFile.write(task.getName() + "::::" + task.getPriority() + "::::" + task.isDone());
            taskFile.newLine();
        }
    }

    private static void updateFile(LinkedList<Task> tasks) throws IOException {
        try (BufferedWriter taskFile = new BufferedWriter(new FileWriter(".storedTasks.txt"))) {
            for(Task t : tasks) {
                taskFile.write(t.getName() + "::::" + t.getPriority() + "::::" + t.isDone());
                taskFile.newLine();
            }
        }
    }

    private static String printStars(int n) {
        StringBuilder stars = new StringBuilder();
        for (int i = 0; i < n; i++) {
            stars.append("*");
        }
        return stars.toString();
    }
}
