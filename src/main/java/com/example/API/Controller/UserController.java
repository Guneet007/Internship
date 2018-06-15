package com.example.API.Controller;

import com.example.API.Entities.User;
import com.example.API.Services.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.math.BigInteger;
import java.util.List;


@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @ApiOperation(value="Return all Users")
    @RequestMapping("/users")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @ApiOperation(value="Show a particular User")
    @RequestMapping("/users/{id}")
    public User getUser(@PathVariable BigInteger id){
        return userService.getUser(id);
    }

    @ApiOperation(value="Adds a List of new Users")
    @RequestMapping(method = RequestMethod.POST,value = "/users")
    public List<User> addUser(@RequestBody List<User> user){
        return userService.addUser(user);
    }

    @ApiOperation(value="Adds a new User")
    @RequestMapping(method = RequestMethod.POST,value = "/user")
    public User addUser(@RequestBody User user) {
        return userService.addUser(user);
    }

    @ApiOperation(value="Alter a User")
    @RequestMapping(method = RequestMethod.PUT, value="/users/{id}")
    public User updateUser(@RequestBody User user,@PathVariable BigInteger id) throws Exception {
        return userService.updateUser(user, id);
    }

    @ApiOperation(value="Delete a User")
    @RequestMapping(method = RequestMethod.DELETE, value="/users/{id}")
    public void deleteUser(@PathVariable BigInteger id){
            userService.deleteUser(id);
    }
}

