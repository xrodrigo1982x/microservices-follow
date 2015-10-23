package com.tweet.follow.repository;

import com.tweet.follow.model.Recommendation;
import com.tweet.follow.model.User;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.neo4j.repository.RelationshipOperationsRepository;

import java.util.List;

public interface UserRepository extends GraphRepository<User>, RelationshipOperationsRepository<User> {

    User findById(String id);

    @Query("match (user {id: {0}})-[follow:FOLLOWING]->(following) return following")
    List<User> findFollowedBy(String id);

    @Query("match (user {id: {0}})-[follow:FOLLOWING*2]->(following) where " +
            "not (user)-[:FOLLOWING]->(following) with user, following, count(following) as qt where qt > 1 and not (following.id = user.id) return following, qt as quantity")
    List<Recommendation> recommended(String id);

}
