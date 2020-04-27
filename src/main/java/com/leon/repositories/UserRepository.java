package com.leon.repositories;

import com.leon.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends MongoRepository<User, String>
{
    List<User> findByUserId(String userId);
    List<User> findByDeskName(String deskName);
}