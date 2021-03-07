package com.code.divvyDose.models;

import java.util.List;
import java.util.Map;

public class Profile {
    int repoCount;
    int followersCount;
    int forkRepoCount;
    int languagesCount;
    List<Repository> repos;

    @Override
    public String toString() {
        return "Profile{" +
                "repoCount=" + repoCount +
                ", followersCount=" + followersCount +
                ", forkRepoCount=" + forkRepoCount +
                ", languagesCount=" + languagesCount +
                ", repos=" + repos.toString() +
                '}';
    }

    public int getForkRepoCount() {
        return forkRepoCount;
    }

    public void setForkRepoCount(int forkRepoCount) {
        this.forkRepoCount = forkRepoCount;
    }

    public int getRepoCount() {
        return repoCount;
    }

    public void setRepoCount(int repoCount) {
        this.repoCount = repoCount;
    }

    public int getFollowersCount() {
        return followersCount;
    }

    public void setFollowersCount(int followersCount) {
        this.followersCount = followersCount;
    }

    public int getLanguagesCount() {
        return languagesCount;
    }

    public void setLanguagesCount(int languagesCount) {
        this.languagesCount = languagesCount;
    }

    public List<Repository> getRepos() {
        return repos;
    }

    public void setRepos(List<Repository> repos) {
        this.repos = repos;
    }

}
