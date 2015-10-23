package com.tweet.follow.model;

import lombok.*;
import org.springframework.data.neo4j.annotation.QueryResult;
import org.springframework.data.neo4j.annotation.ResultColumn;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@QueryResult
public class Recommendation {

    @ResultColumn("following")
    private User user;
    @ResultColumn("quantity")
    private Integer quantity;

}
