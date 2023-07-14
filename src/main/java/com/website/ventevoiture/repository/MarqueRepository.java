package com.website.ventevoiture.repository;

import com.website.ventevoiture.model.Marque;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author kuetesteve21
 */
@Repository
public interface MarqueRepository extends JpaRepository<Marque, Long> {
    
    public Optional<Marque> findByName(String name);
}
