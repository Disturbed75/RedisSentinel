package com.sashafilth.config;

import com.sashafilth.cache.RedisCacheHelperValueOperations;
import com.sashafilth.dao.User;
import com.sashafilth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;

@Configuration
public class InitializationConfig {

    private final UserRepository userRepository;
    private final RedisCacheHelperValueOperations cacheHelper;

    @Autowired
    public InitializationConfig(UserRepository userRepository, RedisCacheHelperValueOperations cacheHelper) {
        this.userRepository = userRepository;
        this.cacheHelper = cacheHelper;
    }

    @Bean
    public void initCacheAndDB() {
        User userToCache = new User("Specific", "User", 100, 100, Collections.emptyList());
        cacheHelper.put("USER_SPECIFIC", "user_specific", userToCache, 120);
        userRepository.save(userToCache);
        System.out.println("specific user created");
    }

}
