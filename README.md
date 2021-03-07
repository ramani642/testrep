# Git and Bitbucket Profiles
This project includes following information:

- Total number of public repos
- Total watcher/follower count
- A list/count of languages used across all public repos
- A list/count of repo topics


## Requirements
Docker 2.1.0.5+

## Run

```bash
docker pull ramani642/gitbitread:latest 
docker run -p 8085:8085 gitbitreadv1:latest
```

## Usage

to fetch a profile object from git and bitbucket repos 
[http://localhost:8085/profile/?repo=repoName](http://localhost:8085/profile/?repo=repoName)