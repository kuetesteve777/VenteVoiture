package com.website.ventevoiture.repository;

import com.website.ventevoiture.model.Image;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author kuetesteve21
 */
@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
 
    public Optional<Image> findByName(String name);
}
