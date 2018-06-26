package com.example.API.Repository;

import com.example.API.Entities.User;
import org.springframework.data.repository.CrudRepository;
import java.math.BigInteger;
import java.util.List;

public interface UserRepository extends CrudRepository<User,BigInteger> {
    // saveAll(List<User> users);

}
