package com.tweet.follow.controller;

import com.tweet.follow.model.FollowInfo;
import com.tweet.follow.model.User;
import com.tweet.follow.service.UserService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@Api(value = "follow", description = "Informações de 'seguindo' entre usuários")
public class FollowController {

    @Autowired
    private UserService userService;

    @RequestMapping("followedby/{id}")
    @ApiOperation(value = "Lista usuários seguidos pelo usuário informado", httpMethod = "GET")
    public List<FollowInfo> findFollowed(@PathVariable String id){
        return userService.findFollowed(id);
    }

    @RequestMapping("{id}/timelineusers")
    @ApiOperation(value = "Usuários da timeline do usuário informado", httpMethod = "GET")
    public List<String> timeline(@PathVariable String id){
        return userService.findFollowed(id).stream().map(f -> f.getFollowed().getId()).collect(toList());
    }

    @RequestMapping("following/{id}")
    @ApiOperation(value = "Lista de usuários que seguem o usuário informado", httpMethod = "GET")
    public List<String> findFollowing(@PathVariable String id){
        return userService.findFollowing(id).stream().map(f -> f.getUser().getId()).collect(toList());
    }

    @RequestMapping("recommended/{id}")
    @ApiOperation(value = "Usuários recomendados para o usuário informado", httpMethod = "GET")
    public @ApiResponses({
            @ApiResponse(code = 200, message = "Usuários recomendados para o usuário informado")
    }) List findRecommended(@PathVariable
                                   @ApiParam(name = "Usuário para quem se destina a informação de recomendação") String id){
        return userService.findRecommendation(id);
    }

    @RequestMapping(value = "follow",method = POST)
    @ResponseStatus(OK)
    @ApiOperation(value = "Segue o usuário informado", notes = "Requer autenticação", httpMethod = "POST")
    public void follow(@AuthenticationPrincipal @ApiParam(name = "Usuário autenticado", required = true) Principal currentUser,
                       @RequestBody @ApiParam(name = "Usuário a ser seguido", required = true) User user){
        userService.follow(currentUser.getName(), user);
    }

    @RequestMapping(value="unfollow", method = POST)
    @ResponseStatus(OK)
    @ApiOperation(value = "Deixa de seguir o usuário informado", notes = "Requer autenticação", httpMethod = "POST")
    public void unfollow(@AuthenticationPrincipal @ApiParam(name = "Usuário autenticado", required = true) Principal currentUser,
                         @RequestBody @ApiParam(name = "Usuário que não será mais seguido", required = true) User user){
        userService.unfollow(currentUser.getName(), user);
    }

}
