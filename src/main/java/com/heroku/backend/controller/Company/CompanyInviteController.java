package com.heroku.backend.controller.Company;

import com.heroku.backend.data.CompanyInviteRequestData;
import com.heroku.backend.data.response.CompanyInviteRequestResponseData;
import com.heroku.backend.exceptions.CompanyNotFoundException;
import com.heroku.backend.exceptions.UserAlreadyInCompanyException;
import com.heroku.backend.exceptions.UserNotFoundException;
import com.heroku.backend.service.Company.CompanyInviteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CompanyInviteController {

    private final CompanyInviteService companyInviteService;

    public CompanyInviteController(CompanyInviteService companyInviteService){ this.companyInviteService = companyInviteService; }

    @PostMapping("/company/invite/request")
    public ResponseEntity<CompanyInviteRequestResponseData> requestCompanyInviteUrl(@RequestBody CompanyInviteRequestData companyInviteRequestData)
            throws UserNotFoundException, CompanyNotFoundException, UserAlreadyInCompanyException {
        return companyInviteService.requestCompanyInviteUrl(companyInviteRequestData);
    }

}
