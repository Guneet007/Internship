package com.example.API.Repository;

import com.example.API.Entities.User;
import org.springframework.data.repository.CrudRepository;
import java.math.BigInteger;

public interface UserRepository extends CrudRepository<User,BigInteger> {


}
