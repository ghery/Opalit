package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Calcul;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Calcul entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CalculRepository extends JpaRepository<Calcul,Long> {

}
