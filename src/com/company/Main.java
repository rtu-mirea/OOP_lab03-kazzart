package com.company;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static List<User> users = new ArrayList<>();
    private static Voting currentVoting;
    private static User currentUser;
    public static void main(String[] args) {
        int comm0 = -1;
        Scanner in = new Scanner(System.in);
        outputMenu0();
        while (comm0 != 0) {
            System.out.print("Choose variant: ");
            comm0 = in.nextInt();
            switch (comm0) {
                case 1:
                    findUserProcess();
                    int comm1 = -1;
                    while (comm1 != 0){
                        System.out.print("Choose variant: ");
                        comm1 = in.nextInt();
                        switch (comm1) {
                            case 1:
                                break;
                            case 2:
                        }
                    }
                    break;
                case 2:
                    addUserProcess();
                    outputMenu0();
                    break;
                case 0:
                    System.out.println("Ending the program...");
                    break;
                default:
                    System.out.println("Entered wrong command");
                    break;
            }
        }
    }

    private static User addUser(String name, String login, String password, String role) throws Exception {
        if (!users.isEmpty()) {
            for (User user: users) {
                if (user.login.equals(login)) {
                    System.out.println("0");
                    throw new Exception("Login already exists");
                }
            }
        }

        if (role.equals("admin")) {
            Admin user = new Admin(name, login, password);
            users.add(user);
            return user;
        } else if (role.equals("elector")) {
            Elector user = new Elector(name, login, password);
            users.add(user);
            return user;
        }
        throw new Exception("Wrong role!");
    }

    private static void addUserProcess() {
        boolean done = false;
        while (!done) {
            Scanner in = new Scanner(System.in);
            System.out.print("Enter your name: ");
            String name = in.nextLine();
            System.out.print("Enter your login: ");
            String login = in.nextLine();
            System.out.print("Enter your password: ");
            String password1 = in.nextLine();
            System.out.print("Enter your password again: ");
            String password2 = in.nextLine();
            if (password1.equals(password2)) {
                int roleN = 0;
                System.out.println("1. Admin");
                System.out.println("2. Elector");
                while (roleN < 1) {
                    System.out.print("Choose your role: ");
                    roleN = in.nextInt();
                    switch (roleN) {
                        case 1:
                            try {
                                addUser(name, login, password1, "admin");
                                System.out.println("New admin registered");
                                done = true;
                            } catch (Exception e) {
                                System.out.println(e.getMessage());
                            }
                            break;
                        case 2:
                            try {
                                addUser(name, login, password1, "elector");
                                System.out.println("New elector registered");
                                done = true;
                            } catch (Exception e) {
                                System.out.println(e.getMessage());
                            }
                            break;
                        default:
                            System.out.println("Entered wrong command");
                            break;
                    }
                }
            } else {
                System.out.println("Passwords are different");
            }
        }
    }

    private static User findUser(String login, String password) throws Exception {
        if (!users.isEmpty()) {
            for (User user: users) {
                if (user.login.equals(login)) {
                    if (user.password.equals(password)) {
                        return user;
                    } else {
                        throw new Exception("Wrong password!");
                    }
                }
            }
        }
        throw new Exception("Login not found!");
    }

    private static void findUserProcess() {
        Scanner in = new Scanner(System.in);
        System.out.print("Enter your login: ");
        String login = in.nextLine();
        System.out.print("Enter your password: ");
        String password1 = in.nextLine();
        try {
            currentUser = findUser(login, password1);
            if (currentUser.access == 1) {
                System.out.println("Logined as admin - " + currentUser.login);
            } else {
                System.out.println("Logined as elector - " + currentUser.login);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void outputMenu0() {
        System.out.println("1. Login");
        System.out.println("2. Registration");
        System.out.println("0. Exit");
    }
}
