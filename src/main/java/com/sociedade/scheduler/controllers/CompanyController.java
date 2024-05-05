package com.sociedade.scheduler.controllers;

import com.sociedade.scheduler.model.Company;
import com.sociedade.scheduler.model.dto.CreateCompanyDTO;
import com.sociedade.scheduler.model.user.User;
import com.sociedade.scheduler.exception.GenericException;
import com.sociedade.scheduler.services.CompanyService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.ApiOperation;


import java.util.List;

@RestController()
@RequestMapping(value = "/company")
public class CompanyController {


    private final CompanyService companyService;

    @Autowired
    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @PostMapping()
    @ResponseBody()
    public ResponseEntity<Company> saveCompany(@RequestBody CreateCompanyDTO createCompanyDTO,
                                               @AuthenticationPrincipal User user) {
        try {
            Company saveCompany = this.companyService.saveCompany(createCompanyDTO, user);
            return new ResponseEntity<>(saveCompany, HttpStatus.CREATED);
        } catch (GenericException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Company> deleteCompany(@PathVariable Long id) {
        try {
            Company deletedCompany = this.companyService.delete(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping()
    public ResponseEntity<Page<Company>> search(
            @RequestParam(required = false, defaultValue = "true") Boolean isPageble, Pageable pageable) {
        try {
            Page<Company> companies = this.companyService.find(pageable, isPageble);
            return new ResponseEntity<>(companies, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Company> findById(@PathVariable Long id) {
        try {
            Company company = this.companyService.findById(id);
            return new ResponseEntity<>(company, HttpStatus.OK);
        } catch (GenericException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @ResponseBody
    @PutMapping(value = "/{id}")
    public ResponseEntity<Company> updateCompany(@PathVariable Long id, @RequestBody CreateCompanyDTO createCompanyDTO) {
        try {
            Company updatedCompany = this.companyService.updateById(id, createCompanyDTO);
            return new ResponseEntity<>(updatedCompany, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ResponseBody
    @GetMapping(value = "/users/{id}")
    @ApiOperation(value = "Find User by Company ID")
    public ResponseEntity<List<User>> findUsersByCompany(@PathVariable Long id) {
        List<User> users = this.companyService.getUsersByCompanyId(id);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }
}
