package com.code.divvyDose.services;

import com.code.divvyDose.models.Profile;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
public interface RepoService {
    Map<String, Profile> getRepos(String repo);
}
