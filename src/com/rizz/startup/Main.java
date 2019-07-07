package com.rizz.startup;

import com.rizz.util.ConsoleColors;

import java.io.*;
import java.util.InputMismatchException;
import java.util.LinkedList;
import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static LinkedList<Task> tasksList = new LinkedList<>();
    private static String TASKY_HOME = System.getProperty("user.home") + "/.tasky";
    private static String STORED_TASKS = TASKY_HOME + "/storedTasks.txt";

    public static void main(String[] args) {
        printWelcome();
        loadSavedTasks();
        showTaskyPrompt();
    }

    private static void showTaskyPrompt() {
        boolean flag = true;
        choiceScreen();
        while (flag) {
            colorPrint("\nEnter choice: ", ConsoleColors.GREEN_BOLD);
            int input;
            try {
                input = scanner.nextInt();
            } catch (InputMismatchException e) {
                colorPrintln("Sorry... Invalid input\n", ConsoleColors.RED);
                input = 6;
            }
            scanner.nextLine();
            System.out.println();
            switch (input) {
                case 1: {
                    if (tasksList.size() == 0) {
                        colorPrintln("Well... There's not a single task in your agenda.\nPretty cool -_- ", ConsoleColors.PURPLE);
                    } else {
                        colorPrintln("Tasks List: ", ConsoleColors.RED_BRIGHT);
                        printAllTasks(tasksList);
                    }
                    break;
                }
                case 2: {
                    colorPrintln("Adding new task to your list...", ConsoleColors.BLUE);
                    Task newT = null;
                    try {
                        newT = addNewTask();
                    } catch (IOException e) {
                        colorPrint("xxx Exception occurred while adding new task. Try restarting the app xxx", ConsoleColors.RED);
                    }
                    tasksList.add(newT);
                    break;
                }
                case 3: {
                    printAllTasks(tasksList);
                    colorPrint("\nChoose the task you completed from the above list: ", ConsoleColors.BLUE_BOLD);
                    int task2update = scanner.nextInt();
                    tasksList.get(task2update - 1).setDone(true);
                    break;
                }
                case 4: {
                    printAllTasks(tasksList);
                    colorPrint("\nRemoving task from your list...\nChoose the task no. from the above mentioned list: ", ConsoleColors.BLUE);
                    int task2remove = scanner.nextInt();
                    tasksList.remove(task2remove - 1);
                    colorPrintln("Task removed successfully from your list", ConsoleColors.BLUE);
                    break;
                }
                case 5: {
                    choiceScreen();
                    break;
                }
                case 0: {
                    flag = false;
                    printGoodBye();

                    try {
                        updateFile(tasksList);
                    } catch (IOException e) {
                        colorPrint("xxx Save File corrupted. Try restarting the app xxx", ConsoleColors.RED);
                    }
                    break;
                }
                default: {
                    colorPrint("xxx INVALID INPUT xxx", ConsoleColors.RED);
                }

            }
        }
    }

    private static void loadSavedTasks() {
        File taskyHome = new File(TASKY_HOME);
        if (!taskyHome.exists()) {
            taskyHome.mkdir();
        }

        //Reading Data from stored task file
        try (BufferedReader taskFile = new BufferedReader(new FileReader(STORED_TASKS))) {
            String input;
            while ((input = taskFile.readLine()) != null) {
                String[] data = input.split("::::");
                int imp = Integer.parseInt(data[1]);
                boolean taskStatus = Boolean.parseBoolean(data[2]);
                tasksList.add(new Task(data[0], imp, taskStatus));
            }
        } catch (IOException e) {
            //Creating new file to store Tasks
            new File(STORED_TASKS);
        }
    }

    private static void choiceScreen() {
        colorPrintln("Please enter your choice:- ", ConsoleColors.BLUE);
        colorPrintln("\t1. To view your current tasks.", ConsoleColors.BLUE);
        colorPrintln("\t2. To add new task.", ConsoleColors.BLUE);
        colorPrintln("\t3. To mark task as complete.", ConsoleColors.BLUE);
        colorPrintln("\t4. To delete a task.", ConsoleColors.BLUE);
        colorPrintln("\t5. To view menu.", ConsoleColors.BLUE);
        colorPrintln("\t0. To quit.", ConsoleColors.BLUE);
    }

    private static void printAllTasks(LinkedList<Task> tasks) {
        for (int i = 0; i < tasks.size(); i++) {
            System.out.printf("[%-5s] {%-5s} ==> %3d. %s\n"
                    , printStars(tasks.get(i).getPriority()), tasks.get(i).isDone(), (i + 1), tasks.get(i).getName());
        }
    }

    private static Task addNewTask() throws IOException {
        colorPrint("What's the task?\n--> ", ConsoleColors.BLUE);
        String taskName = scanner.nextLine();
        while (true) {
            try {
                colorPrint("How important is it? \nRate from 1(Not that important) to 5(Highly critical job)\n--> ", ConsoleColors.CYAN);
                int important = scanner.nextInt();
                Task newT = new Task(taskName, important);
                if (newT.getPriority() >= 1 && newT.getPriority() <= 5) {
                    addToFile(newT);
                    return newT;
                }
            } catch (InputMismatchException e) {
                colorPrintln("\nSorry!! Invalid input... \nNumbers from 1 to 5 are valid only\n", ConsoleColors.RED);
                scanner.nextLine();
            }
        }

    }

    private static void addToFile(Task task) throws IOException {
        //Appending old tasks in the stored file
        try (BufferedWriter taskFile = new BufferedWriter(new FileWriter(STORED_TASKS, true))) {
            taskFile.write(task.getName() + "::::" + task.getPriority() + "::::" + task.isDone());
            taskFile.newLine();
        }
    }

    private static void updateFile(LinkedList<Task> tasks) throws IOException {
        try (BufferedWriter taskFile = new BufferedWriter(new FileWriter(STORED_TASKS))) {
            for (Task t : tasks) {
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


    private static void printWelcome() {
        colorPrintln("╦ ╦┌─┐┬  ┌─┐┌─┐┌┬┐┌─┐  ┌┬┐┌─┐  ╔╦╗┌─┐┌─┐┬┌─┬ ┬\n" +
                "║║║├┤ │  │  │ ││││├┤    │ │ │   ║ ├─┤└─┐├┴┐└┬┘\n" +
                "╚╩╝└─┘┴─┘└─┘└─┘┴ ┴└─┘   ┴ └─┘   ╩ ┴ ┴└─┘┴ ┴ ┴ ", ConsoleColors.PURPLE_BOLD);
    }

    private static void printGoodBye() {
        colorPrint("★─▄█▀▀║░▄█▀▄║▄█▀▄║██▀▄║─★\n" +
                "★─██║▀█║██║█║██║█║██║█║─★\n" +
                "★─▀███▀║▀██▀║▀██▀║███▀║─★\n" +
                "★───────────────────────★\n" +
                "★───▐█▀▄─ ▀▄─▄▀ █▀▀──█───★\n" +
                "★───▐█▀▀▄ ──█── █▀▀──▀───★\n" +
                "★───▐█▄▄▀ ──▀── ▀▀▀──▄───★", ConsoleColors.CYAN);
    }

    private static void colorPrint(Object msg, String color) {
        System.out.print(color + msg + ConsoleColors.RESET);
    }

    private static void colorPrintln(Object msg, String color) {
        System.out.println(color + msg + ConsoleColors.RESET);
    }

}
