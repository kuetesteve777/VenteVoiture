package com.website.ventevoiture.repository;

import com.website.ventevoiture.model.Annonceur;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author kuetesteve21
 */
@Repository
public interface AnnonceurRepository extends JpaRepository<Annonceur, Long> {
    
    public Optional<Annonceur> findByName(String name);
    public Optional<Annonceur> findByUserName(String userName);
    public Optional<Annonceur> findByEmail(String email);
    public Optional<Annonceur> findByPassword(String password);
    public Optional<Annonceur> findByAdress(String adress);
    public Optional<Annonceur> findByPhone(String phone);
    public Optional<Annonceur> findByEmailAndPassword(String email, String password);
}
