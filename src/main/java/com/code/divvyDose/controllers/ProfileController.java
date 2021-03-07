package com.code.divvyDose.controllers;

import com.code.divvyDose.models.Profile;
import com.code.divvyDose.services.RepoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("profile")
public class ProfileController {

    @Qualifier("repoServiceImpl")
    @Autowired
    RepoService gitService;

    private final Logger log = LoggerFactory.getLogger(ProfileController.class);

    @GetMapping
    @RequestMapping("/")
    public Map<String, Profile> commitsByArea(@RequestParam(name = "repo", required = true) String repo) {
        return gitService.getRepos(repo);
    }
}
