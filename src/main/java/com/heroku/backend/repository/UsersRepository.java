package com.heroku.backend.repository;

import com.heroku.backend.entity.UserEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UsersRepository extends MongoRepository<UserEntity, String> {
}