package com.company;

public class Elector extends User {
    private boolean voted = false;

    public Elector(String name, String login, String password) {
        super(name, login, password);
    }

    public boolean isVoted() {
        return voted;
    }

    public void vote() {
        voted = true;
    }
}
