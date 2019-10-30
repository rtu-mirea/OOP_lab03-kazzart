package com.company;

public class User {
    protected String name;
    protected String login;
    protected String password;
    public int access;
    public Voting lastElection;

    public User() {

    }

    public User(String name, String login, String password) {
        setName(name);
        setLogin(login);
        setPassword(password);
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public boolean enter(String login, String password) {
        if (this.login == login && this.password == password) {
            return true;
        } else {
            return false;
        }
    }
}
