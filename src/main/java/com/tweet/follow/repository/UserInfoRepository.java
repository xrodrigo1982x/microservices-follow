package com.tweet.follow.repository;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@FeignClient("user")
public interface UserInfoRepository {

    @RequestMapping(value = "/{id}", method = GET)
    public User findUser(@PathVariable("id") String id);

    @Getter
    @Setter
    @NoArgsConstructor
    public static class User{

        private String name;

    }

}
