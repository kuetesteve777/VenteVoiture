package com.website.ventevoiture.repository;

import com.website.ventevoiture.model.Annonce;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author kuetesteve21
 */

@Repository
public interface AnnonceRepository extends JpaRepository<Annonce, Long> {
    
}
