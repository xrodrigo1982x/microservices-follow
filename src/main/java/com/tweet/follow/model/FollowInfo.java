package com.tweet.follow.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.data.neo4j.annotation.*;

import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@RelationshipEntity(type = Relationship.FOLLOWING)
@ApiModel(value = "Info de relacionamento", description = "Detalhes de relacionamento entre usuários")
public class FollowInfo {

    @GraphId
    @JsonIgnore
    private Long id;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "Data de criação do relacionamento", example = "2015-10-10 20:30:45", required = false)
    private Date since;
    @StartNode
    @Fetch
    @ApiModelProperty(value = "Usuário origem do relacionamento (quem segue)", reference = "usuario")
    private User user;
    @EndNode
    @Fetch
    @ApiModelProperty(value = "Usuário relacionamdo ao usuário origem (quem é seguido)", reference = "usuario")
    private User followed;

}
