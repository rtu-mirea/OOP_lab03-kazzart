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
                    if (currentUser.access == 0) {
                        outputMenu1_1();
                        int comm1 = -1;
                        while (comm1 != 0) {
                            System.out.print("Choose variant: ");
                            comm1 = in.nextInt();
                            switch (comm1) {
                                case 1:
                                    vote();
                                    break;
                                case 2:
                                    getResults();
                                    break;
                                case 0:
                                    System.out.println("Exiting the account");
                                    break;
                                default:
                                    System.out.println("Entered wrong command");
                                    break;
                            }
                        }
                    } else {
                        outputMenu1_2();
                        int comm1 = -1;
                        while (comm1 != 0) {
                            System.out.print("Choose variant: ");
                            comm1 = in.nextInt();
                            switch (comm1) {
                                case 1:
                                    addVoting();
                                    break;
                                case 2:
                                    addNewCandidate();
                                    break;
                                case 3:
                                    getResults();
                                    break;
                                case 0:
                                    System.out.println("Exiting the account");
                                    break;
                                default:
                                    System.out.println("Entered wrong command");
                                    break;
                            }
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

    private static void addVoting() {
        Scanner in = new Scanner(System.in);
        System.out.print("Enter electing title: ");
        String title = in.nextLine();
        currentVoting = new Voting(title, new ArrayList<>());
    }

    private static void addNewCandidate() {
        if (currentVoting == null) {
            Scanner in = new Scanner(System.in);
            System.out.print("Enter candidate's name: ");
            String name = in.nextLine();
            if (!currentVoting.candidates.isEmpty()) {
                for (Candidate candidate : currentVoting.candidates) {
                    if (candidate.getName().equals(name)) {
                        System.out.println("Candidate is already on the list");
                        return;
                    }
                }
            }
            currentVoting.candidates.add(new Candidate(name));
        }
    }

    private static void vote() {
        Scanner in = new Scanner(System.in);
        if (currentUser.lastElection != null && currentUser.lastElection != currentVoting) {

            for (int i = 0; i < currentVoting.candidates.size(); i++) {
                System.out.println(Integer.toString(i+1) + ". " + currentVoting.candidates.get(i).getName());
            }
            System.out.println("0. Vote for none");
            int choiceDone = -1;
            while (choiceDone >= 0 && choiceDone <= currentVoting.candidates.size()) {
                System.out.print("Choose the candidate from the list: ");
                choiceDone = in.nextInt();
                if(choiceDone >= 0 && choiceDone <= currentVoting.candidates.size()){
                    if (choiceDone == 0) {
                        currentUser.lastElection = currentVoting;
                    } else {
                        Candidate votedCandidate = currentVoting.candidates.get(choiceDone - 1);
                        votedCandidate.addVoice();
                        currentVoting.candidates.set(choiceDone - 1, votedCandidate);
                    }
                }else{
                    System.out.println("Wrong input!");
                    choiceDone = -1;
                }
            }
        }
    }

    private static void getResults() {
        Scanner in = new Scanner(System.in);
        if (currentVoting != null && !currentVoting.candidates.isEmpty()) {
            for (int i = 0; i < currentVoting.candidates.size(); i++) {
                System.out.println(Integer.toString(i+1) + ". " + currentVoting.candidates.get(i).getName() + ": " +
                        currentVoting.candidates.get(i).getVoices());
            }
        }
    }

    private static void outputMenu0() {
        System.out.println("1. Login");
        System.out.println("2. Registration");
        System.out.println("0. Exit");
    }

    private static void outputMenu1_1() {
        System.out.println("1. Vote");
        System.out.println("2. Election results");
        System.out.println("0. Exit account");
    }

    private static void outputMenu1_2() {
        System.out.println("1. Add new candidate in the list");
        System.out.println("2. Hold new elections with candidates list");
        System.out.println("3. Election results");
        System.out.println("0. Exit account");
    }
}
