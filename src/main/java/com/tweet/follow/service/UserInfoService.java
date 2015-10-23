package com.tweet.follow.service;

import com.tweet.follow.repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class UserInfoService {

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Cacheable(value = "follow.user", key = "#username")
    public String getName(String username) {
        return userInfoRepository.findUser(username).getName();
    }

}
