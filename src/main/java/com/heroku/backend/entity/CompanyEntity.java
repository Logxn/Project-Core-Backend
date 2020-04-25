package com.heroku.backend.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.List;

@SuppressWarnings("JpaAttributeTypeInspection")
@Entity
@Data
@NoArgsConstructor
@Document(collection = "companies")
public class CompanyEntity {
    @Id
    private String id;
    private String companyName;
    private String administratorId;
    private String[] members;

    public CompanyEntity(String companyName, String administratorId, String[] members){
        this.companyName = companyName;
        this.administratorId = administratorId;
        this.members = members;
    }
}
