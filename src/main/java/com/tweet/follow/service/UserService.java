package com.tweet.follow.service;

import com.tweet.follow.model.FollowInfo;
import com.tweet.follow.model.Relationship;
import com.tweet.follow.model.User;
import com.tweet.follow.repository.FollowInfoRepository;
import com.tweet.follow.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private FollowInfoRepository followInfoRepository;
    @Autowired
    private UserInfoService userInfoService;


    public User findById(String id) {
        User byId = userRepository.findById(id);
        return byId != null ? byId : userRepository.save(User.builder().id(id).build());
    }

    public List<FollowInfo> findFollowed(String id){
        List<FollowInfo> followedBy = followInfoRepository.findFollowedBy(id);
        followedBy.parallelStream()
                .forEach(user -> user.getUser().setName(userInfoService.getName(user.getUser().getId())));
        followedBy.parallelStream()
                .forEach(user -> user.getFollowed().setName(userInfoService.getName(user.getFollowed().getId())));
        return followedBy;
    }

    @CacheEvict(value = "follow.timeline", key = "#id")
    public void follow(String id, User user) {
        User follower = findById(id);
        User following = findById(user.getId());
        if(follower.getFollowing().contains(following))
            throw new IllegalArgumentException("User already following");
        FollowInfo info = userRepository.createRelationshipBetween(follower, following, FollowInfo.class, Relationship.FOLLOWING);
        info.setSince(new Date());
        followInfoRepository.save(info);
    }

    @CacheEvict(value = "follow.timeline", key = "#id")
    public void unfollow(String id, User user) {
        User follower = userRepository.findById(id);
        User following = userRepository.findById(user.getId());
        userRepository.deleteRelationshipBetween(follower, following, Relationship.FOLLOWING);
    }

    public List<FollowInfo> findFollowing(String id) {
        return followInfoRepository.findFollowing(id);
    }

    public List findRecommendation(String id) {
        return userRepository.recommended(id);
    }
}
