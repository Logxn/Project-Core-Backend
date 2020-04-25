package com.heroku.backend.service.Company;

import com.heroku.backend.data.CompanyInviteRequestData;
import com.heroku.backend.data.response.CompanyInviteRequestResponseData;
import com.heroku.backend.entity.CompanyEntity;
import com.heroku.backend.entity.UserEntity;
import com.heroku.backend.enums.Status;
import com.heroku.backend.exceptions.CompanyNotFoundException;
import com.heroku.backend.exceptions.UserAlreadyInCompanyException;
import com.heroku.backend.exceptions.UserNotFoundException;
import com.heroku.backend.repository.CompanyRepository;
import com.heroku.backend.repository.UsersRepository;
import com.heroku.backend.service.JwtTokenService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;

@Service
public class CompanyInviteService {
    private final JwtTokenService jwtTokenService;
    private final UsersRepository usersRepository;
    private final CompanyRepository companyRepository;

    public CompanyInviteService(JwtTokenService jwtTokenService, UsersRepository usersRepository, CompanyRepository companyRepository) {
        this.jwtTokenService = jwtTokenService;
        this.usersRepository = usersRepository;
        this.companyRepository = companyRepository;
    }

    public ResponseEntity<CompanyInviteRequestResponseData> requestCompanyInviteUrl(@RequestBody CompanyInviteRequestData companyInviteRequestData)
            throws UserNotFoundException, CompanyNotFoundException, UserAlreadyInCompanyException{
        String username = companyInviteRequestData.username;
        String companyName = companyInviteRequestData.companyName;
        // Example: Invite-Logan-Github
        String tokenSubject = "Invite-" + username + "-" + companyName;

        UserEntity foundUser = usersRepository.findByUsername(username);
        if(foundUser == null)
            throw new UserNotFoundException();

        CompanyEntity foundCompany = companyRepository.findByRegex("^" + companyName + "$");

        if(foundCompany == null)
            throw new CompanyNotFoundException();

        if(Arrays.asList(foundCompany.getMembers()).contains(foundUser))
            throw new UserAlreadyInCompanyException();

        String token = jwtTokenService.generateToken(tokenSubject);
        String inviteLink = "project-core.herokuapp.com/company/invite/accept?token=" + token;

        CompanyInviteRequestResponseData responseData = new CompanyInviteRequestResponseData(Status.SUCCESS, inviteLink, LocalDateTime.now());

        return ResponseEntity.ok(responseData);
    }
}
