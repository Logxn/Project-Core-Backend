package com.heroku.backend.repository;

import com.heroku.backend.entity.CompanyEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CompanyRepository extends MongoRepository<CompanyEntity, String> {
    CompanyEntity findByCompanyName(String companyName);
}
