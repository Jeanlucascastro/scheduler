package com.sociedade.scheduler.repositories;

import com.sociedade.scheduler.model.Company;
import com.sociedade.scheduler.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CompanyRepository extends JpaRepository<Company, Long> {

    List<User> findUsersById(Long companyId);
}
