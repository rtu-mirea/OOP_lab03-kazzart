package com.company;

public class Elector extends User {
    private boolean voted = false;

    public Elector(String name, String login, String password) {
        super(name, login, password);
        access = 0;
    }

    public boolean isVoted() {
        return voted;
    }

    public void vote() {
        voted = true;
    }
}
