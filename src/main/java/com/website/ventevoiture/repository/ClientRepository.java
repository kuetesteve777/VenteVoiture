package com.website.ventevoiture.repository;

import com.website.ventevoiture.model.Client;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author kuetesteve21
 */
@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    
    public Optional<Client> findByName(String name);
    public Optional<Client> findByUserName(String userName);
    public Optional<Client> findByEmail(String email);
    public Optional<Client> findByPassword(String password);
    public Optional<Client> findByEmailAndPassword(String email, String password);
}
