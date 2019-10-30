package com.company;

import java.util.ArrayList;
import java.util.List;

public class Voting {
    public String title;
    public List<Candidate> candidates;

    public Voting(String title, List<Candidate> candidates) {
        this.title = title;
        this.candidates = candidates;
    }
}
