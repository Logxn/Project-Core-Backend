package com.heroku.backend.repository;

import com.heroku.backend.entity.UserEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface UsersRepository extends MongoRepository<UserEntity, String> {
    @Query(value = "{'username': {$regex : ?0, $options: 'i'}}")
    UserEntity findByUsername(String username);
    UserEntity findByEmail(String email);
}