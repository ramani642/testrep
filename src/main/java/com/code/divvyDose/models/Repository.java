package com.code.divvyDose.models;

import java.util.List;
import java.util.Set;

public class Repository {

    String name;

    Integer forks;

    Integer followers;

    @Override
    public String toString() {
        return "Repository{" +
                "name='" + name + '\'' +
                ", forks=" + forks +
                ", followers=" + followers +
                ", languages='" + languages + '\'' +
                '}';
    }

    String languages;

    public String getLanguages() {
        return languages;
    }

    public void setLanguages(String languages) {
        this.languages = languages;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getForks() {
        return forks;
    }

    public void setForks(int forks) {
        this.forks = forks;
    }

    public int getFollowers() {
        return followers;
    }

    public void setFollowers(int followers) {
        this.followers = followers;
    }

}
