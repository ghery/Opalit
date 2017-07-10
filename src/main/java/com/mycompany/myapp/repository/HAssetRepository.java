package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.HAsset;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the HAsset entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HAssetRepository extends JpaRepository<HAsset,Long> {

}
