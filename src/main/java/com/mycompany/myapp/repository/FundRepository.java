package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Fund;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Fund entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FundRepository extends JpaRepository<Fund,Long> {

}
