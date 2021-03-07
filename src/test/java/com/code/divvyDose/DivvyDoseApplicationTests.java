package com.code.divvyDose;

import com.code.divvyDose.models.Profile;
import com.code.divvyDose.services.RepoService;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

@SpringBootTest
class DivvyDoseApplicationTests {


    @Qualifier("repoServiceImpl")
    @Autowired
    RepoService gitService;

	@Test
	void contextLoads() {
        Map<String, Profile> result = gitService.getRepos("mailchimp");
        for(String key : result.keySet()){
            Profile profile = result.get(key);
            System.out.println(key+": "+profile.toString());
        }
        System.out.println(result.toString());
        Assert.assertNotNull(result);
	}

}
