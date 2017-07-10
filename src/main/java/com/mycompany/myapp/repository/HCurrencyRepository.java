package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.HCurrency;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the HCurrency entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HCurrencyRepository extends JpaRepository<HCurrency,Long> {

}
