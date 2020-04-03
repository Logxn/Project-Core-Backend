package com.heroku.backend.repository;

import com.heroku.backend.entity.EmailEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EmailRepository extends MongoRepository<EmailEntity, String> {
    EmailEntity findByEmail(String email);
}
