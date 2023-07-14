package com.website.ventevoiture.repository;

import com.website.ventevoiture.model.Administrateur;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author kuetesteve21
 */
@Repository
public interface AdminRepository extends JpaRepository<Administrateur, Long> {
    
    public Optional<Administrateur> findByName(String name);
    public Optional<Administrateur> findByUserName(String userName);
    public Optional<Administrateur> findByEmail(String email);
    public Optional<Administrateur> findByPassword(String password);
    public Optional<Administrateur> findByEmailAndPassword(String email, String password);
}
