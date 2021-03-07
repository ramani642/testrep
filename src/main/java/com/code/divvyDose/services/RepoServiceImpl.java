package com.code.divvyDose.services;

import com.code.divvyDose.models.Profile;
import com.code.divvyDose.models.Repository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class RepoServiceImpl implements RepoService {

    RestTemplate template = new RestTemplate();

    @Autowired
    private ObjectMapper objectMapper;

    @Value("${git.url}")
    String gitRepoUrl;

    @Value("${bitbucket.url}")
    String bitBucketRepoUrl;

    /**
     * This method is used to find all the merged profiles of git and bitbucket.
     * @param repo This is repoName
     * @return Map object of two values(keys: git, bitbucket).
     */

 @Override
    public Map<String, Profile> getRepos(String repo) {
        {
            Map<String, Profile> map = new HashMap<>();
            Profile gitProfile = getGitProfile(repo);
            Profile bitBucketProfile = getBitBucketProfile(repo);
           map.put("Git", gitProfile);
            map.put("BitBucket", bitBucketProfile);

            return map;
        }
    }

    /**
     * This method is used to find bitbucket profile.
     * @param repo this is repoName
     * @return profile object.
     */
    private Profile getBitBucketProfile(String repo) {
        Profile profile = new Profile();
        ResponseEntity<?> response = null;
        String url = bitBucketRepoUrl+repo;

        String result = "";
        try {
            JsonNode returnData = null;
            response = template.exchange(url, HttpMethod.GET, null, JsonNode.class);
            if (response.getStatusCode() == HttpStatus.OK) {
                returnData = (JsonNode) response.getBody();
                if (returnData.get("values") instanceof ArrayNode) {
                    mapBitBucketComponentEvents(
                            objectMapper.readValue(returnData.get("values").toString(), JsonNode[].class), profile);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Exception in getBitBucketProfile", e);
        }
        return profile;
    }

    /**
     * This method maps git repo jsonbody to git profile object.
     * @param body jsonbody for gitrepo
     * @param profile This is git profile object
     */
    private void mapBitBucketComponentEvents(JsonNode[] body, Profile profile) throws JsonProcessingException {
        {
            List<Repository> repos = new ArrayList<>();
            int totalForkCount = 0;
            int toatalFollowersCount = 0;
            Set<String> languages = new HashSet<>();

            for (final JsonNode eventNode : body) {
                if (eventNode.get("name") != null) {
                    Repository repo = new Repository();
                    repo.setName(nullCheck(eventNode.get("name")));
                    String watchersUrl = null;
                    String forksUrl = null;
                    if (eventNode.get("links") != null && eventNode.get("links").get("forks") != null && eventNode.get("links").get("forks").get("href") != null) {
                        String forkCount = getSize(eventNode.get("links").get("forks").get("href").asText());
                        totalForkCount = totalForkCount + Integer.valueOf(forkCount);
                        repo.setForks(Integer.valueOf(forkCount));

                    }
                    if (eventNode.get("links") != null && eventNode.get("links").get("watchers") != null && eventNode.get("links").get("watchers").get("href") != null) {
                        String followersCount = getSize(eventNode.get("links").get("watchers").get("href").asText());
                        toatalFollowersCount = toatalFollowersCount + Integer.valueOf(followersCount);
                        repo.setFollowers(Integer.valueOf(followersCount));
                    }
                    String language = nullCheck(eventNode.get("language"));
                    repo.setLanguages(language);
                    languages.add(language);
                    repos.add(repo);
                }
            }
            profile.setRepos(repos);
            profile.setFollowersCount(toatalFollowersCount);
            profile.setLanguagesCount(languages.size());
            profile.setRepoCount(repos.size());
            profile.setForkRepoCount(totalForkCount);

        }
    }

    private String getSize(String url) {
        ResponseEntity<?> response = null;
        String result = "";
        try {
            JsonNode returnData = null;
            response = template.exchange(url, HttpMethod.GET, null, JsonNode.class);
            if (response.getStatusCode() == HttpStatus.OK) {
                returnData = (JsonNode) response.getBody();
                return nullCheck(returnData.get("size"));
            }
        } catch (Exception e) {
            throw new RuntimeException("Exception in getSize", e);
        }
        return "0";
    }

    /**
     * This method is used to find git profile.
     * @param repo This is the first parameter to findAvg method
     * @return profile object.
     */
    private Profile getGitProfile(String repo) {
        Profile profile = new Profile();
        ResponseEntity<?> response = null;
        String url = gitRepoUrl+repo+"/repos";

        String result = "";
        try {
            JsonNode returnData = null;
            response = template.exchange(url, HttpMethod.GET, null, JsonNode.class);
            if (response.getStatusCode() == HttpStatus.OK) {
                returnData = (JsonNode) response.getBody();
                if (returnData instanceof ArrayNode) {
                    mapComponentEvents(
                            objectMapper.readValue(returnData.toString(), JsonNode[].class), profile);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Exception in getGitProfile", e);
        }
        return profile;
    }

    /**
     * This method maps bitbucket repo jsonbody to bitbucket profile object.
     * @param body jsonbody for bitbucketrepo
     * @param profile This is bitbucket profile object
     */
    private void mapComponentEvents(JsonNode[] body, Profile profile) {
        List<Repository> repos = new ArrayList<>();
        int totalForkCount = 0;
        int toatalFollowersCount = 0;
        Set<String> languages = new HashSet<>();

        for (final JsonNode eventNode : body) {
            if (eventNode.get("name") != null) {
                Repository repo = new Repository();
                repo.setName(nullCheck(eventNode.get("name")));
                if (nullCheck(eventNode.get("forks_count")) != null) {
                    int forkCount = Integer.valueOf(String.valueOf(eventNode.get("forks_count")));
                    totalForkCount = totalForkCount + forkCount;
                    repo.setForks(forkCount);
                }
                if (nullCheck(eventNode.get("watchers_count")) != null) {
                    int followersCount = Integer.valueOf(String.valueOf(eventNode.get("watchers_count")));
                    toatalFollowersCount = toatalFollowersCount + followersCount;
                    repo.setFollowers(followersCount);
                }
                String language = nullCheck(eventNode.get("language"));
                repo.setLanguages(language);
                languages.add(language);
                repos.add(repo);
            }
        }
        profile.setRepos(repos);
        profile.setFollowersCount(toatalFollowersCount);
        profile.setLanguagesCount(languages.size());
        profile.setRepoCount(repos.size());
        profile.setForkRepoCount(totalForkCount);

    }

    private String nullCheck(JsonNode node) {
        return node != null ? node.asText() : null;
    }
}
