package com.heroku.backend.controller.Company;

import com.heroku.backend.data.CompanyInviteRequestData;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CompanyInviteController {

    private final CompanyInviteService companyInviteService;

    public CompanyInviteController(CompanyInviteService companyInviteService){ this.companyInviteService = companyInviteService; }

    @PostMapping("/company/invite/request")
    public ResponseEntity<CompanyInviteRequestResponseData> requestCompanyInviteUrl(@RequestBody CompanyInviteRequestData companyInviteRequestData){
        return companyInviteService.requestCompanyInviteUrl(companyInviteRequestData);
    }

}
