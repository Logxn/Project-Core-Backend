package com.heroku.backend.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
@NoArgsConstructor
@Document(collection = "companies")
public class CompanyEntity {
    @Id
    private String id;
    private String companyName;
    private String administratorId;
    private transient String[] members;

    public CompanyEntity(String companyName, String administratorId, String[] members){
        this.companyName = companyName;
        this.administratorId = administratorId;
        this.members = members;
    }
}
