package com.tweet.follow.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.neo4j.graphdb.Direction;
import org.springframework.data.annotation.Transient;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedToVia;

import java.util.Set;

/**
 * User
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@NodeEntity
@ApiModel(value = "usuario", description = "Informação de usuário e seus relacionamentos")
public class User {

    @GraphId
    @JsonIgnore
    private Long nodeId;
    @Indexed(unique = true)
    @ApiModelProperty(name = "Nome de usuário", required = true, example = "userabc", value = "Username")
    private String id;
    @RelatedToVia(type = Relationship.FOLLOWING, direction = Direction.OUTGOING)
    @JsonIgnore
    private Set<FollowInfo> following;
    @Transient
    private String name;

}
