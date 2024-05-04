package com.sociedade.scheduler.services;


import com.sociedade.scheduler.model.Company;
import com.sociedade.scheduler.model.dto.CreateCompanyDTO;
import com.sociedade.scheduler.model.user.User;
import com.sociedade.scheduler.repositories.CompanyRepository;
import com.sociedade.scheduler.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;


@Service
public class CompanyService {

    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    UserRepository userRepository;

    @Transactional
    public Company saveCompany(@RequestBody @Valid CreateCompanyDTO companyDTO, User user) {

        Company company = new Company();

        company.setName(companyDTO.name());
        company.setDescription(companyDTO.description());

        Company newCompany = this.companyRepository.save(company);

        company.getUsers().add(user);
        company.setDeleted(false);

        return this.companyRepository.save(newCompany);
    }

    public Company updateById(@PathVariable Long id, @RequestBody final CreateCompanyDTO companyDTO) {

        Company existingCompany = companyRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Empresa não encontrado com o ID: " + id ));

        existingCompany.setName(companyDTO.name());
        existingCompany.setDescription(companyDTO.description());

        return companyRepository.save(existingCompany);
    }

    public Company delete(@PathVariable Long id) {
        Company existingCompany = companyRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Empresa não encontrado com o ID: " + id ));
        existingCompany.setDeleted(true);
        return companyRepository.save(existingCompany);
    }

    public Page<Company> find(Pageable pageable, Boolean isPageable) {
        if (isPageable) {
            return this.companyRepository.findAll(pageable);
        } else {
            return new PageImpl<>(this.companyRepository.findAll());
        }
    }

    public Company findById(Long id) {
        return this.companyRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Empresa não encontrado com o ID: " + id));
    }


    public List<User> getUsersByCompanyId(Long companyId) {
        Optional<Company> companyOptional = companyRepository.findById(companyId);
        if (companyOptional.isPresent()) {
            Company company = companyOptional.get();
            return company.getUsers();
        } else {
            throw new EntityNotFoundException("Empresa não encontrado com o ID: " + companyId);
        }
    }

}
