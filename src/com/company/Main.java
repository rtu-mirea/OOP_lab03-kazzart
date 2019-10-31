package com.company;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static List<User> users = new ArrayList<>();
    private static Voting currentVoting;
    private static User currentUser;
    public static void main(String[] args) {
        users.add(new Admin("admin", "admin", "admin"));
        int comm0 = -1;
        Scanner in = new Scanner(System.in);
        while (comm0 != 0) {
            outputMenu0();
            System.out.print("Choose variant: ");
            comm0 = in.nextInt();
            System.out.println();
            switch (comm0) {
                case 1:
                    findUserProcess();
                    if (currentUser != null && currentUser.access == 0) {
                        int comm1 = -1;
                        while (comm1 != 0) {
                            outputMenu1_1();
                            System.out.print("Choose variant: ");
                            comm1 = in.nextInt();
                            System.out.println();
                            switch (comm1) {
                                case 1:
                                    vote();
                                    break;
                                case 2:
                                    getResults();
                                    break;
                                case 0:
                                    System.out.println("Exiting the account");
                                    System.out.println();
                                    currentUser = null;
                                    break;
                                default:
                                    System.out.println("Entered wrong command");
                                    System.out.println();
                                    break;
                            }
                        }
                    } else if(currentUser != null && currentUser.access == 1) {
                        int comm1 = -1;
                        while (comm1 != 0) {
                            outputMenu1_2();
                            System.out.print("Choose variant: ");
                            comm1 = in.nextInt();
                            System.out.println();
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
                                    System.out.println();
                                    currentUser = null;
                                    break;
                                default:
                                    System.out.println("Entered wrong command");
                                    System.out.println();
                                    break;
                            }
                        }
                    }
                    break;
                case 2:
                    addUserProcess();
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
                try {
                    addUser(name, login, password1, "elector");
                    System.out.println("New elector registered");
                    System.out.println();
                    done = true;
                } catch (Exception e) {
                    System.out.println(e.getMessage());
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
        System.out.println();
        try {
            currentUser = findUser(login, password1);
            if (currentUser.access == 1) {
                System.out.println("Logined as admin - " + currentUser.login);
                System.out.println();
            } else {
                System.out.println("Logined as elector - " + currentUser.login);
                System.out.println();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static void addVoting() {
        Scanner in = new Scanner(System.in);
        System.out.print("Enter elections title: ");
        String title = in.nextLine();
        System.out.println();
        currentVoting = new Voting(title, new ArrayList<>());
    }

    private static void addNewCandidate() {
        if (currentVoting != null) {
            Scanner in = new Scanner(System.in);
            System.out.print("Enter candidate's name: ");
            String name = in.nextLine();
            System.out.println();
            if (!currentVoting.candidates.isEmpty()) {
                for (Candidate candidate : currentVoting.candidates) {
                    if (candidate.getName().equals(name)) {
                        System.out.println("Candidate is already on the list");
                        return;
                    }
                }
            }
            currentVoting.candidates.add(new Candidate(name));
        } else {
            System.out.println("There is no any elections right now");
        }
    }

    private static void vote() {
        Scanner in = new Scanner(System.in);
        if (!currentVoting.candidates.isEmpty()) {
            if (currentUser.lastElection != currentVoting) {
                System.out.println("Elections: " + currentVoting.title);
                for (int i = 0; i < currentVoting.candidates.size(); i++) {
                    System.out.println(Integer.toString(i + 1) + ". " + currentVoting.candidates.get(i).getName());
                }
                System.out.println("0. Vote for none");
                System.out.println();
                int choiceDone = -1;
                while (choiceDone < 0 || choiceDone > currentVoting.candidates.size()) {
                    System.out.print("Choose the candidate from the list: ");
                    choiceDone = in.nextInt();
                    System.out.println();
                    if (choiceDone >= 0 && choiceDone <= currentVoting.candidates.size()) {
                        if (choiceDone == 0) {
                            currentUser.lastElection = currentVoting;
                        } else {
                            Candidate votedCandidate = currentVoting.candidates.get(choiceDone - 1);
                            votedCandidate.addVoice();
                            currentVoting.candidates.set(choiceDone - 1, votedCandidate);
                            currentUser.lastElection = currentVoting;
                        }
                    } else {
                        System.out.println("Wrong input!");
                        choiceDone = -1;
                    }
                }
            } else {
                System.out.println("You cannot vote in this elections anymore");
            }
        } else {
            System.out.println("There is no any person in the candidates list");
        }
    }

    private static void getResults() {
        Scanner in = new Scanner(System.in);
        if (currentVoting != null && !currentVoting.candidates.isEmpty()) {
            System.out.println("Elections: " + currentVoting.title);
            for (int i = 0; i < currentVoting.candidates.size(); i++) {
                System.out.println(Integer.toString(i + 1) + ". " + currentVoting.candidates.get(i).getName() + ": " +
                        currentVoting.candidates.get(i).getVoices());
            }
            System.out.println();

        } else {
            System.out.println("There is no any person in the candidates list");
            System.out.println();
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
        System.out.println("1. Hold new elections");
        System.out.println("2. Add new candidate in the list");
        System.out.println("3. Election results");
        System.out.println("0. Exit account");
    }
}
