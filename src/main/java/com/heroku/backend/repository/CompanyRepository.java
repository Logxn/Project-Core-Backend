package com.heroku.backend.repository;

import com.heroku.backend.entity.CompanyEntity;
import com.heroku.backend.entity.UserEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface CompanyRepository extends MongoRepository<CompanyEntity, String> {
    @Query(value = "{'companyName': {$regex : ?0, $options: 'i'}}")
    CompanyEntity findByRegex(String regexString);
}
