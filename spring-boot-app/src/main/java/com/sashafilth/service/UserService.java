package com.sashafilth.service;

import com.sashafilth.cache.RedisCacheHelperValueOperations;
import com.sashafilth.dao.User;
import com.sashafilth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RedisCacheHelperValueOperations cacheHelper;

    @Autowired
    public UserService(UserRepository userRepository, RedisCacheHelperValueOperations cacheHelper) {
        this.userRepository = userRepository;
        this.cacheHelper = cacheHelper;
    }

    public void fillWithUsers() {
        try {
            List<User> userList = userList();
            userRepository.saveAll(userList);
            for (int i = 0; i < userList.size(); i++) {
                User user = userList.get(i);
                cacheHelper.put("USER", "user_" + i, user, 600);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public User findRandom() {
        Integer randomNumber = getRandomValue();
        Object obj = cacheHelper.get("USER", "user_" + randomNumber);
        if (obj != null) {
            System.out.println("From cache");
            return (User) obj;
        }
        List<String> about = aboutList();
        User userToCache = new User("Oleksandr", "Denysenko", 28, randomNumber, about);
        User savedToCache = saveToCache(randomNumber, userToCache);
        if (savedToCache != null) {
            System.out.println("From cache");
            return savedToCache;
        }
        System.out.println("From db");
        return userRepository.findByUniqueNumber(randomNumber);
    }

    private User saveToCache(Integer userNumber, User user) {
        try {
            cacheHelper.put("USER", "user_" + userNumber, user, 600);
            Object obj = cacheHelper.get("USER", "user_" + userNumber);
            return (User) obj;
        } catch (Exception e) {
            System.err.println("Save to cache error: " + e.getMessage());
            return null;
        }
    }

    private List<User> userList() {
        List<User> userList = new ArrayList<>();
        List<String> about = aboutList();
        for (int i = 0; i < 100; i++) {
            userList.add(new User("Oleksandr", "Denysenko", 28, i, about));
        }
        return userList;
    }

    private List<String> aboutList() {
        String sample = "Contrary to popular belief, Lorem Ipsum is not simply random text. It has roots in a piece of classical Latin literature from 45 BC, making it over 2000 years old. Richard McClintock, a Latin professor at Hampden-Sydney College in Virginia, looked up one of the more obscure Latin words, consectetur, from a Lorem Ipsum passage, and going through the cites of the word in classical literature, discovered the undoubtable source. Lorem Ipsum comes from sections 1.10.32 and 1.10.33 of \"de Finibus Bonorum et Malorum\" (The Extremes of Good and Evil) by Cicero, written in 45 BC. This book is a treatise on the theory of ethics, very popular during the Renaissance. The first line of Lorem Ipsum, \"Lorem ipsum dolor sit amet..\", comes from a line in section 1.10.32.\n" +
                "\n" +
                "The standard chunk of Lorem Ipsum used since the 1500s is reproduced below for those interested. Sections 1.10.32 and 1.10.33 from \"de Finibus Bonorum et Malorum\" by Cicero are also reproduced in their exact original form, accompanied by English versions from the 1914 translation by H. Rackham.";
        List<String> aboutList = new ArrayList<>();
        for (int i = 0; i < 5000; i++) {
            aboutList.add(sample);
        }
        return aboutList;
    }

    private Integer getRandomValue() {
        Random r = new Random();
        int low = 0;
        int high = 100;
        return r.nextInt(high-low) + low;
    }
}
