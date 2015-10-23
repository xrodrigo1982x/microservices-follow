package com.tweet.follow.repository;

import com.tweet.follow.model.FollowInfo;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;

import java.util.List;

public interface FollowInfoRepository extends GraphRepository<FollowInfo> {

    @Query("match (user {id: {0}})-[follow:FOLLOWING]->(following) return follow")
    List<FollowInfo> findFollowedBy(String id);

    @Query("match (user {id: {0}})<-[follow:FOLLOWING]-(following) return follow")
    List<FollowInfo> findFollowing(String id);

}
