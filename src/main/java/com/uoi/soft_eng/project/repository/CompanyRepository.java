package com.uoi.soft_eng.project.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.uoi.soft_eng.project.model.Company;

@Repository
public interface CompanyRepository extends JpaRepository<Company, String>  {
    Optional<Company> findByUsername(String username);
}