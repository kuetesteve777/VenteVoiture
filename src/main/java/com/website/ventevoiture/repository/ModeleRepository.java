package com.website.ventevoiture.repository;

import com.website.ventevoiture.model.Modele;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author kuetesteve21
 */
@Repository
public interface ModeleRepository extends JpaRepository<Modele, Long> {
    
    public Optional<Modele> findByName(String name);
}
