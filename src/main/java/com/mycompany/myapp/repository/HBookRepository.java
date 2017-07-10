package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.HBook;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the HBook entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HBookRepository extends JpaRepository<HBook,Long> {

}
