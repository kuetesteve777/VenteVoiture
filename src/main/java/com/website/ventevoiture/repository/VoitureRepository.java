package com.website.ventevoiture.repository;

import com.website.ventevoiture.model.Voiture;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author kuetesteve21
 */

@Repository
public interface VoitureRepository extends JpaRepository<Voiture, Long> {
 
    public Optional<Voiture> findByName(String name);
}
