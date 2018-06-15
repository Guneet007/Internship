package com.example.API.Services;

import com.example.API.Entities.User;
import com.example.API.Repository.UserRepository;
import javassist.NotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers(){
        List<User> users=new ArrayList<>();
        userRepository.findAll()
                .forEach(users::add);
        return users;
    }

    public User addUser(User user) {
        return userRepository.save(user);
    }

    public List<User> addUser(List<User>user){
        for (int i=0;i<user.size();i++)
            userRepository.save(user.get(i));
        return user;
    }

    public User getUser(BigInteger id){
        Optional<User> users = userRepository.findById(id);
        return  users.get();
    }

    public User updateUser(User user, BigInteger id) throws Exception {
        User userFromDB = getUser(id);
        if(user !=null) {
            BeanUtils.copyProperties(user,userFromDB);
            userFromDB.setId(id);
        } else {
            throw new NotFoundException("not found");
        }
        return userRepository.save(userFromDB);
    }

    public void deleteUser(BigInteger id) {
        userRepository.deleteById(id);
    }
}
