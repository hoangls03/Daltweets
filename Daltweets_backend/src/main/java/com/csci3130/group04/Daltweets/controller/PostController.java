package com.csci3130.group04.Daltweets.controller;

import java.util.List;
import java.util.Map;

import com.csci3130.group04.Daltweets.service.Implementation.GroupServiceImpl;
import com.csci3130.group04.Daltweets.service.Implementation.UserServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.csci3130.group04.Daltweets.model.Post;
import com.csci3130.group04.Daltweets.model.User;
import com.csci3130.group04.Daltweets.service.FollowersService;
import com.csci3130.group04.Daltweets.service.PostService;
import com.csci3130.group04.Daltweets.service.UserService;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api/post")
public class PostController {
    @Autowired
    PostService postService;

    @Autowired
    UserServiceImplementation userService;

    @Autowired
    FollowersService followersService;
    @Autowired
    GroupServiceImpl groupService;

    @PostMapping("/create")
    public ResponseEntity<Post> createPost(@RequestBody Post post) {
        Post createdPost = postService.createPost(post);
        if (createdPost == null) {
            return ResponseEntity.badRequest().body(null); // Return 400 Bad Request if user is null
        }
        return ResponseEntity.ok(createdPost);
    }

    @GetMapping("/{username}/all")
    List<Post> getAllPosts(@PathVariable("username") String username) {
        User user = userService.getUserByName(username);
        List<User> following = followersService.getUserFollowing(user);
        return postService.getPostsByUsers(following);
    }
    @GetMapping("/{groupname}/groupPosts")
    ResponseEntity<List<Post>> getAllGroupPosts(@PathVariable("groupname") String groupname ) {
        if ( groupname == null ) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        List<User> groupmembers = groupService.getGroupMembers(groupname);
        if ( groupmembers == null ) {
            return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        }
        List<Post> groupposts = postService.getPostsByUsers(groupmembers);
        return new ResponseEntity<>(groupposts,HttpStatus.OK);
    }

}
