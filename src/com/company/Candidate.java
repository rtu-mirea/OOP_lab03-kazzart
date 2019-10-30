package com.company;

public class Candidate {
    private String name;
    private int voices = 0;

    public Candidate(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void addVoice() {
        voices++;
    }

    public int getVoices() {
        return voices;
    }
}
